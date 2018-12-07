package fcul.pco.eurosplit;
/*
 * @author Cláudia Belém & Fábio Neves
 */
import java.io.IOException;
import java.util.Map;

import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.UserCatalog;

public class Test {

	public static void main(String[] args) throws IOException {
		// int TEST = 1;

		//if(TEST == 1) {
			/*
			 * Test 1:
			 * Creates 3 User;
			 * 1 Catalog;
			 * Adds User to catalog;
			 * saves in "users.dat"
			 * prints out catalog using UserCatalog.toString() (not implied by the exercise but usefull);
			 */
			User user1 = new User ("albino", "albino@hotmail.com");
			User user2 = new User ("josefino", "josefino@netcabo.pt");
			User user3 = new User ("carlino", "carlino@gmail.com");

			UserCatalog catalog = new UserCatalog();

			catalog.addUser(user1);
			catalog.addUser(user2);
			catalog.addUser(user3);

			catalog.save();

			System.out.println(catalog.toString());
			System.out.println("catalog2");
	//	} else {
			/*
			 * Test2:
			 * Creates 1 UserCatalog;
			 * Loads "users.dat" into it.
			 * Prints out catalog.
			 */
			UserCatalog catalog2 = new UserCatalog();
			catalog2.load();

			System.out.println(catalog2.toString());
	//	}
	}

}
