import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver class
 */
public class Main {
    public static void main(String[] args) {
        BudgetAnalyzer analyzer = new BudgetAnalyzer();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            printMenu();
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number 1-5.");
                scanner.nextLine(); // Clear the invalid input
                continue; // Restart loop
            }
            switch (choice) {
                case 1 -> addExpense(analyzer, scanner);
                case 2 -> showAllExpenses(analyzer);
                case 3 -> showCategoryReport(analyzer);
                case 4 -> predictBudget(analyzer, scanner);
                case 5 -> showUpcomingBills(analyzer);
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void printMenu() {
        System.out.println("\n=== Smart Expense Tracker (Enter number only: e.g. '1') ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Category Report");
        System.out.println("4. Predict Future Budget");
        System.out.println("5. View Upcoming Bills");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }
    
    private static void addExpense(BudgetAnalyzer analyzer, Scanner scanner) {
        System.out.print("Enter expense name: ");
        String name = scanner.next();
        
        System.out.print("Enter category: ");
        String category = scanner.next();
        
        double amount;
            do {
                System.out.print("Enter amount: ");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next(); // Clear invalid input
                }
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Amount must be positive!");
                }
            } while (amount <= 0);
        System.out.print("Is this a bill with a due date? (y/n): ");
        String isBill = scanner.next().toLowerCase();

        LocalDate dueDate = null;
        if (isBill.equals("y")) {
            System.out.print("Enter due date (YYYY-MM-DD): ");
            String dueDateStr = scanner.next();
            dueDate = LocalDate.parse(dueDateStr); // Convert String to LocalDate
        }

        
        analyzer.addExpense(new Expense(
            name, category, amount, LocalDate.now(), dueDate
        ));
        System.out.println("Expense added!");
    }
    
    private static void showAllExpenses(BudgetAnalyzer analyzer) {
        System.out.println("\nAll Expenses:");
        analyzer.getAllExpenses().forEach(System.out::println);
    }
    
    private static void showCategoryReport(BudgetAnalyzer analyzer) {
        System.out.println("\nSpending by Category:");
        analyzer.getCategoryTotals().forEach((cat, total) -> 
            System.out.printf("%-10s: $%.2f\n", cat, total)
        );
    }
    
    
    private static void predictBudget(BudgetAnalyzer analyzer, Scanner scanner) {
        System.out.print("Enter months to predict: ");
        int months = scanner.nextInt();
        
        double prediction = analyzer.predictBudget(
            analyzer.getTotalSpending(), 
            months
        );
        
        System.out.printf("\nPredicted spending in %d months: $%.2f\n", 
            months, prediction);
    }

    private static void showUpcomingBills(BudgetAnalyzer analyzer) {
    System.out.println("\nUpcoming Bills (Earliest First):");
    List<Expense> bills = analyzer.getSortedBills();
    if (bills.isEmpty()) {
        System.out.println("No upcoming bills!");
    } else {
        bills.forEach(bill -> 
            System.out.println(bill.getName() + " (Due: " + bill.getDueDate() + ")")
        );
    }
}
}