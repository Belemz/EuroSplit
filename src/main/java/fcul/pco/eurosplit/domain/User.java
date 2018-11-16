package fcul.pco.eurosplit.domain;


/*
 * The User class represents a user assigned with name and email.
 * @author Cláudia Belém & Fábio Neves	
 */

public class User {
	private String name;
	
	private String email;
	
	/*
	 * Constructor of Instance User.
	 * @param name
	 * @param email
	 */
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @returns String.
	 */
	
	/*
	 *@param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * @returns User.name
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/*
	 * @returns User.email
	 */
	public String getEmail() {
		return email;
	}
	
	/*
	 * Returns hashkey separated user attributes.
	 * @returns User.toString();
	 */
	public String toString() {
		StringBuilder user = new StringBuilder();
		user.append(this.name + "#" + this.email);
		return user.toString();
	}
	
	
	/*
	 * Creates an User instance from string
	 * Splits string on "#".
	 * @returns new User(name, email)
	 */
	public static User fromString(String user) {
		String[] split_user = user.split("#");
		return new User(split_user[0], split_user[1]);
	}
	
	/*
	 * Creates an User instance from two diferent strings.
	 * @returns new User(name, email)
	 */
	public static User fromString(String name, String email) {
		return new User(name, email);
	}
}
