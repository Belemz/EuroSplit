package fcul.pco.eurosplit.persistence;

/*
 *
 * @author Cláudia Belém & Fábio Neves
 */


import fcul.pco.eurosplit.domain.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class UserCatalog {

  private static String directory = "./tests/";
  private static String fileName = "users.dat";

  public static void save(Map<String, User> users) throws IOException {

    try (BufferedWriter fw = new BufferedWriter(new FileWriter(directory + fileName))) {


      for (User user : users.values()) {
        fw.write(user.toString() + "\n");
      }
    }
  }


  public static Map<String, User> load() throws FileNotFoundException {
    HashMap<String, User> map_users = new HashMap<String, User>();

    try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(directory + fileName)))) {

      while (inputFromFile.hasNextLine()) {
        String line = inputFromFile.nextLine();
        User user = User.fromString(line);
        map_users.put(user.getEmail(), user);
      }
    }
    return map_users;

  }

}
