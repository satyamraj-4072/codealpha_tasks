import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance = 100000; // starting balance

    void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (cost > balance) {
            System.out.println("❌ Not enough balance!");
            return;
        }
        holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + qty);
        balance -= cost;
        System.out.println("✅ Bought " + qty + " shares of " + stock.symbol);
    }

    void sellStock(Stock stock, int qty) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (qty > owned) {
            System.out.println("❌ Not enough shares!");
            return;
        }
        holdings.put(stock.symbol, owned - qty);
        balance += stock.price * qty;
        System.out.println("✅ Sold " + qty + " shares of " + stock.symbol);
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n📊 Portfolio:");
        double totalValue = balance;

        for (String sym : holdings.keySet()) {
            int qty = holdings.get(sym);
            double value = market.get(sym).price * qty;
            totalValue += value;
            System.out.println(sym + " | Qty: " + qty + " | Value: ₹" + value);
        }
        System.out.println("Cash Balance: ₹" + balance);
        System.out.println("Total Portfolio Value: ₹" + totalValue);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", 3500));
        market.put("INFY", new Stock("INFY", 1500));
        market.put("RELIANCE", new Stock("RELIANCE", 2500));

        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("❌ Enter numbers only!");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.println("\n📈 Market Data:");
                    for (Stock s : market.values()) {
                        System.out.println(s.symbol + " : ₹" + s.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySym = sc.nextLine().toUpperCase();
                    if (!market.containsKey(buySym)) {
                        System.out.println("❌ Stock not found!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    sc.nextLine();
                    portfolio.buyStock(market.get(buySym), buyQty);
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSym = sc.nextLine().toUpperCase();
                    if (!market.containsKey(sellSym)) {
                        System.out.println("❌ Stock not found!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    sc.nextLine();
                    portfolio.sellStock(market.get(sellSym), sellQty);
                    break;

                case 4:
                    portfolio.showPortfolio(market);
                    break;

                case 5:
                    System.out.println("👋 Exiting Stock Trading Platform");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}
