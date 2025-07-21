import java.time.LocalDate;
import java.util.*;

/**
 * Main application logic
 */
public class BudgetAnalyzer {
    // Data Structures
    private final ArrayList<Expense> expenses = new ArrayList<>();          // 1. Dynamic array
    private final PriorityQueue<Expense> upcomingBills = new PriorityQueue<>(
        Comparator.comparing(Expense::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
    );
    private final HashMap<String, Double> categoryTotals = new HashMap<>(); // 3. Hash table
    
    // Add new expense to all data structures
    public void addExpense(Expense expense) {
        expenses.add(expense);
        if (expense.getDueDate() != null) {
        upcomingBills.add(expense);
    }
        
        // Update category total
        categoryTotals.merge(expense.getCategory(), 
            expense.getAmount(), 
            Double::sum);
    }
    
    // Algorithm 1: Bubble Sort
    public void sortExpensesByDate() {
        for (int i = 0; i < expenses.size()-1; i++) {
            for (int j = 0; j < expenses.size()-i-1; j++) {
                if (expenses.get(j).getDate().isAfter(expenses.get(j+1).getDate())) {
                    Collections.swap(expenses, j, j+1);
                }
            }
        }
    }
    
    // Algorithm 2: Linear Search with Filters
    public List<Expense> findExpenses(String category, double minAmount) {
        List<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category) 
                && e.getAmount() >= minAmount) {
                results.add(e);
            }
        }
        return results;
    }
    
    // Algorithm 3: Recursive Budget Prediction
    public double predictBudgetRecursive(int months) {
        if (months <= 0) return 0;  // Base case
        return getAverageSpending() + predictBudgetRecursive(months - 1);
    }
    
    // Helper methods
    public double getTotalSpending() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    
    public double getAverageSpending() {
        if (expenses.isEmpty()) return 0;
        return getTotalSpending() / getMonthCount();
    }

    public double calculateFuturePrediction(int months) {
        return predictBudgetRecursive(months);
    }

    private long getMonthCount() {
    if (expenses.isEmpty()) return 1;
    LocalDate earliest = expenses.stream()
        .map(Expense::getDate)
        .min(LocalDate::compareTo)
        .orElse(LocalDate.now());
    LocalDate latest = expenses.stream()
        .map(Expense::getDate)
        .max(LocalDate::compareTo)
        .orElse(LocalDate.now());
    return Math.max(1, earliest.until(latest).toTotalMonths() + 1);
    }

    
    // Getters for data structures
    public List<Expense> getAllExpenses() { return new ArrayList<>(expenses); }
    public List<Expense> getSortedBills() {
        List<Expense> sortedBills = new ArrayList<>();
        PriorityQueue<Expense> copy = new PriorityQueue<>(upcomingBills);
        while (!copy.isEmpty()) {
            sortedBills.add(copy.poll());
        }
        return sortedBills;
    }
    public Map<String, Double> getCategoryTotals() { return new HashMap<>(categoryTotals); }

    
}