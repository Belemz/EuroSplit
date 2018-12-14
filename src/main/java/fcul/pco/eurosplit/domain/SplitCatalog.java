package fcul.pco.eurosplit.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */


public class SplitCatalog {

    private Map<User, List<Split>> splits_map;


    private static SplitCatalog instance;


    private SplitCatalog() {
        this.splits_map = new HashMap<User, List<Split>>();
    }

    public static SplitCatalog getInstance() {
        if (instance == null) {
            instance = new SplitCatalog();
        }
        return instance;
    }


    public void addSplit(Split split) {
        User user = split.getOwner();
        List<Split> split_list = getUserSplits(user);
        if (split_list == null) {
            split_list = new ArrayList<>();
            this.splits_map.put(user, split_list);
        }
        split_list.add(split);
    }

    public List<Split> getUserSplits(User currentUser) {
        return this.splits_map.get(currentUser);
    }


    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.SplitCatalog.save(this.splits_map);
    }

    public void load() throws FileNotFoundException {
        this.splits_map = fcul.pco.eurosplit.persistence.SplitCatalog.load();
    }

}


