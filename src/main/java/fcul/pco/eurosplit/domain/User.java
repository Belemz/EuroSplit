package fcul.pco.eurosplit.domain;


/**
 * The User class represents a user assigned with name and email.
 * @author Cláudia Belém
 * @author Fábio Neves	
 */

public class User implements Comparable<User>{
	private String name;
	
	private String email;
	
	/**
	 * Constructor of Instance User.
	 * @param name
	 * @param email
	 */
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	/**
	 * Sets instance name. If already present, overwrites.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns name of User associated instance.
	 * @returns String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets instance email. If already present, overwrites.
	 *@param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns email of User associated instance.
	 * @return String email.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns hashkey separated user attributes in format name#email.
	 * @return String user.toString().
	 */
	public String toString() {
		StringBuilder user = new StringBuilder();
		user.append(this.name + "#" + this.email);
		return user.toString();
	}
	
	
	/**
	 * Creates an User instance from string. Splits string on hashkey.
	 * @return User(user.toString)
	 * @see {@link #toString()}
	 */
	public static User fromString(String user) {
		String[] split_user = user.split("#");

		return new User(split_user[0], split_user[1]);
	}
	
	/**
	 * Creates an User instance from two different strings.
	 * @return User (name#email)
	 */
	public static User fromString(String name, String email) {
		return new User(name, email);
	}
	
	/**
	 * Method for the implementation of type comparable.
	 * Gets char from name o at position 0, and compares with the same position
	 * on User instance.
	 * @param o is User
	 * @return Character.compare(valueof(x), valueof(y)).
	 */
	@Override
	public int compareTo(User o) {
		return Character.compare(this.name.charAt(0), o.name.charAt(0));
	}
}
