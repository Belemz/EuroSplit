package fcul.pco.eurosplit.persistence;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */


import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static fcul.pco.eurosplit.main.ApplicationConfiguration.EXPENSES_CATALOG_FILENAME;
import static fcul.pco.eurosplit.main.ApplicationConfiguration.ROOT_DIRECTORY;


public class SplitCatalog {

    public static void save(Map<User, List<Split>> splits) throws IOException {

        try (BufferedWriter file_write = new BufferedWriter(new FileWriter(ROOT_DIRECTORY + EXPENSES_CATALOG_FILENAME))) {

            for (List<Split> user_split_list : splits.values()) {
                //n posso escrever a lista de uma vez só;

            }


            for (split:
                 splits.values()) {
                file_write.write(split.toString() + "\n");
            }
        }
    }


    public static Map<User, List<Split>> load() throws FileNotFoundException {

        Map<User, List<Split>> map_splits = new HashMap<User, List<Split>>();

        try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(ROOT_DIRECTORY + EXPENSES_CATALOG_FILENAME)))) {

            while (inputFromFile.hasNextLine()) {
                String line = inputFromFile.nextLine();

                Split split = Split.fromString(line);

                //todo o professor quer que o identificador de um split seja o id ou o email do owner?
                map_splits.put(split.getOwner(), split);
            }
        }
        return map_splits;

    }
}
