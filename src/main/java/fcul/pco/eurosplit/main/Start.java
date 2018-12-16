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

    public static void initialize() throws IOException {
        userCatalog = UserCatalog.getInstance();
        
        try {
            userCatalog.load();
        } catch (FileNotFoundException e) {
            System.err.println("The user catalog file was not found. \nCreating a new one.");
        	File f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.USER_CATALOG_FILENAME);
            f.createNewFile();
        }

        
        expenseCatalog = ExpenseCatalog.getInstance();
        try {
            expenseCatalog.load();
        } catch (FileNotFoundException e) {
        	System.err.println("The user expense catalog file was not found. \nCreating a new one.");
        	File f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.EXPENSES_CATALOG_FILENAME);
            f.createNewFile();
        }

        splitCatalog = SplitCatalog.getInstance();
        try {
            splitCatalog.load();
        } catch (FileNotFoundException e) {
        	System.err.println("The split catalog file was not found. \nCreating a new one.");
        	File f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.SPLIT_CATALOG_FILENAME);
            f.createNewFile();        }

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
    private static void run() {
    	try {
			deleteCatalogs();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        Scanner input = new Scanner(System.in);
        
        try {
			initialize();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Interp interp = new Interp(input);
        String command = "";

        System.out.println("Type 'help' to show available commands.");

        do {
            command = interp.nextToken();
            try {
				interp.execute(command, input);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } while (!command.equals("quit"));


    }

    public static void main(String[] args) throws IOException {
        run();

    }
}