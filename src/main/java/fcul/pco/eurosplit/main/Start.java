<<<<<<< HEAD
import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.UserCatalog;
=======
package fcul.pco.eurosplit.main;
/*
 * @author Cláudia Belém & Fábio Neves
 */

import fcul.pco.eurosplit.domain.*;
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Start {

    private static UserCatalog userCatalog;
    private static ExpenseCatalog expenseCatalog;
    private static SplitCatalog splitCatalog;

    public static UserCatalog getUserCatalog() {
        return userCatalog;
    }

    public static ExpenseCatalog getExpenseCatalog() {
        return expenseCatalog;
    }

    public static SplitCatalog getSplitCatalog() {
        return splitCatalog;
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

        splitCatalog = SplitCatalog.getInstance();
        try {
            splitCatalog.load();
        } catch (FileNotFoundException e) {
            System.err.println("The split catalog file was not found.");
        }

    }


    /*
     * Deletes the files saved as Catalogs from UserCatalog class.
     */
    private static void deleteCatalogs() throws IOException {
        File f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                + "/"
                + ApplicationConfiguration.EXPENSES_CATALOG_FILENAME);
        f.delete();
<<<<<<< HEAD
        f.createNewFile();
=======

>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
        f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                + "/"
                + ApplicationConfiguration.USER_CATALOG_FILENAME);
        f.delete();
<<<<<<< HEAD
        f.createNewFile();
=======

>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
        f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                + "/"
                + ApplicationConfiguration.SPLIT_CATALOG_FILENAME);
        f.delete();
        f.createNewFile();
    }
<<<<<<< HEAD
    
    private static void run() throws IOException {
    	deleteCatalogs();
    	Scanner input = new Scanner(System.in);
    	initialize();
    	Interp interp = new Interp(input);
    	String command = "";
    	do {
    		command = interp.nextToken();
    		interp.execute(command, input);
    	} while (!command.equals("quit"));
    	
    	
=======

    private static void run() {
        deleteCatalogs();
        Scanner input = new Scanner(System.in);
        initialize();
        Interp interp = new Interp(input);
        String command = "";
        do {
            command = interp.nextToken();
            interp.execute(command, input);
        } while (!command.equals("quit"));


>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
    }
<<<<<<< HEAD
    public static void main(String[] args) throws IOException {
        run();
=======


    public static void main(String[] args) {
        initialize();
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git


<<<<<<< HEAD
        //if(TEST == 1) {
        /*
         * Start 1:
         * Creates 3 User;
         * 1 Catalog;
         * Adds User to catalog;
         * saves in "users.dat"
         * prints out catalog using UserCatalog.toString() (not implied by the exercise but usefull);
         
=======
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
        User user1 = new User("albino", "albino@hotmail.com");
        User user2 = new User("josefino", "josefino@netcabo.pt");
        User user3 = new User("carlino", "carlino@gmail.com");

        userCatalog.addUser(user1);
        userCatalog.addUser(user2);
        userCatalog.addUser(user3);

        try {
            userCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }


<<<<<<< HEAD
        //	} else {
        /*
         * Test2:
         * Creates 1 UserCatalog;
         * Loads "users.dat" into it.
         * Prints out catalog.
         */
        // UserCatalog catalog2 = new UserCatalog();
        // catalog2.load();

        // System.out.println(catalog2.toString());
        //	}

        /*
        Expense expense1 = new Expense("noitada", 30, user1);
=======
        Expense expense1 = new Expense("avião", 30, user1);
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
        Expense expense2 = new Expense("Bowling", 16, user3);
<<<<<<< HEAD
        
=======
        Expense expense3 = new Expense("pipocas", 5, user2);


>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
        expense1.addPaidFor(user3);
        expense1.addPaidFor(user2);
        expense2.addPaidFor(user1);


        expense3.addPaidFor(user1);
        expense3.addPaidFor(user2);

        expenseCatalog.addExpense(expense1);
        expenseCatalog.addExpense(expense2);
        expenseCatalog.addExpense(expense3);
        try {
            expenseCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        System.out.println(expenses_catalog.toString());
		*/
//        ExpenseCatalog expense_list2 = new ExpenseCatalog();
//        try {
//            expense_list2.load();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(expense_list2);
=======

        Split split1 = new Split(user1, "viagem a Madrid");
        Split split2 = new Split(user1, "aniversário");
        Split split3 = new Split(user2, "cinema");

        split1.addExpense(expense1);
        split1.addExpense(expense2);

        split2.addExpense(expense2);

        split3.addExpense(expense3);

        splitCatalog.addSplit(user1, split1);
        splitCatalog.addSplit(user1, split2);
        splitCatalog.addSplit(user2, split3);

        try {
            splitCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }


>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
    }
}