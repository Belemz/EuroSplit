package fcul.pco.eurosplit.persistence;

import fcul.pco.eurosplit.domain.Expense;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExpenseCatalog {


    private static String directory = "./tests/";
    private static String fileName = "expenses.dat";


    public static void save(Map<Integer, Expense> expenses) throws IOException {

        try (BufferedWriter fw = new BufferedWriter(new FileWriter(directory + fileName))) {

            for (Expense expense : expenses.values()) {
                fw.write(expense.toString() + "\n");

            }
        }
    }


    public static Map<Integer, Expense> load() throws FileNotFoundException {

        HashMap<Integer, Expense> map_expenses = new HashMap<Integer, Expense>();

        try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(directory + fileName)))) {

            while (inputFromFile.hasNextLine()) {
                String line = inputFromFile.nextLine();

                Expense expense = Expense.fromString(line);
                map_expenses.put(expense.getId(), expense);
            }
        }
        return map_expenses;

    }
}
