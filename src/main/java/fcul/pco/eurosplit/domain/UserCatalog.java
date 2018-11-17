package fcul.pco.eurosplit.domain;
/*
 * @author Fábio Neves
 * UserCatalog stores instances of type User in a HashMap fashion.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UserCatalog {
	
	
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
	 * Returns User isntance from UserCatalog according to key.
	 * @param key
	 * @return User
	 */
	public User getUserById(String key) {
		if(users.containsKey(key) == true) {
			return users.get(key);
		} else {
			return null;
		}
	}
	
	/*
	 * This class was not implied by the teacher yet I found it usefull for testing purposes.
	 * @returns String
	 */
	public String toString() {
		StringBuilder catalog_string = new StringBuilder();
		
		for(Entry<String, User> u : users.entrySet()) {
			catalog_string.append(u.getValue().toString());
			catalog_string.append("##");
		};
		
		return catalog_string.toString();
	}
	
	/*
	 * Searches for a User within UserCatalog for it's email.
	 * @returns boolean
	 */
	public boolean hasUserWithId(String email) {
		return users.containsKey(email);
	}
	
	/*
	 * Uses UserCatalog.save() from persistence package. 
	 * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
	 * Saves to file "users.dat" with Users.toString method.
	 */
	public void save() throws IOException {
		fcul.pco.eurosplit.persistence.UserCatalog.save(users);
	}
	
	/*
	 * Uses UserCatalog.load() from persistence package.
	 * Persistence Package Full Path - fcul.pco.eurosplit.persistence.
	 * Loads from file "users.dat" with Users.fromString method reading each line one by one.
	 */
	public static void load() throws FileNotFoundException {
		fcul.pco.eurosplit.persistence.UserCatalog.load();
	}
}
