package fcul.pco.eurosplit.main;

import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.UserCatalog;
import fcul.pco.eurosplit.domain.Date;
import fcul.pco.eurosplit.domain.Table;
import java.io.IOException;
import java.util.ArrayList;
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
    // private Split currentSplit;
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
    }

    private void makeNewUser(Scanner input) {
        // TODO 
    }

    private void quit() {
        save();
    }

    private void showUsers() {
        // TODO 
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
        if(!Start.getUserCatalog().hasUserWithName(username.toLowerCase())) {
        	System.out.println("User not found");
        	return;
        };
        
        System.out.print("Email: ");
        String email = input.nextLine();
        if(!Start.getUserCatalog().getUserById(email).getName().toLowerCase().equals(username.toLowerCase())) {
        	System.out.println("Email doesn't match.");
        	return;
        };
        
        this.currentUser = Start.getUserCatalog().getUserById(email);
        
        
        
        //TODO
    }

    private void makeNewSplit(Scanner input) {
        // TODO
    }

    private void selectSplit(Scanner input) {
        // TODO
    }

    private void printBalance() {
        // TODO
    }

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

    private void makeNewExpense(Scanner input) {
        // TODO
    }

    public String getPrompt() {
        return prompt;
    }

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
         */
    }

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
