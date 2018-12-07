package fcul.pco.eurosplit.persistence;

/*
 *
 * @author Cláudia Belém & Fábio Neves
 */


import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import fcul.pco.eurosplit.domain.User;


public class UserCatalog {

  private static String directory = "./tests/";
  private static String fileName = "users.dat";

  public static void save(Map<String, User> users) {

    try (BufferedWriter fw = new BufferedWriter(new FileWriter(directory + fileName))){
      for (Entry<String, User> entry : users.entrySet()) {
        fw.write(entry.toString() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, User> load() {
    HashMap<String, User> map = new HashMap<String, User>();

    try (Scanner inputFromFile = new Scanner(new BufferedReader(new FileReader(directory + fileName)))) {

      while (inputFromFile.hasNextLine()) {
        String line = inputFromFile.nextLine();
        User user = User.fromString(line);
        map.put(user.getEmail(), user);
      }

    } catch (FileNotFoundException e) {
      System.err.println("There is no such file");
    }

    return map;

  }

}
