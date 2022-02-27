package advisor.view;

import advisor.models.Item;

import java.util.List;
import java.util.stream.IntStream;

import static advisor.util.GlobalVariables.CURRENT_PAGE_REPORT;
import static advisor.util.GlobalVariables.NO_MORE_PAGES;

public class ViewerContext implements ItemViewStrategy {

    private final List<Item<String>> items;
    private final int page;
    private final int totalPages;
    private int actualPage;
    private int start;
    private int end;
    private boolean isFirstPage;
    private boolean isLastPage;

    {
        this.actualPage = 0;
        this.start = 0;
        this.isFirstPage = true;
        this.isLastPage = false;
    }

    public ViewerContext(List<Item<String>> items, int page) {
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
            System.out.println(NO_MORE_PAGES);
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
            System.out.println(NO_MORE_PAGES);
        }

        isFirstPage = actualPage == 0;
        isLastPage = actualPage == totalPages;
    }

    private void printItemsWithPageReport() {

        IntStream.range(start, end)
                .mapToObj(i -> items.get(i).getT())
                .forEach(System.out::println);

        System.out.printf(CURRENT_PAGE_REPORT, actualPage, totalPages);
    }
}
