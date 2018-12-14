package fcul.pco.eurosplit.domain;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */

import fcul.pco.eurosplit.main.Start;

import java.util.ArrayList;
import java.util.List;

public class Split {

    private int id;

    private User owner;

    private String event;

    private final List<Expense> expenses;

    private static int counter = 0;


    private Split(int id, User owner, String event) {
        this.id = id;
        this.owner = owner;
        this.event = event;
        this.expenses = new ArrayList<>();
    }

    public Split(User owner, String events) {
        this(counter++, owner, events);

    }

    public void setEvent(String event) {
        this.event = event;
    }
    
    public String getEvent() {
    	return this.event;
    }
    
    public User getOwner() {
        return this.owner;
    }
    
    public List<Expense> getExpenses() {
    	return this.expenses;
    }
    public void addExpense(Expense e) {
        this.expenses.add(e);
    }

    public int getId() {
        return id;
    }

    public String toString() {
        StringBuilder split_string = new StringBuilder();

        split_string.append(this.id).append("#");
        split_string.append(this.owner.getEmail()).append("#");
        split_string.append(this.event).append("#");

        for (Expense expense : this.expenses) {
            split_string.append(expense.getId()).append("-");
        }

        return split_string.toString().substring(0, split_string.length() - 1);
    }

    public static Split fromString(String split) {
        String[] split_string = split.split("#");

        User owner = Start.getUserCatalog().getUserById(split_string[1]);


        Split split_object = new Split(Integer.parseInt(split_string[0]), owner, split_string[2]);


        for (String id : split_string[3].split("-")) {
            Expense expense = Start.getExpenseCatalog().getExpenseById(Integer.parseInt(id));
            split_object.addExpense(expense);
        }

        counter = (split_object.id > counter) ? counter = split_object.id + 1 : counter;

        return split_object;

    }

}