package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * The ExpenseCatalog class gives the tools to create, store, and manage
 * general lists of expenses created in runtime.
 * @author Cláudia Belém 
 * @author Fábio Neves
 */
public class ExpenseCatalog {

    private Map<Integer, Expense> expenses_map;

    private static ExpenseCatalog instance;


    private ExpenseCatalog() {
        this.expenses_map = new HashMap<Integer, Expense>();
    }


    public static ExpenseCatalog getInstance() {
        if (instance == null) {
            instance = new ExpenseCatalog();
        }
        return instance;
    }


    /**
     * Stores expense instance to ExpenseCatalog.
     * @param exp
     */
    public void addExpense(Expense exp) {
        this.expenses_map.put(exp.getId(), exp);
    }

    /**
     * Returns Expense instance from ExpenseCatalog according to key.
     * @param id
     * @return Expense
     */
    public Expense getExpenseById(Integer id) {
        return this.expenses_map.get(id);
    }

    /**
     * Searches for an Expense within ExpenseCatalog for it's id.
     * @returns boolean
     */
    public boolean hasExpenseWithId(Integer id) {
        return this.expenses_map.containsKey(id);
    }

    /**
     * Saves the current instance of ExpenseCatalog in the default path
     * specified in fcul.pco.main.AplicationConfiguration.
     * @throws IOException
     * @warning Existing files in the specified path will be overwritten.
     */
    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.ExpenseCatalog.save(this.expenses_map);
    }
    
    /**
     * Loads a saved ExpenseCatalog to the current instance in the default path
     * specified in fcul.pco.main.AplicationConfiguration.
     * @throws FileNotFoundException
     */
    public void load() {
        this.expenses_map = fcul.pco.eurosplit.persistence.ExpenseCatalog.load();
    }
}
