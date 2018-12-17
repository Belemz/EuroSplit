package fcul.pco.eurosplit.main;

import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.SplitCatalog;
import fcul.pco.eurosplit.domain.Table;
import fcul.pco.eurosplit.domain.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author tl
 */
public class Interp extends Start {

    /**
     * Contains the string that is correspond to interpreter's prompt. It is
     * printed on the console. The prompt is updated by the setPrompt() method.
     */
    private String prompt;
    /**
     * The input of the interpreter
     */
    private final Scanner input;
    /**
     * Contains the current user (after user creation or after login).
     */
    private User currentUser;

    /**
     * Contains the current Split
     */
    private Split currentSplit;
    /**
     *
     * @param input
     */
    public Interp(Scanner input) {
        prompt = ApplicationConfiguration.DEFAULT_PROMPT;
        this.input = input;
    }

    /**
     * Main interpreter command
     *
     * @param command
     * @param input
     * @throws IOException 
     */
    public void execute(String command, Scanner input) throws IOException {
        switch (command) {
            case "help":
                help();
                break;
            case "new user":
                makeNewUser(input);
                break;
            case "show users":
                showUsers();
                break;
            case "login":
                login(input);
                break;
            case "new split":
                makeNewSplit(input);
                break;
            case "select split":
                selectSplit(input);
                break;
            case "new expense":
                makeNewExpense(input);
                break;
            case "balance":
                printBalance();
                break;
            case "quit":
                quit();
                break;
            default:
                System.out.println("Unknown command. [" + command + "]");
        }
    }

    private void help() {
        System.out.println("help: show commands information.");
        System.out.println("new user: create a new account.");
        System.out.println("show users: show the list of registred users.");
        System.out.println("new split: create a new split.");
        System.out.println("select split: select a split.");
        System.out.println("new expense: add an expense to current split.");
        System.out.println("balance: print the balance of the current split.");
        System.out.println("login: log a user in.");
        System.out.println("quit: terminate the program.");
        System.out.println("reset: deletes current catalogs");
    }
    
    /**
     * Command to introduce a new user.
     * @param input
     */
    private void makeNewUser(Scanner input) {
       System.out.print("User name: ");
       String nName = input.nextLine();
       System.out.print("Email address: ");
       String nEmail = input.nextLine();
       
       if(Start.getUserCatalog().hasUserWithId(nEmail)) {
    	   System.out.println("A user with this email address already exists.");
       } else {
    	   User nUser = new User(nName, nEmail);
           this.currentUser = nUser;
           //adds a User instance to Start class UserCatalog instance.
           Start.getUserCatalog().addUser(nUser);
           this.setPrompt();
       }
       
    }

    /**
     * Command to introduce a new user.
     * @param Scanner
     * @param String
     * @Overload
     */
    private void makeNewUser(Scanner input, String nName) {
        System.out.print("Email address: ");
        String nEmail = input.nextLine();
        
        if(Start.getUserCatalog().hasUserWithId(nEmail)) {
     	   System.out.println("A user with this email address already exists.");
        } else {
     	   User nUser = new User(nName, nEmail);
            this.currentUser = nUser;
            
            //adds a User instance to Start class UserCatalog instance.
            Start.getUserCatalog().addUser(nUser);
            this.setPrompt();
        }

    };
    
    /**
     * Calls Start.getAllUsers from within Start class in order 
     * to print Start.userCatalog instance users in table form.
     */
    private void showUsers() {
        System.out.println(Start.getUserCatalog().getAllUsers());
    }
    
