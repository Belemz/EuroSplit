package fcul.pco.eurosplit.domain;

import java.util.ArrayList;


/*
 * The Expense class represents a an expense composed of item
 * , value, and a type User instance.
 * @author Cláudia Belém & Fábio Neves	
 */

public class Expense {
	
	private String item;
	
	private int value;
	
	private User paidBy;
	
	private ArrayList<User> paidFor;
	
	private Date when;
	/*
	 * Constructor
	 * @param item. Expense related item.
	 * @param value. Monetary value of Expense.
	 * @param paidBy. Type User instance, assigns 
	 * an individual to the expense (identified by Name, Email). 
	 * @throws Exception.
	 */
	
	public Expense(String item, int value, User paidBy) throws Exception {
		this.item = item;
		this.value = value;
		if(value <= 0) {
			throw new Exception("Positive Expense value needed.");
			//Doesn't allow negative value Expense instance to be created.
		}
		
		this.paidBy = paidBy;
		this.when = Date.fromString(Date.dateNow());
		this.paidFor = new ArrayList<User>();
	}
	
	/*
	 * Substitutes the name of the item.
	 *@param item
	 */
	public void setItem(String item) {
		this.item = item;
	}
	
	/*
	 * Returns Item of the expense instance.
	 * @returns String
	 */
	public String getItem() {
		return item;
	}
	
	/*
	 * Sets value. If already set, overwrites.
	 * @param value
	 */
	public void setValue(int value) throws Exception {
		if(value <= 0) {
			throw new Exception("Positive Expense value needed.");
			//Doesn't allow negative value Expense instance to be created.
		}
		
		this.value = value;
	}
	
	/*
	 * Returns the value of the expense instance.
	 * @returns int
	 */
	public int getValue() {
		return value;
	}
	/*
	 * Sets User instance. If already present, overwrites previous value.
	 * @param paidBy
	 * @requires User instance.
	 */
	public void setUser(String name, String email) {
		this.paidBy = new User(name, email);
	}
	
	/*
	 * Returns the paying User of expense instance.
	 * @returns User
	 */
	public User getUser() {
		return paidBy;
	}
	
	public void addPaidFor(User U) {
		paidFor.add(U);
	}
	
	/*
	 * Transforms Expense attributes into its hashkey string with format item#value#paidBy.
	 * @returns String
	 */
	public String toString() {
		StringBuilder expense = new StringBuilder();
		
		
		expense.append(this.item + "#");
		
		expense.append(this.value + "#");
		
		expense.append(this.paidBy.toString() + "#");
		
		//in order to maintain readability only the email of the users inside paidFor are printed out.
		for(User u : this.paidFor) {
			expense.append(u.getEmail() + "#");
		}
		
		expense.append(this.when.toString());
		
		
		return expense.toString();
	}
	
	/*
	 * Uses User.fromString(String, String) method.
	 * @param String
	 * @requires input formated into "String#Integer#User"
	 * @returns new Expense(String, String, User)
	 */
	public static Expense fromString(String expense) throws NumberFormatException, Exception {
		String[] split_expense = expense.split("#"); 
		
		return new Expense(split_expense[0], Integer.parseInt(split_expense[1]), User.fromString(split_expense[2], split_expense[3]));
	}
}
