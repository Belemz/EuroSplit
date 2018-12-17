package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to create instances that store split lists linked to the
 * users that created them.
 * @author Cláudia Belém
 * @author Fábio Neves
 */


public class SplitCatalog {
	
    private Map<User, List<Split>> splits_map;


    private static SplitCatalog instance;

    /**
     * Constructor to SplitCatalog. Should be accessed using public getInstance method.
     */
    private SplitCatalog() {
        this.splits_map = new HashMap<User, List<Split>>();
    }
    
    /**
     * Used to either access the constructor of this class 
     * or retrieve the current instance.
     * @return SplitCatalog.
     */
    public static SplitCatalog getInstance() {
        if (instance == null) {
            instance = new SplitCatalog();
        }
        return instance;
    }

    /**
     * Adds a split to SplitCatalog split list.
     * @param split  the list of expenses to be added to SplitCatalog instance.
     */
    public void addSplit(Split split) {
        User user = split.getOwner();
        List<Split> split_list = getUserSplits(user);
        if (split_list == null) {
            split_list = new ArrayList<>();
            this.splits_map.put(user, split_list);
        }
        split_list.add(split);
    }
    
    /**
     * Retrieves the splits within SplitCatalog which key corresponds to the logged in user.
     * @param currentUser
     * @return List<Split>
     */
    public List<Split> getUserSplits(User currentUser) {
        return this.splits_map.get(currentUser);
    }

    /**
     * Saves the current instance of SplitCatalog in the default path assigned in fcul.pco.main.AplicationConfiguration.
     * @throws IOException
     */
    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.SplitCatalog.save(this.splits_map);
    }
    
    /**
     * Loads a new instance of SplitCatalog from the default path assigned in fcul.pco.main.AplicationConfiguration.
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        this.splits_map = fcul.pco.eurosplit.persistence.SplitCatalog.load();
    }

}


