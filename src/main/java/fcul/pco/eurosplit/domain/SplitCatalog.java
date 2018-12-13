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

    public void addSplitList(User user, List<Split> split_list) {
        this.splits.put(user, split_list);   // TODO confirmar este método
    }

    public void addSplit(User user, Split split) {   // TODO FALTA RESOLVER SE O UTILIZADOR NÃO EXISTIR
        List<Split> split_list = getUserSplits(user);

        if (split_list == null) {
            split_list = new ArrayList<>();
            splits.put(user, split_list);
        }
        split_list.add(split);
    }

    public List<Split> getUserSplits(User currentUser) {
        return this.splits.get(currentUser);
    }


    public void save() throws IOException {
        fcul.pco.eurosplit.persistence.SplitCatalog.save(this.splits);
    }

    public void load() throws FileNotFoundException {
        this.splits = fcul.pco.eurosplit.persistence.SplitCatalog.load();
    }

}


