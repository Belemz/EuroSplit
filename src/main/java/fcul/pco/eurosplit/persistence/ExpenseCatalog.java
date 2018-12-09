package fcul.pco.eurosplit.persistence;

import fcul.pco.eurosplit.domain.Expense;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static fcul.pco.eurosplit.main.ApplicationConfiguration.EXPENSES_CATALOG_FILENAME;
import static fcul.pco.eurosplit.main.ApplicationConfiguration.ROOT_DIRECTORY;

public class ExpenseCatalog {

    public static void save(Map<Integer, Expense> expenses) throws IOException {

        try (BufferedWriter file_write = new BufferedWriter(new FileWriter(ROOT_DIRECTORY + EXPENSES_CATALOG_FILENAME))) {

            for (Expense expense : expenses.values()) {
                file_write.write(expense.toString() + "\n");

            }
        }
    }


    public static Map<Integer, Expense> load() throws FileNotFoundException {

        HashMap<Integer, Expense> map_expenses = new HashMap<Integer, Expense>();

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
