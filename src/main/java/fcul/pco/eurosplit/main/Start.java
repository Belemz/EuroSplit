package fcul.pco.eurosplit.main;
/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */

import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.SplitCatalog;
import fcul.pco.eurosplit.domain.UserCatalog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 * Penso que todas as tarefas requisitadas no projecto foram devidamente cobertas.
 * Alterações ao código do professor foram mínimas, no entanto além do método getAllUsers na classe UserCatalog
 * onde o tipo de retorno foi mudado para ser compatível com o método Table.tableToString(ArrayList<List<String>>),
 * quaiquer outras mudanças foram simplesmente aditivas e derivantes de erros resultantes do processo de debugging.
 *
 * Com os últimos testes corridos não parecem estar a haver quaisquer erros.
 */
public class Start {

    private static UserCatalog userCatalog;
    private static ExpenseCatalog expenseCatalog;
    private static SplitCatalog splitCatalog;
    
    /**
     * Retrieves the current UserCatalog.
     * @return UserCatalog
     */
    public static UserCatalog getUserCatalog() {
        return userCatalog;
    }
    
    /**
     * Retrieves the current ExpenseCatalog.
     * @return ExpenseCatalog
     */
    public static ExpenseCatalog getExpenseCatalog() {
        return expenseCatalog;
    }

    /**
     * Retrieves the current SplitCatalog.
     * @return SplitCatalog
     */
    public static SplitCatalog getSplitCatalog() {
        return splitCatalog;
    }

    /**
     * Loads the catalogs stored in the paths specified in main.ApplicationConfiguration.
     * @throws IOException
     */
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


    /**
     * Deletes the files saved as Catalogs from UserCatalog class.
     * @throws IOException
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
    
    /**
     * Deletes the current catalogs, initializes, and starts the prompt loop.
     * @see {@link #deleteCatalogs() #initialize()}
     */
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