    /**
     * Saves the current session catalogs and exits the loop.
     * @see {@link #save()}
     */
    private void quit() {
        save();
    }

    
    /**
     * Current user is replaced on a successfull login.
     * Not sentitive to case for either email or name. 
     * Additional methods were created in UserCatalog to get matching names.
     * @param Scanner
     */
    private void login(Scanner input) {
        System.out.print("Username: ");
        String username = input.nextLine();
        User selUser;
        Boolean notLoggedIn;
        //verifies if user exists in Start.userCatalog
        selUser = this.selectOrCreateUser(input, username);
        
        try {
        	notLoggedIn = !selUser.getName().equals(this.currentUser.getName());
        	} catch (NullPointerException e) {
        		notLoggedIn = true;
        		}
        
        if(notLoggedIn) {
        	//to avoid asking again for email when failed login name
        	System.out.print("Email: ");
            String email = input.nextLine();
            //verifies if email corresponds to user in Start.userCatalog
            if(Start.getUserCatalog().hasUserWithId(email)) {
            	this.currentUser = Start.getUserCatalog().getUserById(email);
            } else {
            	System.out.println("Email doesn't match.");
            	return;
            };
        }
        
        
        this.setPrompt();
    }    
    
    /**
     * Replaces this.currentSplit with nSplit, with logged in user as owner and event set by request.
     * Replaces singleton SplitCatalog instance with this.currentSplit.
     * @param input 
     */
	private void makeNewSplit(Scanner input) {
		if(secure_procedure(0)) return;
		
		System.out.println("For what event is this split ? (i.e. «trip to Madrid», «house expenses», etc...)");
		String event = input.nextLine();
		Split nSplit = new Split(this.currentUser, event);
		this.currentSplit = nSplit;
		this.setPrompt();
		SplitCatalog.getInstance().addSplit(this.currentSplit);
		
		
	}
    
    /**
     * Selects and assigns created split to currentSplit attribute.
     * @param input
     */
    private void selectSplit(Scanner input) {
    	if(this.secure_procedure(0)) return;
		
		System.out.print("Name of split's owner ?");
		String splitowner = input.nextLine();
		
		List<Split> allSplits = Start.getSplitCatalog().getUserSplits(this.selectUser(input, splitowner));
		if(allSplits == null) {
			System.out.println("No splits assigned to user.");
			return;
		}
		
		for(int i = 0; i < allSplits.size(); i++){
			System.out.println(i + " " + allSplits.get(i).getEvent());
		};
		System.out.println("Select a split number:");
		
		this.currentSplit = allSplits.get(Integer.parseInt(input.nextLine()));
		this.setPrompt();
    }
    
    /**
     * Prints the balance of every user participating in a split.
     */
    private void printBalance() {
    	if(this.secure_procedure(1)) return;
        
    	int numberPaidFor;
    	int debitAmmount;
    	int debitAmmountRemainder;
    	int userBalanceUpdate;
    	
    	HashMap<User, Integer> userBalance = new HashMap<>();
    	for(Expense nextExp : this.currentSplit.getExpenses()) {
    		//this adds the creator of the expense.
        	userBalance.putIfAbsent(nextExp.getUser(), 0);
        	
    		numberPaidFor = nextExp.getPaidFor().size();
    		debitAmmount = Math.floorDiv(nextExp.getValue(), numberPaidFor);
    		debitAmmountRemainder = Math.floorMod(nextExp.getValue(), numberPaidFor);
    		//de modo a conseguir criar uma lista dos balanços dos intervenientes
    		//criei um map com keys user e int(saldo). Intervenientes vão sendo adicionados
    		//com saldo 0 se ainda não tiverem sido adicionados.
    		for(User paidFor : nextExp.getPaidFor()) {
        		userBalance.putIfAbsent(paidFor, 0);
        		userBalanceUpdate = userBalance.get(paidFor) - debitAmmount;
        		userBalance.replace(paidFor, userBalanceUpdate);
        	}
    		
    		//super simplified code to share the remainder of the expense.
    		ArrayList<User> userList = new ArrayList<User>();
    		userList.addAll(nextExp.getPaidFor());
    		for(int i = 0; i <= 1; i++) {
	    		//to prevent issues when list gets reduced to size == 1
    			if(userList.size() != 1) {
	    			int randomNum = ThreadLocalRandom.current().nextInt(0, userList.size() - 1);
		    		User randomPick = userList.get(randomNum);
		    		userBalanceUpdate = userBalance.get(randomPick) - debitAmmountRemainder;
	        		userBalance.replace(randomPick, userBalanceUpdate);
	        		userList.remove(randomPick);
	    		} else {
	    			User floorPick = userList.get(0);
		    		userBalanceUpdate = userBalance.get(floorPick) - debitAmmountRemainder;
	        		userBalance.replace(floorPick, userBalanceUpdate);
	    		}
    			
    		}
    		
    		//agora adiciona-se o user pago (se não tiver aparecido antes) 
    		//e incrementa-se o devido valor.
    		System.out.println(userBalance.get(nextExp.getUser()));
    		userBalanceUpdate = (nextExp.getValue()) + userBalance.get(nextExp.getUser());
    		userBalance.replace(nextExp.getUser(), userBalanceUpdate);
        }
    	
    	ArrayList<List<String>> tab = new ArrayList<List<String>>();
    	
    	for(User u : userBalance.keySet()) {
    		ArrayList<String> tabentry = new ArrayList<>();
    		
    		tabentry.add(u.getName());
    		tabentry.add(userBalance.get(u).toString());
    		
    		
    		tab.add(tabentry);
    	}
        
        try{
        	System.out.println(Table.tableToString(tab));
        } catch(IndexOutOfBoundsException e) {
        	System.err.println("No expenses found.");
        }
    }

