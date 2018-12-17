package fcul.pco.eurosplit.persistence;

import fcul.pco.eurosplit.domain.Expense;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static fcul.pco.eurosplit.main.ApplicationConfiguration.EXPENSES_CATALOG_FILENAME;
import static fcul.pco.eurosplit.main.ApplicationConfiguration.ROOT_DIRECTORY;
/**
 * This class ensures the proper management of saving and loading operations.
 * @author Fábio Neves
 * @author Cláudia Belém
 */
public class ExpenseCatalog {
	
	/**
	 * Saves the ExpenseCatalog instance to the specified path in fcul.pco.main.ApplicationConfiguration.
	 * @param expenses
	 * @throws IOException
	 */
    public static void save(Map<Integer, Expense> expenses) throws IOException {

        try (BufferedWriter file_write = new BufferedWriter(new FileWriter(ROOT_DIRECTORY + EXPENSES_CATALOG_FILENAME))) {

            for (Expense expense : expenses.values()) {
                file_write.write(expense.toString() + "\n");
            }
        }
    }

    /**
     * Loads the ExpenseCatalog stored in the specified path in fcul.pco.main.ApplicationConfiguration into the specified instance.
     * @return Map<Integer, Expense>
     * @throws FileNotFoundException
     */
    public static Map<Integer, Expense> load() throws FileNotFoundException {

        Map<Integer, Expense> map_expenses = new HashMap<Integer, Expense>();

        try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(ROOT_DIRECTORY + EXPENSES_CATALOG_FILENAME)))) {

            while (inputFromFile.hasNextLine()) {
                String line = inputFromFile.nextLine();

                Expense expense = Expense.fromString(line);
                map_expenses.put(expense.getId(), expense);
            }
        }
        return map_expenses;

    }
}
