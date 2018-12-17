package fcul.pco.eurosplit.persistence;



import fcul.pco.eurosplit.domain.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static fcul.pco.eurosplit.main.ApplicationConfiguration.ROOT_DIRECTORY;
import static fcul.pco.eurosplit.main.ApplicationConfiguration.USER_CATALOG_FILENAME;

/**
 * This class ensures the proper management of saving and loading operations.
 *
 * @author Fábio Neves
 * @author Cláudia Belém
 */

public class UserCatalog {

    /**
     * Saves the UserCatalog instance to the specified path in fcul.pco.main.ApplicationConfiguration.
     * @param users
     * @throws IOException
     */
    public static void save(Map<String, User> users) throws IOException {

        try (BufferedWriter fw = new BufferedWriter(new FileWriter(ROOT_DIRECTORY + USER_CATALOG_FILENAME))) {


            for (User user : users.values()) {
                fw.write(user.toString() + "\n");
            }
        }
    }

    /**
     * Loads the UserCatalog stored in the specified path in fcul.pco.main.ApplicationConfiguration into the specified instance.
     * @return Map<String, User>
     */
    public static Map<String, User> load() {
        Map<String, User> map_users = new HashMap<String, User>();

        try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(ROOT_DIRECTORY + USER_CATALOG_FILENAME)))) {

            while (inputFromFile.hasNextLine()) {
                String line = inputFromFile.nextLine();
                User user = User.fromString(line);
                map_users.put(user.getEmail(), user);
            }
        } catch (FileNotFoundException e) {
            System.err.println("The user catalog file was not found.");
        }
        return map_users;

    }

}
