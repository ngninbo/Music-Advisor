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
        this.actualPage = 1;
        this.start = 0;
    }

    public ViewerContext(List<Item<String>> items, int page) {
        this.items = items;
        this.page = page;
        int numPages = items.size() / page;
        this.totalPages = items.size() % page == 0 ? numPages : numPages + 1;
        this.end = start + page;
    }

    @Override
    public void viewFirst() {
        printItemsWithPageReport(start, end, actualPage, totalPages);
        this.isFirstPage = true;
        this.isLastPage = false;
    }

    @Override
    public void viewPrev() {
        if (!isFirstPage) {
            end = start;
            start -= page;
            actualPage -= 1;
            isFirstPage = actualPage == 1;
            isLastPage = actualPage == totalPages;
            printItemsWithPageReport(start, end, actualPage, totalPages);
        } else {
            System.out.println(NO_MORE_PAGES);
            isFirstPage = actualPage == 1;
            isLastPage = actualPage == totalPages;
        }
    }

    @Override
    public void viewNext() {

        if (!isLastPage) {
            start += page;
            int nextEntries = start + page;
            end = nextEntries > items.size() ? end + items.size() % page : nextEntries;
            actualPage += 1;
            printItemsWithPageReport(start, end, actualPage, totalPages);
        } else {
            System.out.println(NO_MORE_PAGES);
        }
        isFirstPage = actualPage == 1;
        isLastPage = actualPage == totalPages;

    }

    private void printItemsWithPageReport(int start, int end, int actualPage, int totalPages) {

        IntStream.range(start, end)
                .mapToObj(i -> items.get(i).getT())
                .forEach(System.out::println);

        System.out.printf(CURRENT_PAGE_REPORT, actualPage, totalPages);
    }
}
