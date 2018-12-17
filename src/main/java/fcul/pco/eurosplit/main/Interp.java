package fcul.pco.eurosplit.main;

import fcul.pco.eurosplit.domain.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Allows to use the prompt.
 *
 * @author Cláudia Belém
 * @author Fábio Neves
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
            case "delete":
                deleteCatalogs();
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
        System.out.println("delete: deletes current catalogs");
    }

    /**
     * Command to introduce a new user.
     *
     * @param input
     */
    private void makeNewUser(Scanner input) {
        System.out.print("User name: ");
        String nName = input.nextLine();
        System.out.print("Email address: ");
        String nEmail = input.nextLine();

        if (Start.getUserCatalog().hasUserWithId(nEmail)) {
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
     *
     * @param input
     * @param newName
     * @Overload
     */
    private void makeNewUser(Scanner input, String newName) {
        System.out.print("Email address: ");
        String newEmail = input.nextLine();

        if (Start.getUserCatalog().hasUserWithId(newEmail)) {
            System.out.println("A user with this email address already exists.");
        } else {
            User nUser = new User(newName, newEmail);
            this.currentUser = nUser;

            //adds a User instance to Start class UserCatalog instance.
            Start.getUserCatalog().addUser(nUser);
            this.setPrompt();
        }

    }

    ;

    /**
     * Calls Start.getAllUsers from within Start class in order
     * to print Start.userCatalog instance users in table form.
     */
    private void showUsers() {
        System.out.println(Start.getUserCatalog().getAllUsers());
    }

    /**
     * Saves the current session catalogs and exits the loop.
     *
     * @see {@link #save()}
     */
    private void quit() {
        save();
    }


    /**
     * Current user is replaced on a successfull login.
     * Not sentitive to case for either email or name.
     * Additional methods were created in UserCatalog to get matching names.
     *
     * @param input
     */
    private void login(Scanner input) {
        User selectedUser;
        Boolean isLoggedIn;

        System.out.print("Username: ");
        String username = input.nextLine();

        //verifies if user exists in Start.userCatalog
        selectedUser = this.selectOrCreateUser(input, username);

        if (selectedUser != null) {
            try {
                isLoggedIn = selectedUser.getName().equals(this.currentUser.getName());
            } catch (NullPointerException exception) {
                isLoggedIn = false;
            }

            if (!isLoggedIn) {
                // to avoid asking again for email when failed login name
                System.out.print("Email: ");
                String email = input.nextLine();
                // verifies if email corresponds to user in Start.userCatalog

                if (selectedUser.getEmail().equals(email) &&
                        Start.getUserCatalog().hasUserWithId(email)) {

                    this.currentUser = Start.getUserCatalog().getUserById(email);
                } else {
                    System.out.println("Email doesn't match.");
                }
            }

        }


        this.setPrompt();
    }

    /**
     * Replaces this.currentSplit with nSplit, with logged in user as owner and event set by request.
     * Replaces singleton SplitCatalog instance with this.currentSplit.
     *
     * @param input received from the user input (through Scanner)
     */
    private void makeNewSplit(Scanner input) {
        if (secure_procedure(0)) {
            return;
        }

        System.out.println("For what event is this split ? (i.e. «trip to Madrid», «house expenses», etc...)");
        String event = input.nextLine();

        this.currentSplit = new Split(this.currentUser, event);
        this.setPrompt();

        SplitCatalog.getInstance().addSplit(this.currentSplit);


    }

    /**
     * Selects and assigns created split to currentSplit attribute.
     *
     * @param input
     */
    private void selectSplit(Scanner input) {
        if (this.secure_procedure(0)) {
            return;
        }

        System.out.print("Name of split's owner ? ");
        String splitowner = input.nextLine();

        User selectedUser = selectUser(input, splitowner);

        List<Split> allSplits = Start.getSplitCatalog().getUserSplits(selectedUser);
        if (allSplits == null) {
            System.out.println(selectedUser.getName() + " has no split yet.");
            this.setPrompt();
        } else {

            for (int i = 0; i < allSplits.size(); i++) {
                System.out.println((i + 1) + " " + allSplits.get(i).getEvent());
            }

            System.out.println("Select a split number:");

            int index = Integer.parseInt(input.nextLine()) - 1;

            try {
                this.currentSplit = allSplits.get(index);
                this.setPrompt();

            } catch (IndexOutOfBoundsException exception) {
                System.err.println("Select a valid split number.");
                selectSplit(input);
            }

        }

    }

    /**
     * Prints the balance of every user participating in a split.
     */
    private void printBalance() {
        if (this.secure_procedure(1)) {
            return;
        }

        int numberOfUsersPaidFor;
        int debitAmmount;
        int debitAmmountRemainder;
        int userBalanceUpdate;


        HashMap<User, Integer> usersBalance = new HashMap<>();
        for (Expense nextExp : this.currentSplit.getExpenses()) {

            //this adds the creator of the expense.
            usersBalance.putIfAbsent(nextExp.getUser(), 0);

            numberOfUsersPaidFor = nextExp.getPaidFor().size();
            debitAmmount = Math.floorDiv(nextExp.getValue(), numberOfUsersPaidFor);
            debitAmmountRemainder = Math.floorMod(nextExp.getValue(), numberOfUsersPaidFor);


            // in order to make a list of the balances we have created a map with keys user and int (balance).
            // Users not present are added with a balance of zero.
            for (User paidFor : nextExp.getPaidFor()) {
                usersBalance.putIfAbsent(paidFor, 0);
                userBalanceUpdate = usersBalance.get(paidFor) - debitAmmount;
                usersBalance.replace(paidFor, userBalanceUpdate);
            }

            // simplified code to share the remainder of the expense.
            ArrayList<User> userList = new ArrayList<User>();
            userList.addAll(nextExp.getPaidFor());
            for (int i = 0; i <= 1; i++) {
                //to prevent issues when list gets reduced to size == 1
                if (userList.size() != 1) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, userList.size() - 1);
                    User randomPick = userList.get(randomNum);
                    userBalanceUpdate = usersBalance.get(randomPick) - debitAmmountRemainder;
                    usersBalance.replace(randomPick, userBalanceUpdate);
                    userList.remove(randomPick);
                } else {
                    User floorPick = userList.get(0);
                    userBalanceUpdate = usersBalance.get(floorPick) - debitAmmountRemainder;
                    usersBalance.replace(floorPick, userBalanceUpdate);
                }

            }

            //the paying user is added (if it has not appeared before) and the corresponding value is incremented.
            userBalanceUpdate = (usersBalance.get(nextExp.getUser()) + nextExp.getValue());
            usersBalance.replace(nextExp.getUser(), userBalanceUpdate);
        }

        ArrayList<List<String>> table = new ArrayList<List<String>>();

        for (User u : usersBalance.keySet()) {
            ArrayList<String> tableRow = new ArrayList<>();

            tableRow.add(u.getName());
            tableRow.add(usersBalance.get(u).toString());


            table.add(tableRow);
        }

        try {
            System.out.println(Table.tableToString(table));
        } catch (IndexOutOfBoundsException e) {
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
     *
     * @param input
     */
    private void makeNewExpense(Scanner input) {
        if (this.secure_procedure(1)) {
            return;
        }

        System.out.print("Expense made by you (" + this.currentUser.getName() + "). What did you pay for ? ");
        String theItem = input.nextLine();

        System.out.print("How much did you pay? ");
        int theValue = 0;
        while (!input.hasNextInt()) {
            System.err.print("Expense value must be an integer.\nTry again.");
            input.next();
        }

        theValue = input.nextInt();

        Expense nExpense = new Expense(theItem, theValue, currentUser);

        input.nextLine(); //there was an issue were input was not flushed.

        String paidFor;
        User whichUser;
        //breaks out of loop when no one is prompted
        while (true) {
            System.out.print("Who did you pay for: ('no one' to terminate) ");
            paidFor = input.nextLine();
            //if constraints are not employed printBalance will try to divide by 0.
            if (paidFor.equalsIgnoreCase("no one")) {
                if (nExpense.getPaidFor().size() == 0) {
                    System.out.println("Add at least one participating user for the Expense.");
                    continue;
                } else {
                    break;
                }
            }

            whichUser = this.selectUser(input, paidFor);
            if (whichUser == null) {
                if (nExpense.getPaidFor().size() == 0) {
                    System.out.println("Add at least one participating user for the Expense.");
                    continue;
                } else {
                    break;
                }
            }

            nExpense.addPaidFor(whichUser);
        }
        this.currentSplit.addExpense(nExpense);

        Start.getExpenseCatalog().addExpense(nExpense);
    }

    /**
     * Retrieves a new command.
     *
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
        } else if (currentSplit == null) {
            this.prompt = currentUser.getName();
        } else {
            this.prompt = currentUser.getName() + "." + currentSplit.getEvent();
        }
    }

    /**
     * @return String
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
            System.err.println("There is no registered user with name " + name + ".");
            if (askYNQuestion(input, "Do you want to create user " + name)) {
                makeNewUser(input, name);
                User newUser = currentUser;
                //currentUser = theUser;
                setPrompt();
                return newUser;
            } else {
                System.err.println("User not found.");
                return null;
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

                    list.get(i);
                } catch (IndexOutOfBoundsException e1) {
                    System.err.println("Use an int within choice range...");
                    error = true;
                } catch (Exception e2) {
                    System.err.println("Please use an int...");
                    error = true;
                }
            } while (error);

            return list.get(i);
        }
    }

    /**
     * Selects a user from the current session UserCatalog
     *
     * @param input
     * @param name
     * @return User
     */
    private User selectUser(Scanner input, String name) {
        if (name.equalsIgnoreCase("no one")) {
            return null;
        }

        List<User> list = Start.getUserCatalog().getUsersWithName(name);
        if (list.size() == 1) {
            return list.get(0);
        }

        int k;
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + " " + list.get(i));
            }
            System.out.print("Select a user: ");
            k = Integer.parseInt(input.nextLine());
        } else {
            System.err.println("User not found.");
            System.out.print("Name: ('no one' to terminate) ");
            name = input.nextLine();
            return selectUser(input, name);
        }
        return list.get(k);
    }


    /**
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
     *
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


    /**
     * Deletes the files saved as Catalogs from UserCatalog class.
     *
     * @throws IOException
     */
    private static void deleteCatalogs() {
        try {
            File f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.EXPENSES_CATALOG_FILENAME);
            f.delete();
            f.createNewFile();
            f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.USER_CATALOG_FILENAME);
            f.delete();
            f.createNewFile();
            f = new File(ApplicationConfiguration.ROOT_DIRECTORY
                    + "/"
                    + ApplicationConfiguration.SPLIT_CATALOG_FILENAME);
            f.delete();
            f.createNewFile();

            System.out.println("The catalogs have been deleted.");
        } catch (IOException e) {
            System.err.println("There was an error creating the new files.");
        }
    }

}