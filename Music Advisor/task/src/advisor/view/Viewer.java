package advisor.view;

import advisor.models.Item;
import advisor.models.ItemList;
import advisor.util.MessageLogger;

public class Viewer implements ItemViewStrategy {

    private final ItemList itemList;
    private final int page;
    private final int totalPages;
    private int actualPage;
    private int start;
    private int end;

    public Viewer(ItemList items, int page) {
        this.itemList = items;
        this.page = page;
        int numPages = itemList.size() / page;
        this.totalPages = remainder() == 0 ? numPages : numPages + 1;
        this.end = start + page;
    }

    @Override
    public void prev() {

        if (start >= page) {
            end = start;
            start -= page;
            actualPage -= 1;
            show();
        } else {
            logNorMorePages();
        }
    }


    @Override
    public void next() {

        if (!isLastPage()) {
            start = isFirstPage() ? start : nextEntries();
            int nextEntries = nextEntries();
            end = nextEntries > itemList.size() ? end + remainder() : nextEntries;
            actualPage += 1;
            show();
        } else {
            logNorMorePages();
        }
    }

    private int nextEntries() {
        return start + page;
    }

    private int remainder() {
        return itemList.size() % page;
    }

    private boolean isFirstPage() {
        return actualPage == 0;
    }

    private boolean isLastPage() {
        return actualPage == totalPages;
    }

    private void show() {
        printItems();
        printReport();
    }

    private void printItems() {
        itemList.subList(start, end)
                .stream()
                .map(Item::getT)
                .forEach(System.out::println);
    }

    private void printReport() {
        MessageLogger.log("CURRENT_PAGE_REPORT", actualPage, totalPages);
    }

    private void logNorMorePages() {
        MessageLogger.log("NO_MORE_PAGES");
    }
}
