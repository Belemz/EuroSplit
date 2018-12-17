package fcul.pco.eurosplit.domain;
/**
 * @author Cláudia Belém
 * @author Fábio Neves
 * UserCatalog stores instances of type User in a HashMap fashion.
 */

import java.io.IOException;
import java.util.*;

public class UserCatalog {


    private Map<String, User> users_map;

    private static UserCatalog instance;

    /**
     * Constructor of UserCatalog class
     */
    private UserCatalog() {
        this.users_map = new HashMap<String, User>();
    }
    
    /**
     * Either retrieves a current instance of UserCatalog or creates a new one accessing
     * its constructor.
     * @return UserCatalog
     */
    public static UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }


    /**
     * Stores user instance to UserCatalog.
     * @param User
     */
    public void addUser(User u) {
        this.users_map.put(u.getEmail(), u);
    }

    /**
     * Returns User instance from UserCatalog according to key.
     * @param String
     * @return User
     */
    public User getUserById(String key) {
        // return this.users_map.getOrDefault(key, null);
        if (this.users_map.containsKey(key)) {
            return this.users_map.get(key);
        } else {
            return null;
        }
    }

    /**
     * Returns an list containing user instances with names according to name.
     * @param name
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsersWithName(String name) {
    	ArrayList<User> l = new ArrayList<>();
        for (User u : this.users_map.values()) {
            String[] match = u.getName().split(" ");
            if (match[0].equalsIgnoreCase(name)) {
                l.add(u);
            }
        }
        return l;
    }

    /**
     * Verifies wether an user with email exists in UserCatalog or not.
     * @returns boolean
     */
    public boolean hasUserWithId(String email) {
        return this.users_map.containsKey(email);
    }

    /**
     * Searches for a User according to his name.
     * Non-sensitive to case.
     * @param String
     * @return boolean
     */
    public boolean hasUserWithName(String name) {

        for (User user : this.users_map.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Uses UserCatalog.save() from persistence package.
     * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
     * Saves to file "users_map.dat" with Users.toString method.
     * @throws IOException
     */
    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.UserCatalog.save(this.users_map);
    }

    /**
     * Uses UserCatalog.load() from persistence package.
     * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
     * Loads from file "users_map.dat" with Users.fromString method reading each line one by one.
     */
    public void load() {
        this.users_map = fcul.pco.eurosplit.persistence.UserCatalog.load();
    }

    /**
     * !!!A variável que é alimentada ao método Table.tableToString
     * foi alterada para ArrayList<List<String>> para ser
     * compatível.!!!
     * Returns the all the values within map sorted.
     * @return ArrayList<User>
     * @throws IndexOutOfBoundsException
     */
    public String getAllUsers() {
        ArrayList<User> l = new ArrayList<User>();
        ArrayList<List<String>> tab = new ArrayList<List<String>>();

        l.addAll(this.users_map.values());
        Collections.sort(l);
        
        for (User u : l) {
        	ArrayList<String> tabentry = new ArrayList<String>();
            tabentry.add(u.getName());
            tabentry.add(u.getEmail());

            tab.add(tabentry);
        }
        
        try{
        	return Table.tableToString(tab);	
        } catch(IndexOutOfBoundsException e) {
        	return new String("No users added.");
        }
    }

}
