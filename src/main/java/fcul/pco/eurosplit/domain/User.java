package fcul.pco.eurosplit.domain;


/**
 * 
 * A class for representing the User with an e-mail and a name.
 * 
 * @author ClaudiaBelem
 **/
public class User {
	private String email;
	private String name;
	
	
	/**
	 *  Constructs a new user using supplied strings for email and user
	 * @param this.email for e-mail
	 * @param this.name for name
	 *
	 **/
	public User (String email, String name) {
		this.email = email;
		this.name = name;
		
	}

	
	
	
	/**
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @return the email
	 **/
	public String getEmail() {
		
		return email;
	}

	

	/**
	 *
	 * @param name the name to set
	 **/
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * 
	 * 
	 **/
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(email).append("#").append(name);
		return sb.toString();

	}

	
	public static User fromString(String line) {
		String [] line_split = line.split("#");
		String userEmail = line_split[0];
		String userName = line_split[1];
		return new User (userEmail, userName);
	}

	
	
	
}
