package fcul.pco.eurosplit.domain;
/*
 * @author Fábio Neves
 * UserCatalog stores instances of type User in
 * a HashMap fashion.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UserCatalog {
	protected Map<String,User> users;
	
	public UserCatalog() {
		this.users = new HashMap<String, User>();
	}
	
	public void addUser(User u) {
		this.users.put(u.getEmail(), u);
	}
	
	public User getUserById(String key) {
		if(users.containsKey(key) == true) {
			return users.get(key);
		} else {
			return null;
		}
	}
	
	/*
	 * This class was not implied by the teacher yet I found it usefull for testing purposes.
	 */
	public String toString() {
		StringBuilder catalog_string = new StringBuilder();
		
		for(Entry<String, User> u : users.entrySet()) {
			catalog_string.append(u.getValue().toString());
			catalog_string.append("##");
		};
		
		return catalog_string.toString();
	}
	
	public boolean hasUserWithId(String email) {
		return users.containsKey(email);
	}
	
	public void save() throws IOException {
		fcul.pco.eurosplit.persistence.UserCatalog.save(users);
	}
	
	public static void load() throws FileNotFoundException {
		fcul.pco.eurosplit.persistence.UserCatalog.load();
	}
}
