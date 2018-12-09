package fcul.pco.eurosplit.domain;

import fcul.pco.eurosplit.main.Start;

import java.util.ArrayList;


/*
 * The Expense class represents a an expense composed of item
 * , value, and a type User instance.
 * @author Cláudia Belém & Fábio Neves
 */

public class Expense {

    private int id;

    private String item;

    private int value;

    private User paidBy;

    private ArrayList<User> paidFor;

    private Date when;

    private static int counter = 0;

    /*
     * Constructor
     * @param id. Identifier of the expense.
     * @param item. Expense related item.
     * @param value. Monetary value of Expense.
     * @param paidBy. Type User instance, assigns
     * an individual to the expense (identified by Name, Email).
     * @throws Exception.
     */

    private Expense(int id, String item, int value, User paidBy) {
        /*if (value <= 0) {
            //Doesn't allow negative value Expense instance to be created.
            throw new Exception("Positive Expense value needed.");  todo mudar o throw: criar new subclass of Exception
        }*/

        this.id = id;
        this.item = item;
        this.value = value;
        this.paidBy = paidBy;
        this.when = Date.dateNow();
        this.paidFor = new ArrayList<User>();
    }

    public Expense(String item, int value, User paidBy) {
        this(counter++, item, value, paidBy);
    }

    /**
     * Returns the expense corresponding to a certain id.
     *
     * @return int id
     */
    public int getId() {
        return this.id;
    }

    /*
     * Returns Item of the expense instance.
     * @returns String
     */
    public String getItem() {
        return this.item;
    }

    /*
     * Substitutes the name of the item.
     *@param item
     */
    public void setItem(String item) {
        this.item = item;
    }


    /*
     * Sets value. If already set, overwrites.
     * @param value
     */
   /* public void setValue(int value) {  todo isto é necessário ?
        if (value <= 0) {
            throw new Exception("Positive Expense value needed.");
            //Doesn't allow negative value Expense instance to be created.
        }

        this.value = value;
    } */

    /*
     * Returns the value of the expense instance.
     * @returns int
     */
    public int getValue() {
        return this.value;
    }


    /*
     * Returns the paying User of expense instance.
     * @returns User
     */
    public User getUser() {
        return this.paidBy;
    }

    /*
     * Sets User instance. If already present, overwrites previous value.
     * @param paidBy
     * @requires User instance.
     */
    public void setUser(String name, String email) {
        this.paidBy = new User(name, email);
    }

    public void addPaidFor(User user) {
        this.paidFor.add(user);
    }

    /*
     * Transforms Expense attributes into its hashkey string with format item#value#paidBy.
     * @returns String
     */
    public String toString() {

        StringBuilder expense = new StringBuilder();

        expense.append(this.id).append("#");
        expense.append(this.item).append("#");
        expense.append(this.value).append("#");
        expense.append(this.paidBy.getEmail()).append("#");  // I have added the e-mail only due to task 1 from 3rd series

        expense.append(this.when.toString()).append("#");

        for (User u : this.paidFor) {
            expense.append(u.getEmail()).append("|"); //todo confirmar no enunciado - O Símbolo e se podemos escrever apenas o email.
            //todo remover os caracteres após o último e-mail
        }

        return expense.toString();
    }

    /*
     * Uses User.fromString(String, String) method.
     * @param String
     * @requires input formated into "Integer#String#Integer#User"
     * @returns new Expense(Int, String, String, User)
     */
    public static Expense fromString(String expense_line) throws NumberFormatException {

        String[] split_expense = expense_line.split("#");

        User paidBy_user = Start.getUserCatalog().getUserById(split_expense[3]);

        Expense expense_object = new Expense(Integer.parseInt(split_expense[0]),
                split_expense[1],
                Integer.parseInt(split_expense[2]),
                paidBy_user);

        System.out.println(split_expense[4]);
        expense_object.when = Date.fromString(split_expense[4]);  //todo confirmar se posso fazer isto

        for (String email : split_expense[5].split("|")) {
            User paidFor_user = Start.getUserCatalog().getUserById(email);
            expense_object.addPaidFor(paidFor_user);
        }


        //todo: seria melhor usar o getID em vez do exp.id?
        counter = (expense_object.id > counter) ? counter = expense_object.id : counter; //should we replace by getId()?

        return expense_object;

    }


}
