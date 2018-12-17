package fcul.pco.eurosplit.domain;

import java.io.IOException;
import java.util.*;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 * UserCatalog stores instances of type User in a HashMap fashion.
 */

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
     * @param u is User
     */
    public void addUser(User u) {
        this.users_map.put(u.getEmail(), u);
    }

    /**
     * Returns User instance from UserCatalog according to email.
     * @param email is String
     * @return User
     */
    public User getUserById(String email) {
        // return this.users_map.getOrDefault(email, null);
        if (this.users_map.containsKey(email)) {
            return this.users_map.get(email);
        } else {
            return null;
        }
    }

    /**
     * Returns an list containing user instances with names according to name.
     * @param name is String
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsersWithName(String name) {
    	ArrayList<User> l = new ArrayList<>();
        for (User u : this.users_map.values()) {
            if (name.equalsIgnoreCase(u.getName())) {
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
     * @param name
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
     *
     * Returns the all the values within map sorted.
     * @return ArrayList<User>
     */
    public String getAllUsers() {
        // The variable that is fed to the Table.tableToString
        //  was changed to ArrayList <List <String >> for compatibility purposes.
        List<User> userList = new ArrayList<User>();
        List<List<String>> table = new ArrayList<List<String>>();

        userList.addAll(this.users_map.values());
        Collections.sort(userList);


        for (User u : userList) {
            ArrayList<String> tableLine = new ArrayList<String>();
            tableLine.add(u.getName());
            tableLine.add(u.getEmail());

            table.add(tableLine);
        }
        
        try{
            return Table.tableToString(table);
        } catch(IndexOutOfBoundsException e) {
        	return new String("No users added.");
        }
    }

}
