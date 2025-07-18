import java.util.*;

/**
 * Main application logic
 */
public class BudgetAnalyzer {
    // Data Structures
    private final ArrayList<Expense> expenses = new ArrayList<>();          // 1. Dynamic array
    private final PriorityQueue<Expense> upcomingBills = new PriorityQueue<>( // 2. Priority queue
        Comparator.comparing(Expense::getDate)
    );
    private final HashMap<String, Double> categoryTotals = new HashMap<>(); // 3. Hash table
    
    // Add new expense to all data structures
    public void addExpense(Expense expense) {
        expenses.add(expense);
        upcomingBills.add(expense);
        
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
    public double predictBudget(double current, int months) {
        if (months <= 0) return current;
        double monthlyAvg = getAverageSpending();
        return predictBudget(current + monthlyAvg, months - 1);
    }
    
    // Helper methods
    public double getTotalSpending() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    
    private double getAverageSpending() {
        return getTotalSpending() / Math.max(1, expenses.size());
    }
    
    // Getters for data structures
    public List<Expense> getAllExpenses() { return new ArrayList<>(expenses); }
    public Queue<Expense> getUpcomingBills() { return new LinkedList<>(upcomingBills); }
    public Map<String, Double> getCategoryTotals() { return new HashMap<>(categoryTotals); }
}