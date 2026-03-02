import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        grades = new ArrayList<>();
    }

    void addGrade(int grade) {
        grades.add(grade);
    }

    double getAverage() {
        if (grades.size() == 0) return 0;

        int sum = 0;
        for (int g : grades) {
            sum += g;
        }
        return (double) sum / grades.size();
    }

    int getHighest() {
        if (grades.size() == 0) return 0;

        int max = grades.get(0);
        for (int g : grades) {
            if (g > max) {
                max = g;
            }
        }
        return max;
    }

    int getLowest() {
        if (grades.size() == 0) return 0;

        int min = grades.get(0);
        for (int g : grades) {
            if (g < min) {
                min = g;
            }
        }
        return min;
    }
}

public class Main {

    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;   // ✅ initialized properly

        do {
            System.out.println("\n===== Student Grade Tracker =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Display Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } else {
                System.out.println("Invalid input! Enter number only.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    students.add(new Student(name));
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    if (students.size() == 0) {
                        System.out.println("No students available.");
                        break;
                    }

                    displayStudentNames();
                    System.out.print("Select student number: ");
                    int index = sc.nextInt();
                    sc.nextLine();

                    if (index < 1 || index > students.size()) {
                        System.out.println("Invalid student number!");
                        break;
                    }

                    System.out.print("Enter grade: ");
                    int grade = sc.nextInt();
                    sc.nextLine();

                    students.get(index - 1).addGrade(grade);
                    System.out.println("Grade added successfully!");
                    break;

                case 3:
                    displayReport();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }

    static void displayStudentNames() {
        System.out.println("\nStudents List:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).name);
        }
    }

    static void displayReport() {

        if (students.size() == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\n===== STUDENT REPORT =====");

        int classHighest = Integer.MIN_VALUE;
        int classLowest = Integer.MAX_VALUE;
        double classTotal = 0;
        int totalGrades = 0;

        for (Student s : students) {

            System.out.println("\nStudent Name: " + s.name);
            System.out.println("Grades: " + s.grades);

            double avg = s.getAverage();
            int high = s.getHighest();
            int low = s.getLowest();

            System.out.println("Average: " + avg);
            System.out.println("Highest: " + high);
            System.out.println("Lowest: " + low);

            for (int g : s.grades) {
                if (g > classHighest) classHighest = g;
                if (g < classLowest) classLowest = g;

                classTotal += g;
                totalGrades++;
            }
        }

        if (totalGrades > 0) {
            System.out.println("\n===== CLASS SUMMARY =====");
            System.out.println("Class Average: " + (classTotal / totalGrades));
            System.out.println("Class Highest: " + classHighest);
            System.out.println("Class Lowest: " + classLowest);
        }
    }
}