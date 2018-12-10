package fcul.pco.eurosplit.domain;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */

import fcul.pco.eurosplit.main.Start;

import java.util.ArrayList;
import java.util.List;

public class Split {

    private int id; //todo este id é do split ? Ou das expenseS?

    private User owner;

    private String event;

    private final List<Expense> expenses;


    //    public Split (){
//        this.owner = null; //todo posso fazer isto? (perguntar ao thibault)
//        this.id = 0;
//        this.event = "";
//        this.expenses = new ArrayList<>();
//    }
//
//    public Split(int id, List<Expense> expenses){
//
//        this.expenses = expenses;
//    }
    public Split(User owner, int id, String event, List<Expense> expenses) {
        this.owner = owner;
        this.id = id;
        this.event = event;
        this.expenses = expenses;
    }

    public Split(User owner, int i, String s) {
        this(null, 0, "", new ArrayList<>());
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public User getOwner() {
        return this.owner;
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

        //todo PERGUNTAR AO THIBAULT COMO APAGAR O ÚLTIMO -
        return split_string.toString().substring(0, split_string.length() - 1);
    }

    public static Split fromString(String split) {
        String[] split_string = split.split("#");

        User owner = Start.getUserCatalog().getUserById(split_string[0]);


        Split split_object = new Split(owner,
                Integer.parseInt(split_string[1]),
                split_string[2]);

        for (String id : split_string[3].split("-")) {
            Expense expense = Start.getExpenseCatalog().getExpenseById(Integer.parseInt(id));
            split_object.addExpense(expense);
        }

        return split_object;
    }

}