package advisor.models;

import java.util.ArrayList;
import java.util.Collection;

public class ItemList extends ArrayList<Item<String>> {

    public ItemList() {
        super();
    }

    public ItemList(Collection<Item<String>> values) {
        super(values);
    }

    public static ItemList of() {
        return new ItemList();
    }
}
