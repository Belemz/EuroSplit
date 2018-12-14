package fcul.pco.eurosplit.main;
/*
 * @author Cláudia Belém & Fábio Neves
 */

import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.SplitCatalog;
import fcul.pco.eurosplit.domain.UserCatalog;

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
        userCatalog = UserCatalog.getInstance();
        try {
            userCatalog.load();
        } catch (FileNotFoundException e) {
            System.err.println("The user catalog file was not found.");
        }


        expenseCatalog = ExpenseCatalog.getInstance();
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
        f.createNewFile();
        f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                + "/"
                + ApplicationConfiguration.USER_CATALOG_FILENAME);
        f.delete();
        f.createNewFile();
        f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                + "/"
                + ApplicationConfiguration.SPLIT_CATALOG_FILENAME);
        f.delete();
        f.createNewFile();
    }

    // o run não pode ter throws. faz catch dentro do método (à volta do delete) ou dentro do próprio método delete!
    // Não faz sentido fazer throws no main! Isso dá uma exceção sem dar erro. Ou faz catch no main ou antes.
    private static void run() throws IOException {
        deleteCatalogs();
        Scanner input = new Scanner(System.in);
        initialize();
        Interp interp = new Interp(input);
        String command = "";

        System.out.println("Type 'help' to show available commands.");

        do {
            command = interp.nextToken();
            interp.execute(command, input);
        } while (!command.equals("quit"));


    }

    public static void main(String[] args) {
        //TODO Confirmar posição do try
        try {
            run();
        } catch (IOException e) {
            System.err.println("The file was not found.");
        }

/*
        User user1 = new User("albino", "albino@hotmail.com");
        User user2 = new User("josefino", "josefino@netcabo.pt");
        User user3 = new User("carlino", "carlino@gmail.com");

        userCatalog.addUser(user1);
        userCatalog.addUser(user2);
        userCatalog.addUser(user3);




        Expense expense1 = new Expense("avião", 30, user1);
        Expense expense2 = new Expense("Bowling", 16, user3);
        Expense expense3 = new Expense("pipocas", 5, user2);


        expense1.addPaidFor(user3);
        expense1.addPaidFor(user2);
        expense2.addPaidFor(user1);


        expense3.addPaidFor(user1);
        expense3.addPaidFor(user2);

        expenseCatalog.addExpense(expense1);
        expenseCatalog.addExpense(expense2);
        expenseCatalog.addExpense(expense3);


        Split split1 = new Split(user1, "viagem a Madrid");
        Split split2 = new Split(user1, "aniversário");
        Split split3 = new Split(user2, "cinema");

        split1.addExpense(expense1);
        split1.addExpense(expense2);

        split2.addExpense(expense2);

        split3.addExpense(expense3);

        splitCatalog.addSplit(split1);
        splitCatalog.addSplit(split2);
        splitCatalog.addSplit(split3);


        try {
            userCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            expenseCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            splitCatalog.save();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}