import java.io.*;
import java.util.*;

class ExpenseTracker {
    private static final String FILE_NAME = "expenses.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nExpense Tracker Menu");
            System.out.println("1. Add Expense");
            System.out.println("2. View Summary");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewSummary();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            System.out.print("Enter category (Food, Travel, Bills, etc.): ");
            String category = scanner.nextLine();
            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            writer.write(date + "," + category + "," + amount + "," + description + "\n");
            System.out.println("Expense added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expense: " + e.getMessage());
        }
    }

    private static void viewSummary() {
        Map<String, Double> categoryTotals = new HashMap<>();
        double totalExpenses = 0;
        String highestCategory = "";
        double highestAmount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);

                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                totalExpenses += amount;
            }

            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                if (entry.getValue() > highestAmount) {
                    highestAmount = entry.getValue();
                    highestCategory = entry.getKey();
                }
            }

            System.out.println("\nTotal Expenses: $" + totalExpenses);
            System.out.println("Highest Spending Category: " + highestCategory + " - $" + highestAmount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
