package advisor.view;

import advisor.models.Item;
import advisor.util.MessageProperties;

import java.util.List;
import java.util.stream.IntStream;

public class Viewer implements ItemViewStrategy {

    private final List<Item<String>> items;
    private final int page;
    private final int totalPages;
    private int actualPage;
    private int start;
    private int end;
    private boolean isFirstPage;
    private boolean isLastPage;

    {
        this.isFirstPage = true;
    }

    public Viewer(List<Item<String>> items, int page) {
        this.items = items;
        this.page = page;
        int numPages = items.size() / page;
        this.totalPages = items.size() % page == 0 ? numPages : numPages + 1;
        this.end = start + page;
    }

    @Override
    public void prev() {

        if (start >= page) {
            end = start;
            start -= page;
            actualPage -= 1;
            isFirstPage = actualPage == 0;
            isLastPage = actualPage == totalPages;
            printItemsWithPageReport();
        } else {
            logNorMorePages();
        }
    }


    @Override
    public void next() {

        if (!isLastPage) {
            start = isFirstPage ? start : start + page;
            int nextEntries = start + page;
            end = nextEntries > items.size() ? end + items.size() % page : nextEntries;
            actualPage += 1;
            printItemsWithPageReport();
        } else {
            logNorMorePages();
        }

        isFirstPage = actualPage == 0;
        isLastPage = actualPage == totalPages;
    }

    private void printItemsWithPageReport() {

        IntStream.range(start, end)
                .mapToObj(i -> items.get(i).getT())
                .forEach(System.out::println);

        System.out.println(MessageProperties.getMessage("CURRENT_PAGE_REPORT", actualPage, totalPages));
    }

    private void logNorMorePages() {
        System.out.println(MessageProperties.getMessage("NO_MORE_PAGES"));
    }
}