    /**
     * Saves the catalogs of the current session.
     */
    private void save() {
        try {
        	System.out.println("Saving User Catalog...");
            Start.getUserCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving User Catalog.");
        }
        try {
        	System.out.println("Saving Expense Catalog...");
            Start.getExpenseCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving Expense Catalog.");
        }
        try {
        	System.out.println("Saving Split Catalog...");
            Start.getSplitCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving Split Catalog.");
        }
    }
    
    /**
     * Creates a new Expense instance with the proper parameters.
     * Adds the Expense to Interp.currentSplit, and Start.expenseCatalog.
     * @param input
     */
    private void makeNewExpense(Scanner input) {
        if(this.secure_procedure(1)) return;       
    	
        System.out.print("Expense made by you (" + this.currentUser.getName() + "). What did you pay for ?");
        String theItem = input.nextLine();
        
        System.out.print("How much did you pay? ");
        
        int theValue = Integer.parseInt(input.nextLine());
        
        Expense nExpense = new Expense(theItem, theValue, currentUser);
    	
        String paidFor;
        User whichUser;
        //breaks out of loop when no one is prompted
        while(true) {
	        System.out.print("Who did you pay for: («no one» to terminate)");
	        paidFor = input.nextLine();
	        //if constraints are not employed printBalance will try to divide by 0.
			if(paidFor.equalsIgnoreCase("no one")) {
				if(nExpense.getPaidFor().size() == 0){
					System.out.println("Add at least one participating user for the Expense.");
					continue;
				} else break;
			}

	        whichUser = this.selectUser(input, paidFor);
	        nExpense.addPaidFor(whichUser);
        }
        this.currentSplit.addExpense(nExpense);
        
        Start.getExpenseCatalog().addExpense(nExpense);
    }
    
    /**
     * Retrieves a new command.
     * @return
     */
    public String getPrompt() {
        return prompt;
    }
    
    /**
     * Sets a new command.
     */
    public void setPrompt() {
        if (currentUser == null) {
            this.prompt = ApplicationConfiguration.DEFAULT_PROMPT;
        }
        
        else if (currentSplit == null) {
            this.prompt = currentUser.getName();
        }
        else {
            this.prompt = currentUser.getName() + "." + currentSplit.getEvent();
        }
    }
    /**
     * 
     * @return
     */
    String nextToken() {
        String in;
        System.out.print(prompt + "> ");
        System.out.flush();
        if (input.hasNextLine()) {
            in = input.nextLine();
            return in;
        } else {
            return "";
        }

    }

