package advisor.models;

public class Item<T> {

    private final T t;

    public Item(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }
}
