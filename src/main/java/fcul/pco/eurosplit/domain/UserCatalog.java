package fcul.pco.eurosplit.domain;
/*
 * @author ClÃ¡udia BelÃ©m
 * @author FÃ¡bio Neves
 * UserCatalog stores instances of type User in a HashMap fashion.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UserCatalog {
<<<<<<< HEAD
	
	
	private Map<String,User> users;
	
	public UserCatalog() {
		this.users = new HashMap<String, User>();
	}
	
	/*
	 * Stores user instance to UserCatalog.
	 * @param u
	 */
	public void addUser(User u) {
		this.users.put(u.getEmail(), u);
	}
	
	/*
	 * Returns User instance from UserCatalog according to key.
	 * @param key
	 * @return User
	 */
	public User getUserById(String key) {
		if(this.users.containsKey(key)) {
			return this.users.get(key);
		} else {
			return null;
		}
	}
	
	/*
	 * 
	 */
	public ArrayList<User> getUsersWithName(String name) {
		ArrayList<User> l = new ArrayList<User>();
		String[] match;
		
		for(String key : this.users.keySet()) {
			match = this.users.get(key).getName().split(" ");
			
			if(match[0].equalsIgnoreCase(name)) l.add(this.users.get(key));
		}
		
		return l;
	}
	
	/*
	 * This class was not implied by the teacher yet I found it useful for testing purposes.
	 * @returns String
	 */
	public String toString() {
		StringBuilder catalog_string = new StringBuilder();
		
		for(User u : this.users.values()) {
			catalog_string.append(u.toString());
			catalog_string.append("\n");
		}
		
		return catalog_string.toString();
	}
=======
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git


<<<<<<< HEAD
	
	/*
	 * Searches for a User within UserCatalog for it's email.
	 * @returns boolean
	 */
	public boolean hasUserWithId(String email) {
		return this.users.containsKey(email);
	}
	
	/*
	 * Searches for a User according to his name.
	 * Non-sensitive to case.
	 */
	public boolean hasUserWithName(String name) {
		ArrayList<User> l = new ArrayList<User>();
		l.addAll(this.users.values());
		
		for(User u : l) {
			if(u.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		
		return false;
	}
	/*
	 * Uses UserCatalog.save() from persistence package. 
	 * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
	 * Saves to file "users.dat" with Users.toString method.
	 */
	public void save() throws IOException {
		fcul.pco.eurosplit.persistence.UserCatalog.save(this.users);
	}
	
	/*
	 * Uses UserCatalog.load() from persistence package.
	 * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
	 * Loads from file "users.dat" with Users.fromString method reading each line one by one.
	 */
	public void load() throws FileNotFoundException {
		this.users = fcul.pco.eurosplit.persistence.UserCatalog.load();
	}
	
	/*
	 * !!!A variável que é alimentada ao método Table.tableToString 
	 * foi alterada para ArrayList<List<String>> para ser 
	 * compatível.!!!
	 * Returns the all the values within map sorted.
	 * @return ArrayList<User>
	 */
	public String getAllUsers() {
		ArrayList<User> l = new ArrayList<User>();
		
		l.addAll(this.users.values());
		Collections.sort(l);
		
		ArrayList<List<String>> tab= new ArrayList<List<String>>();
		for(User u : l) {
			ArrayList<String> tabentry= new ArrayList<String>();
			tabentry.add(u.getName());
			tabentry.add(u.getEmail());
			
			tab.add(tabentry);
		};
		
		if(tab.size() != 0){
			return Table.tableToString(tab);
		} else {
			String error = new String("No users found");
			return error;
			
		}
	}
	
}
=======
    private Map<String, User> users;

    public UserCatalog() {
        this.users = new HashMap<String, User>();
    }

    /*
     * Stores user instance to UserCatalog.
     * @param u
     */
    public void addUser(User u) {
        this.users.put(u.getEmail(), u);
    }

    /*
     * Returns User instance from UserCatalog according to key.
     * @param key
     * @return User
     */
    public User getUserById(String key) {
        if (this.users.containsKey(key)) {
            return this.users.get(key);
        } else {
            return null;
        }
    }

    public List<User> getUsersWithName(String name) {
        List<User> l = new ArrayList<User>();
        l.addAll(this.users.values());

        for(User u : l) {
            String[] match = u.getName().split(" ");
            if(!match[0].equalsIgnoreCase(name)){
                l.remove(u);
            };
        }
        return l;
    }

    /*
     * Searches for a User within UserCatalog for it's email.
     * @returns boolean
     */
    public boolean hasUserWithId(String email) {
        return this.users.containsKey(email);
    }

    /*
     * Searches for a User according to his name.
     * Non-sensitive to case.
     */
    public boolean hasUserWithName(String name) {
        ArrayList<User> l = new ArrayList<User>();
        l.addAll(this.users.values());

        for(User u : l) {
            if(u.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    /*
     * Uses UserCatalog.save() from persistence package.
     * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
     * Saves to file "users.dat" with Users.toString method.
     */
    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.UserCatalog.save(this.users);
    }

    /*
     * Uses UserCatalog.load() from persistence package.
     * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
     * Loads from file "users.dat" with Users.fromString method reading each line one by one.
     */
    public void load() throws FileNotFoundException {
        this.users = fcul.pco.eurosplit.persistence.UserCatalog.load();
    }

    /*
     * !!!A variï¿½vel que ï¿½ alimentada ao mï¿½todo Table.tableToString
     * foi alterada para ArrayList<List<String>> para ser
     * compatï¿½vel.!!!
     * Returns the all the values within map sorted.
     * @return ArrayList<User>
     */
    public String getAllUsers() {
        ArrayList<User> l = new ArrayList<User>();

        l.addAll(this.users.values());
        Collections.sort(l);

        ArrayList<List<String>> tab = new ArrayList<List<String>>();
        for (User u : l) {
            ArrayList<String> tabentry = new ArrayList<String>();
            tabentry.add(u.getName());
            tabentry.add(u.getEmail());

            tab.add(tabentry);
        }
        ;

        return Table.tableToString(tab);
    }

}
>>>>>>> branch 'master' of https://github.com/Belemz/EuroSplit.git
