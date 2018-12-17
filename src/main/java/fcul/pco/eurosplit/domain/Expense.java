package fcul.pco.eurosplit.domain;

import fcul.pco.eurosplit.main.Start;

import java.util.ArrayList;
import java.util.List;


/**
 * The Expense class represents a an expense composed of item,
 * value, and a type User instance.
 *
 * @author Cláudia Belém
 * @author Fábio Neves
 */

public class Expense {

    private int id;

    private String item;

    private int value;

    private User paidBy;

    private List<User> paidFor;

    private Date when;

    private static int counter = 0;

    /**
     * Constructor
     * @param id Identifier of the expense.
     * @param item Expense related item.
     * @param value Monetary value of Expense.
     * @param paidBy Type User instance, assigns
     * an individual to the expense (identified by Name, Email).
     */
    private Expense(int id, String item, int value, User paidBy) {

        this.id = id;
        this.item = item;
        this.value = value;
        this.paidBy = paidBy;
        this.when = Date.dateNow();
        this.paidFor = new ArrayList<User>();
    }

    public Expense(String item, int value, User paidBy) {
        this(++counter, item, value, paidBy);
    }

    /**
     * Returns the expense corresponding to a certain id.
     *
     * @return int id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns Item of the expense instance.
     * @return String item
     */
    public String getItem() {
        return this.item;
    }


    /**
     * Returns the value of the expense instance.
     * @return int value
     */
    public int getValue() {
        return this.value;
    }


    /**
     * Returns the paying User of expense instance.
     * @return User paidBy
     */
    public User getUser() {
        return this.paidBy;
    }

    /**
     * Returns the list of paidFor Users
     * @return List<User> paidFor
     */
    public List<User> getPaidFor(){
    	return this.paidFor;
    }
    
    /**
     * Adds a paying user to the paidFor list in the instance.
     * @param user
     */
    public void addPaidFor(User user) {
        this.paidFor.add(user);
    }

    /**
     * Transforms Expense attributes into its hashkey string with format item#value#paidBy.
     * @return String
     */
    public String toString() {

        StringBuilder expense = new StringBuilder();

        expense.append(this.id).append("#");
        expense.append(this.item).append("#");
        expense.append(this.value).append("#");
        expense.append(this.paidBy.getEmail()).append("#");  // I have added the e-mail only due to task 1 from 3rd series

        expense.append(this.when.toString()).append("#");

        for (User user : this.paidFor) {
            expense.append(user.getEmail()).append("§");
        }

        return expense.toString().substring(0, expense.length() - 1);
    }

    /**
     * Uses User.fromString(String, String) method.
     * @param expense_line is String
     * @requires input formated into "Integer#String#Integer#User"
     * @return new Expense(Int, String, String, User)
     */
    public static Expense fromString(String expense_line) throws NumberFormatException {

        String[] split_expense = expense_line.split("#");

        User paidBy_user = Start.getUserCatalog().getUserById(split_expense[3]);

        Expense expense_object = new Expense(Integer.parseInt(split_expense[0]),
                split_expense[1],
                Integer.parseInt(split_expense[2]),
                paidBy_user);

        expense_object.when = Date.fromString(split_expense[4]);

        for (String email : split_expense[5].split("§")) {
            User paidFor_user = Start.getUserCatalog().getUserById(email);
            expense_object.addPaidFor(paidFor_user);
        }

        counter = (expense_object.id > counter) ? (expense_object.id) : counter;
        return expense_object;

    }


}
