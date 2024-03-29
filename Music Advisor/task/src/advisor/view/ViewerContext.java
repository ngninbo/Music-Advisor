package advisor.view;

import advisor.util.MessageLogger;

import java.util.Optional;

public class ViewerContext {

    private ItemViewStrategy strategy;

    public ViewerContext setStrategy(ItemViewStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void show() {
        strategy.next();
    }

    public void next() {
        Optional.ofNullable(strategy).ifPresentOrElse(ItemViewStrategy::next, this::printListIsEmpty);
    }

    public void prev() {
        Optional.ofNullable(strategy).ifPresentOrElse(ItemViewStrategy::prev, this::printListIsEmpty);
    }

    private void printListIsEmpty() {
        MessageLogger.log("SELECT_LIST_FIRST");
    }
}
