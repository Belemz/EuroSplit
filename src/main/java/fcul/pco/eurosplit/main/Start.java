package fcul.pco.eurosplit.main;
/*
 * @author Cláudia Belém & Fábio Neves
 */

import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.UserCatalog;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Start {
    private static UserCatalog userCatalog;
    private static ExpenseCatalog expenseCatalog;

    public static UserCatalog getUserCatalog() {
        return userCatalog;
    }

    public static ExpenseCatalog getExpenseCatalog() {
        return expenseCatalog;
    }

    public static void initialize() {
        userCatalog = new UserCatalog();
        try {
            userCatalog.load();
        } catch (FileNotFoundException e) {
            System.err.println("The user catalog file was not found.");
        }


        expenseCatalog = new ExpenseCatalog();
        try {
            expenseCatalog.load();
        } catch (FileNotFoundException e) {
            System.err.println("The expense catalog file was not found.");
        }
    }

    public static void main(String[] args) {
        initialize();

        // int TEST = 1;

        //if(TEST == 1) {
        /*
         * Start 1:
         * Creates 3 User;
         * 1 Catalog;
         * Adds User to catalog;
         * saves in "users.dat"
         * prints out catalog using UserCatalog.toString() (not implied by the exercise but usefull);
         */
        User user1 = new User("albino", "albino@hotmail.com");
        User user2 = new User("josefino", "josefino@netcabo.pt");
        User user3 = new User("carlino", "carlino@gmail.com");

        UserCatalog catalog = new UserCatalog();

        catalog.addUser(user1);
        catalog.addUser(user2);
        catalog.addUser(user3);

        try {
            catalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(catalog.toString());
        System.out.println("User catalog2");

        //	} else {
        /*
         * Test2:
         * Creates 1 UserCatalog;
         * Loads "users.dat" into it.
         * Prints out catalog.
         */
        UserCatalog catalog2 = new UserCatalog();
        // catalog2.load();

        // System.out.println(catalog2.toString());
        //	}


        Expense expense1 = new Expense("noitada", 30, user1);
        Expense expense2 = new Expense("Bowling", 16, user3);

        expense1.addPaidFor(user3);
        expense1.addPaidFor(user2);
        expense2.addPaidFor(user1);

        ExpenseCatalog expenses_catalog = new ExpenseCatalog();
        expenses_catalog.addExpense(expense1);
        expenses_catalog.addExpense(expense2);
        try {
            expenses_catalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(expenses_catalog.toString());

//        ExpenseCatalog expense_list2 = new ExpenseCatalog();
//        try {
//            expense_list2.load();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(expense_list2);
    }

}
