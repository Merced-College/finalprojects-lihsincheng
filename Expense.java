import java.time.LocalDate;

// This class represents a single expense record
public class Expense {
    // Fields (variables that store expense data)
    private String name;        // "Groceries"
    private String category;    // "Food"
    private double amount;      // e.g., 50.00
    private LocalDate date;     // Date of purchase
    private LocalDate dueDate;  // For bills only (like rent)

    // Constructor - called when creating a new Expense
    public Expense(String name, String category, double amount, LocalDate date, LocalDate dueDate) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.dueDate = dueDate;
    }

    // Getter methods (allow other classes to read private fields)
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public LocalDate getDueDate() { return dueDate; }

    // Makes printing expenses readable
    @Override
    public String toString() {
        String output = name + " ($" + amount + ", " + date + ")";
        if (dueDate != null){
            output += " [Due: " + dueDate + "]";
        }
        return output;
        
    }
}