    /**
     * This method may be used to find a user in the catalog given its name, for 
     * example when we want to add "paidFor" users to an expense. The method 
     * receives a name. If there is only one user with this name, return that 
     * user. If there is no user with that name, give the opportunity to create 
     * a new user. The several users (with same name) are found, show the list 
     * and ask which one should be used.
     *
     * @param input
     * @param name
     * @return User
     */
    private User selectOrCreateUser(Scanner input, String name) {
        ArrayList<User> list = Start.getUserCatalog().getUsersWithName(name);
        Boolean error = false;
        if (list.isEmpty()) {
            System.out.println("There is no registred user with name " + name + ".");
            if (askYNQuestion(input, "Do you want to create user " + name)) {
            	//User theUser = currentUser;
                makeNewUser(input, name);
                User newUser = currentUser;
                //currentUser = theUser;
                setPrompt();
                return newUser;
            } else {
                // ask again:
                System.out.println("pff... Who did you pay for: ");
                return selectOrCreateUser(input, input.nextLine());
            }
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            int i = 0;
            
            for (User u : list) {
                System.out.print("(" + i + ") " + u.getName() + "[" + u.getEmail() + "] - ");
                i++;
            }
            System.out.println("Which " + name + " ? ");
            
            do {
            	error = false;
	            try {
	            	i = Integer.valueOf(input.nextLine());
	            	//de forma a apanhar a excep��o.
	            	list.get(i);
	            } catch (IndexOutOfBoundsException e1) {
	            	System.err.println("Use an int within choice range...");
	            	error = true;
	            } catch (Exception e2) {
	            	System.err.println("Please use an int...");
	            	error = true;
	            }
            } while(error);
            
            return list.get(i);
        }
    }
    
    /**
     * Selects a user from the current session UserCatalog
     * @param input
     * @param name
     * @return User
     */
    private User selectUser(Scanner input, String name) {
    	List<User> list = Start.getUserCatalog().getUsersWithName(name);
    	if (list.size() == 1) return list.get(0);
    	
    	int k;
    	if (!list.isEmpty()) {
	    	for (int i=0; i < list.size(); i++) {
	    	System.out.println(i + " " + list.get(i));
	    	}
	    	System.out.print("Select a user: ");
	    	k = Integer.parseInt(input.nextLine());
    	} else {
	    	System.out.println("User not found.");
	    	System.out.print("Name: ");
	    	name = input.nextLine();
	    	return selectUser(input, name);
    	}
    	return list.get(k);
    }
    
    /**
     * 
     * @param input
     * @param question
     * @return
     */
    private boolean askYNQuestion(Scanner input, String question) {
        System.out.print(question + "? (y/n):");
        String answer = input.nextLine();
        while (!(answer.equalsIgnoreCase("Y")
                || answer.equalsIgnoreCase("N"))) {
            System.out.print(question + "? (y/n):");
            answer = input.nextLine();
        }
        return answer.equalsIgnoreCase("Y");
    }
    
    /**
     * Use for assuring session has valid instances of either user or split.
     * @param gate //gate = 0 for verifying user login, or gate = 1 for user and split selection.
     */
    private boolean secure_procedure(int gate) {
    	if (gate == 1) {
	    	try {
	        	this.currentUser.equals(null);	
	        } catch (NullPointerException e) {
	    		System.err.println("You must be logged in to proceed.");
	        	return true;
	    	}
	        try {
	        	this.currentSplit.equals(null);	
	        } catch (NullPointerException e) {
	    		System.err.println("Select a split to proceed.");
	        	return true;
	    	}
    	} else {
    		try {
	        	this.currentUser.equals(null);	
	        } catch (NullPointerException e) {
	    		System.err.println("You must be logged in to proceed.");
	        	return true;
	    	}
    	}
    	
    	return false;
    	
    }

}