import java.time.LocalDate;
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
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1 -> addExpense(analyzer, scanner);
                case 2 -> showAllExpenses(analyzer);
                case 3 -> showCategoryReport(analyzer);
                case 4 -> predictBudget(analyzer, scanner);
                case 5 -> System.exit(0);
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
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
    
    private static void addExpense(BudgetAnalyzer analyzer, Scanner scanner) {
        System.out.print("Enter expense name: ");
        String name = scanner.next();
        
        System.out.print("Enter category: ");
        String category = scanner.next();
        
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        
        analyzer.addExpense(new Expense(
            name, category, amount, LocalDate.now(), null
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
}