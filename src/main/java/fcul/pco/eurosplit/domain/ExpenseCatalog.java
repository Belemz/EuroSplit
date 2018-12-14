package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


    /*
     * Stores expense instance to ExpenseCatalog.
     * @param exp
     */
    public void addExpense(Expense exp) {
        this.expenses_map.put(exp.getId(), exp);
    }

    /*
     * Returns Expense instance from ExpenseCatalog according to key.
     * @param id
     * @return Expense
     */
    public Expense getExpenseById(Integer id) {
        //todo o que fazer quando não existe no dicionário um dado id ?? null (deixo assim)
        return this.expenses_map.get(id);
    }

    /*
     * Searches for an Expense within ExpenseCatalog for it's id.
     * @returns boolean
     */
    public boolean hasExpenseWithId(Integer id) {
        return this.expenses_map.containsKey(id);
    }


    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.ExpenseCatalog.save(this.expenses_map);
    }

    public void load() throws FileNotFoundException {
        this.expenses_map = fcul.pco.eurosplit.persistence.ExpenseCatalog.load();
    }
}
