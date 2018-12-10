package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExpenseCatalog {

    private Map<Integer, Expense> expenses;

    public ExpenseCatalog() {
        this.expenses = new HashMap<Integer, Expense>();
    }

    /*
     * Stores expense instance to ExpenseCatalog.
     * @param exp
     */
    public void addExpense(Expense exp) {
        this.expenses.put(exp.getId(), exp);
    }

    /*
     * Returns Expense instance from ExpenseCatalog according to key.
     * @param id
     * @return Expense
     */
    public Expense getExpenseById(Integer id) {
        //todo o que fazer quando não existe no dicionário um dado id ??
        return this.expenses.get(id);
    }

    /*
     * To separate the different expenses in each line! It turns the files more readable.
     * @returns String
     */
    //todo perguntar se é necessário este toString ou se pode ficar só o load do expenses (idem para o userCatalog).
//    public String toString() {
//        StringBuilder expense_catalog_string = new StringBuilder();
//
//        for (Expense exp : this.expenses.values()) {
//            expense_catalog_string.append(exp.toString());
//            expense_catalog_string.append("\n");
//        }
//
//        return expense_catalog_string.toString();
//    }


    /*
     * Searches for an Expense within ExpenseCatalog for it's id.
     * @returns boolean
     */
    public boolean hasExpenseWithId(Integer id) {
        return this.expenses.containsKey(id);
    }


    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.ExpenseCatalog.save(this.expenses);
    }

    public void load() throws FileNotFoundException {
        this.expenses = fcul.pco.eurosplit.persistence.ExpenseCatalog.load();
    }
}
