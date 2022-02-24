package advisor.view;

public class Viewer {

    private ItemViewStrategy strategy;

    public Viewer setStrategy(ItemViewStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void viewFirst() {
        strategy.viewFirst();
    }

    public void viewPrev() {
        strategy.viewPrev();
    }

    public void viewNext() {
        strategy.viewNext();
    }
}
