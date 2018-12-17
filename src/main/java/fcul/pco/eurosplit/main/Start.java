package fcul.pco.eurosplit.main;


import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.SplitCatalog;
import fcul.pco.eurosplit.domain.UserCatalog;

import java.io.IOException;
import java.util.Scanner;


/**
 * @author Cláudia Belém
 * @author Fábio Neves
 *
 * Pensamos que todas as tarefas requisitadas no projecto foram devidamente cobertas.
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
     */
    public static void initialize() {

        userCatalog = UserCatalog.getInstance();
        userCatalog.load();

        expenseCatalog = ExpenseCatalog.getInstance();
        expenseCatalog.load();

        splitCatalog = SplitCatalog.getInstance();
        splitCatalog.load();

    }



    
    /**
     *  initializes and starts the prompt loop.
     */
    private static void run() {

        Scanner input = new Scanner(System.in);


        initialize();

        
        Interp interp = new Interp(input);
        String command = "";

        System.out.println("Type 'help' to show the available commands.");

        do {
            command = interp.nextToken();
            interp.execute(command, input);

        } while (!command.equals("quit"));

    }

    public static void main(String[] args) throws IOException {
        run();
    }
}