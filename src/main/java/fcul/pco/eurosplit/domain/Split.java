package fcul.pco.eurosplit.domain;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */

import fcul.pco.eurosplit.main.Start;

import java.util.ArrayList;
import java.util.List;

/**
 * The Split class encloses events, to which a list of
 * Expenses are connected to. It's identified by id, owner, and event.
 * @author Fábio Neves & Cláudia Belém
 */
public class Split {

    private int id;

    private User owner;

    private String event;

    private final List<Expense> expenses;

    private static int counter = 0;

    /**
     * Private constructor, used to incorporate a counter in it's parameters.
     * @param id  a unique counter, identifies the Split instance.
     * @param owner  the user who creates the split.
     * @param event  the event associated with the split.
     */
    private Split(int id, User owner, String event) {
        this.id = id;
        this.owner = owner;
        this.event = event;
        this.expenses = new ArrayList<>();
    }

    /**
     * Public constructr, user to declare the owner and event in the split instance.
     * @param owner  the user who creates the split.
     * @param events  the event associated with the split.
     */
    public Split(User owner, String events) {
        this(++counter, owner, events);

    }

    /**
     * Sets the event in the split instance.
     * @param event
     */
    public void setEvent(String event) {
        this.event = event;
    }
    
    /**
     * Gets the event in the split instance.
     * @return Split.event.
     */
    public String getEvent() {
    	return this.event;
    }
    
    /**
     * Gets the owner associated in the split instance.
     * @return Split.owner.
     */
    public User getOwner() {
        return this.owner;
    }
    
    /**
     * Gets a list of expenses in the split instance.
     * @return Split.expenses.
     */
    public List<Expense> getExpenses() {
    	return this.expenses;
    }

    /**
     * Adds an expense to the List of expenses in the split instance.
     * @param e  the expense to be added.
     */
    public void addExpense(Expense e) {
        this.expenses.add(e);
    }

    /**
     * Gets the Identification number given by the static counter in the split instance.
     * @return Split.id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Prepares the contents in the split instance to be transformed into a single string.
     * It's attributes are sepparated by hash (#) sign, and the expense list contents
     * sepparated by ":";
     * @return String formated in "id#owner#event#expense:expense:...".
     */
    public String toString() {
        StringBuilder split_string = new StringBuilder();

        split_string.append(this.id).append("#");
        split_string.append(this.owner.getEmail()).append("#");
        split_string.append(this.event).append("#");

        for (Expense expense : this.expenses) {
            split_string.append(expense.getId()).append(":");
        }

        return split_string.toString().substring(0, split_string.length() - 1);
    }

    /**
     * Transduces the contents of a string formated in the same fashion has the one
     * delivered by Split.toString();
     * @see {@link #toString()}
     * @param  split	String formated in "id#owner#event#expense:expense:...".
     * @return Split instance.
     */
    public static Split fromString(String split) {

        String[] split_string = split.split("#");

        User owner = Start.getUserCatalog().getUserById(split_string[1]);


        Split split_object = new Split(Integer.parseInt(split_string[0]), owner, split_string[2]);

        if (split_string.length > 3) {
            for (String id : split_string[3].split(":")) {
                Expense expense = Start.getExpenseCatalog().getExpenseById(Integer.parseInt(id));
                split_object.addExpense(expense);
            }
        }

        counter = (split_object.id > counter) ? (split_object.id) : counter;

        return split_object;

    }

}