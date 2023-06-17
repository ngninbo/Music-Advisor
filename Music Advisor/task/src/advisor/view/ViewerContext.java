package advisor.view;

public class ViewerContext {

    private ItemViewStrategy strategy;

    public ItemViewStrategy getStrategy() {
        return strategy;
    }

    public ViewerContext setStrategy(ItemViewStrategy strategy) {
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
