import java.util.*;
import java.io.*;

public class Main {

    // ---------------- ROOM CLASS ----------------
    static class Room {
        int roomNumber;
        String category;
        double price;
        boolean available;

        Room(int roomNumber, String category, double price) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.price = price;
            this.available = true;
        }
    }

    // ---------------- RESERVATION CLASS ----------------
    static class Reservation {
        String name;
        int roomNumber;
        String category;
        double amount;

        Reservation(String name, int roomNumber, String category, double amount) {
            this.name = name;
            this.roomNumber = roomNumber;
            this.category = category;
            this.amount = amount;
        }

        public String toFileString() {
            return name + "," + roomNumber + "," + category + "," + amount;
        }
    }

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    // ---------------- INITIALIZE ROOMS ----------------
    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(201, "Deluxe", 3500));
        rooms.add(new Room(202, "Deluxe", 3500));
        rooms.add(new Room(301, "Suite", 5000));
    }

    // ---------------- SEARCH ROOMS ----------------
    static void searchRooms() {
        System.out.println("\nAvailable Rooms:");
        boolean found = false;
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room: " + r.roomNumber +
                        " | Category: " + r.category +
                        " | Price: ₹" + r.price);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    // ---------------- BOOK ROOM ----------------
    static void bookRoom(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        searchRooms();

        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.available) {

                System.out.println("Room Price: ₹" + r.price);
                System.out.print("Confirm payment? (yes/no): ");
                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("yes")) {
                    r.available = false;

                    Reservation res = new Reservation(name, roomNo, r.category, r.price);
                    reservations.add(res);
                    saveToFile(res);

                    System.out.println("✅ Booking Successful!");
                } else {
                    System.out.println("❌ Payment Cancelled.");
                }
                return;
            }
        }

        System.out.println("❌ Room not available.");
    }

    // ---------------- CANCEL BOOKING ----------------
    static void cancelBooking(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        Iterator<Reservation> iterator = reservations.iterator();

        while (iterator.hasNext()) {
            Reservation res = iterator.next();

            if (res.name.equalsIgnoreCase(name)) {
                iterator.remove();

                for (Room r : rooms) {
                    if (r.roomNumber == res.roomNumber) {
                        r.available = true;
                        break;
                    }
                }

                rewriteFile();
                System.out.println("✅ Booking Cancelled.");
                return;
            }
        }

        System.out.println("❌ No booking found.");
    }

    // ---------------- VIEW BOOKINGS ----------------
    static void viewBookings() {
        if (reservations.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : reservations) {
            System.out.println("Name: " + r.name +
                    " | Room: " + r.roomNumber +
                    " | Category: " + r.category +
                    " | Paid: ₹" + r.amount);
        }
    }

    // ---------------- FILE SAVE ----------------
    static void saveToFile(Reservation res) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(res.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("File error!");
        }
    }

    static void rewriteFile() {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (Reservation r : reservations) {
                fw.write(r.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("File error!");
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        initializeRooms();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    bookRoom(sc);
                    break;
                case 3:
                    cancelBooking(sc);
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}