package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExpenseCatalog {

    private Map<Integer, Expense> expenseCatalog;

    public ExpenseCatalog() {
        this.expenseCatalog = new HashMap<Integer, Expense>();
    }

    /*
     * Stores expense instance to ExpenseCatalog.
     * @param exp
     */
    public void addExpense(Expense exp) {
        this.expenseCatalog.put(exp.getId(), exp);
    }

    /*
     * Returns Expense instance from ExpenseCatalog according to key.
     * @param id
     * @return Expense
     */
    public Expense getExpenseById(Integer id) {
        return this.expenseCatalog.getOrDefault(id, null);
    }

    /*
     * To separate the different expenses in each line! It turns the files more readable.
     * @returns String
     */
    public String toString() {
        StringBuilder expense_catalog_string = new StringBuilder();

        for (Expense exp : this.expenseCatalog.values()) {
            expense_catalog_string.append(exp.toString());
            expense_catalog_string.append("\n");
        }

        return expense_catalog_string.toString();
    }


    /*
     * Searches for an Expense within ExpenseCatalog for it's id.
     * @returns boolean
     */
    public boolean hasExpenseWithId(Integer id) {
        return this.expenseCatalog.containsKey(id);
    }


    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.ExpenseCatalog.save(this.expenseCatalog);
    }

    public void load() throws FileNotFoundException {
        this.expenseCatalog = fcul.pco.eurosplit.persistence.ExpenseCatalog.load();
    }
    
}
