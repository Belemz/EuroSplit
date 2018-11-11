package fcul.pco.eurosplit;

import fcul.pco.eurosplit.domain.User;

public class Test {

	public static void main(String[] args) {
		User user1 = new User ("claudiagarciabelem@gmail.com", "Cláudia Belém");
		System.out.println(user1.getEmail());
		System.out.println(user1.getName());
		System.out.println(user1.toString());
		User user2 = User.fromString("fabioneves@gmail.com#Fabio");
		System.out.println(user2);
		user2.setName("Fábio");
		user2.setEmail("fc44949@alunos.fc.ul.pt");
		System.out.println(user2);
		
		
	}

}
