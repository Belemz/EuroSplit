package fcul.pco.eurosplit.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 */


public class SplitCatalog {

    private Map<User, List<Split>> splits;


    private static SplitCatalog instance;


    private SplitCatalog() {
        this.splits = new HashMap<User, List<Split>>();
    }

    public static SplitCatalog getInstance() {
        if (instance == null) {
            instance = new SplitCatalog();
        }
        return instance;
    }


    public List<Split> getUserSplits(User currentUser) {

        return this.splits.get(currentUser);
    }


    public void save() {
        fcul.pco.eurosplit.persistence.SplitCatalog.save(this.splits);
    }
}


