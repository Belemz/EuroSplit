package fcul.pco.eurosplit.main;

import fcul.pco.eurosplit.domain.Expense;
// TODO: import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.UserCatalog;
import fcul.pco.eurosplit.domain.Date;
import fcul.pco.eurosplit.domain.Table;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author tl
 */
public class Interp {

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
    // TODO: private Split currentSplit;
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
     */
    // TODO: Tem de se corrigir e testar "makeNewExpense" "printBalance" e "makeNewExpense"
    public void execute(String command, Scanner input) {
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
                //makeNewSplit(input);
                break;
            case "select split":
                selectSplit(input);
                break;
            case "new expense":
                //makeNewExpense(input);
                break;
            case "balance":
                //printBalance();
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
    }

    private void makeNewUser(Scanner input) {
       System.out.print("User name: ");
       String nName = input.nextLine();
       System.out.print("Email address: ");
       String nEmail = input.nextLine();
       
       User nUser = new User(nName, nEmail);
       this.currentUser = nUser;
       
       //adds a User instance to Start class UserCatalog instance.
       Start.getUserCatalog().addUser(nUser);
    }
    
    /*
     * Calls Start.getAllUsers from within Start class in order 
     * to print Start.userCatalog instance users in table form.
     */
    private void showUsers() {
        System.out.println(Start.getUserCatalog().getAllUsers());
    }
    
    private void quit() {
        save();
    }

    
    /*
     * Current user is replaced on a successfull login.
     * Not sentitive to case for either email or name. 
     * Additional methods were created in UserCatalog to get matching names.
     * @param input 
     */
    private void login(Scanner input) {
        System.out.print("Username: ");
        String username = input.nextLine();
        //verifies if user exists in Start.userCatalog
        if(!Start.getUserCatalog().hasUserWithName(username)) {
        	System.out.println("User not found.");
        	return;
        };
        
        System.out.print("Email: ");
        String email = input.nextLine();
        //verifies if email corresponds to user in Start.userCatalog
        if(!Start.getUserCatalog().getUserById(email).getName().equalsIgnoreCase(username)) {
        	System.out.println("Email doesn't match.");
        	return;
        };
        
        this.currentUser = Start.getUserCatalog().getUserById(email);
        
        
        
        //TODO
    }
    
    /*
     * Replaces this.currentSplit with nSplit, with logged in user as owner and event set by request.
     * Replaces singleton SplitCatalog instance with this.currentSplit.
     * @param input 
     * @return this.currentSplit = nSplit;
     */
    
    /*
     * TODO: 
     * private void makeNewSplit(Scanner input) {
     
    	if(this.currentUser != null) {
	    	
    		Split nSplit = new Split(this.currentUser);
	    	System.out.println("For what event is this split ? (i.e. «trip to Madrid», «house expenses», etc...)");
	        String event = input.nextLine();
	        Split.setEvent(event);
	        this.currentSplit = nSplit;
	        SplitCatalog nSplitC = new SplitCatalog(this.currentUser);
    	
    	} else {
    		System.out.println("User must be logged in order to proceed.");
    	}
    }
     */
    
    
    private void selectSplit(Scanner input) {
        if(this.currentUser != null) {
        	
        	System.out.print("Name of split's owner ?");
        	String splitowner = input.nextLine();
        	
        	// TODO
        	
        	System.out.println("Select a split number:");
        	input.nextLine();
        }
    	// TODO: ainda é preciso corrigir este 
    }
    
    /* TODO:
    private void printBalance() {
    	int numberPaidFor;
    	int debitAmmount;
    	int debitAmmountRemainder;
    	int userBalanceUpdate;
    	// TODO: Ainda tem de ser adicionar um método aleatório 
    	// para dividir o resto por pessoas.
    	Random generator =  new Random();
    	UserCatalog user = Start.getUserCatalog();
    	Map<User, Integer> userBalance;
    	for(Expense nextExp : this.currentSplit.getExpenses) {
    		numberPaidFor = nextExp.getPaidFor().size();
    		debitAmmount = Math.floorDiv(nextExp.getValue(), numberPaidFor);
    		debitAmmountRemainder = Math.floorMod(nextExp.getValue(), numberPaidFor);
    		//de modo a conseguir criar uma lista dos balanços dos intervenientes
    		//criei um map com keys user e int(saldo). Intervenientes vão sendo adicionados
    		//com saldo 0 se ainda não tiverem sido adicionados.
    		for(User paidFor : nextExp.getPaidFor()) {
        		userBalance.putIfAbsent(paidFor, 0);
        		userBalanceUpdate = userBalance.get(paidFor) - debitAmmount;
        		userBalance.put(paidFor, userBalanceUpdate);
        	}
    		
    		
    		//agora adiciona-se o user pago (se não tiver aparecido antes) 
    		//e incrementa-se o devido valor.
    		userBalance.putIfAbsent(nextExp.getUser(), 0);
    		userBalanceUpdate = (nextExp.getValue() - debitAmmount) + userBalance.get(nextExp.getUser());
    		userBalance.put(nextExp.getUser(), userBalanceUpdate);
        }
    	// TODO
    }
    */

    private void save() {
        try {
            Start.getUserCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving User Catalog.");
        }
        try {
            Start.getExpenseCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving Expense Catalog.");
        }
        // TODO
    }
    
    /*
     * Creates a new Expense instance with the proper parameters.
     * Adds the Expense to Interp.currentSplit, and Start.expenseCatalog.
     */
    
    /*TODO:
    private void makeNewExpense(Scanner input) {
        System.out.print("Expense made by you (" + this.currentUser.toString() + "). What did you pay for ?");
        String theItem = input.nextLine();
        
        System.out.print("How much did you pay? ");
        int theValue = input.nextInt();
        
        Expense nExpense = new Expense(theItem, theValue, currentUser);
    	
        String paidFor;
        User whichUser;
        do {
	        System.out.print("Who did you pay for: («no one» to terminate");
	        paidFor = input.nextLine();
	        whichUser = this.selectUser(input, paidFor);
	        nExpense.addPaidFor(whichUser);
        } while(!paidFor.equalsIgnoreCase("no one"));
        this.currentSplit.addSplit(nExpense);
        
        Start.getExpenseCatalog().addExpense(nExpense);
        // TODO
    }
    */

    public String getPrompt() {
        return prompt;
    }
    
    /* TODO:
    public void setPrompt() {
        if (currentUser == null) {
            this.prompt = ApplicationConfiguration.DEFAULT_PROMPT;
        }
        
        else if (currentSplit == null) {
            this.prompt = currentUser.getName();
        }
        else {
            this.prompt = currentUser.getName() + "." + currentSplit.getPurpose();
        }
    }
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
     * @return
     */
    
    /*TODO: QUANDO O setPrompt for corrigido tem de ser tirar isto
    private User selectOrCreateUser(Scanner input, String name) {
        ArrayList<User> list = null; // Start.getUserCatalog().getUsersWithName(name);
        if (list.isEmpty()) {
            System.out.println("There is no registred user with name " + name + ".");
            if (askYNQuestion(input, "Do you want to create user " + name)) {
                User theUser = currentUser;
                // makeNewUser(input, name); <-- write this method
                User newUser = currentUser;
                currentUser = theUser;
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
            i = input.nextInt();
            return list.get(i);
        }
    }
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

}