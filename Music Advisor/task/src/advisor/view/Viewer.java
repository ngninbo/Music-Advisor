package advisor.view;

public class Viewer {

    private ItemViewStrategy strategy;

    public ItemViewStrategy getStrategy() {
        return strategy;
    }

    public Viewer setStrategy(ItemViewStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void prevPage() {
        strategy.prev();
    }

    public void nextPage() {
        strategy.next();
    }
}
