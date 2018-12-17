package fcul.pco.eurosplit.persistence;


import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;

import java.io.*;
import java.util.*;

import static fcul.pco.eurosplit.main.ApplicationConfiguration.ROOT_DIRECTORY;
import static fcul.pco.eurosplit.main.ApplicationConfiguration.SPLIT_CATALOG_FILENAME;


/**
 * This class ensures proper loading and saving of SplitCatalog instances.
 *
 * @author Cláudia Belém
 * @author Fábio Neves
 */

public class SplitCatalog {

    /**
     * Saves the SplitCatalog instance to the specified path in fcul.pco.main.ApplicationConfiguration.
     * @param splits
     * @throws IOException
     */
    public static void save(Map<User, List<Split>> splits) throws IOException {

        try (BufferedWriter file_write = new BufferedWriter(new FileWriter(ROOT_DIRECTORY + SPLIT_CATALOG_FILENAME))) {

            for (List<Split> user_split_list : splits.values()) {
                int split_counter = 0;
                for (Split split : user_split_list) {
                    split_counter ++;
                    if(split_counter < user_split_list.size()) {
                        file_write.write(split.toString() + ",");
                    } else {
                        file_write.write(split.toString());
                    }
                }

                file_write.write("\n");

            }

        }
    }

    /**
     * Loads the SplitCatalog stored in the specified path in fcul.pco.main.ApplicationConfiguration into the specified instance.
     * @return Map<User, List<Split>>
     */
    public static Map<User, List<Split>> load() {

        Map<User, List<Split>> map_splits = new HashMap<User, List<Split>>();

        try (Scanner inputFromFile = new Scanner(new BufferedReader((new FileReader(ROOT_DIRECTORY + SPLIT_CATALOG_FILENAME))))) {

            while (inputFromFile.hasNextLine()) {
                String line = inputFromFile.nextLine();
                String[] line_split = line.split(",");

                List<Split> split_list = new ArrayList<Split>();
                User user = null;

                for (String split_element : line_split) {
                    Split split = Split.fromString(split_element);

                    user = split.getOwner();
                    split_list.add(split);

                }
                map_splits.put(user, split_list);


            }


        } catch (FileNotFoundException e) {
            System.err.println("The split catalog file was not found.");
        }
        return map_splits;

    }
}
