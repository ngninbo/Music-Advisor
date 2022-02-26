package advisor.controller;

import advisor.auth.BaseOAuth;
import advisor.models.Item;
import advisor.services.MusicService;
import advisor.view.Viewer;
import advisor.view.ViewerContext;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static advisor.util.GlobalVariables.*;

public class AdvisorControllerImpl implements AdvisorController {

    private final BaseOAuth oAuth;
    private final String resourceUrl;
    private boolean accessGranted;
    private final int page;

    private final Viewer viewer;

    private List<Item<String>> items;

    {
        viewer = new Viewer();
    }

    public AdvisorControllerImpl(String[] args) {
        oAuth = BaseOAuth.init(args[1] != null ? args[1] : ACCOUNTS_SPOTIFY_URL);
        int page = args[5] != null ? Integer.parseInt(args[5]) : 5;
        this.resourceUrl = args[3];
        this.page = page;
    }

    @Override
    public void showView(String command) {
        if (Pattern.matches(PLAYLISTS_REGEX, command)) {
            String category = command.substring(PLAYLISTS.length()).trim();
            viewPlaylistByCategory(category);
        } else {
            showViewByName(command);
        }
    }

    @Override
    public void showViewByName(String viewName) {
        switch (viewName) {
            case "new":
                viewNewAlbums();
                break;
            case "featured":
                viewFeaturePlaylist();
                break;
            case "categories":
                viewCategories();
                break;
            case "next":
                viewer.viewNext();
                break;
            case "prev":
                viewer.viewPrev();
                break;
            default:
                System.out.println(UNKNOWN_COMMAND);
        }
    }

    @Override
    public void auth() {
        if (!accessGranted) {
            oAuth.authorizeAccess();
            accessGranted = oAuth.isCodeReceived();
        } else {
            System.out.println(ACCESS_ALREADY_PROVIDED);
        }
    }

    @Override
    public void viewNewAlbums() {
        items = MusicService.init(resourceUrl)
                .getNewReleases(oAuth.getAccessToken(), oAuth.getTokenType());

        viewer.setStrategy(new ViewerContext(items, page)).viewFirst();
    }

    @Override
    public void viewFeaturePlaylist() {
        items = MusicService.init(resourceUrl)
                .getFeaturedPlaylist(oAuth.getAccessToken(), oAuth.getTokenType());

        viewer.setStrategy(new ViewerContext(items, page)).viewFirst();
    }

    @Override
    public void viewCategories() {
        items = MusicService.init(resourceUrl)
                .getCategories(oAuth.getAccessToken(), oAuth.getTokenType());

        viewer.setStrategy(new ViewerContext(items, page)).viewFirst();
    }

    @Override
    public void viewPlaylistByCategory(String category) {
        items = MusicService.init(resourceUrl)
                .getPlaylistByCategory(category, oAuth.getAccessToken(), oAuth.getTokenType());

        viewer.setStrategy(new ViewerContext(items, page)).viewFirst();
    }

    @Override
    public void start() {
        boolean exit = false;
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if ("exit".equals(command)) {
                System.out.println(GOODBYE);
                exit = true; // TODO: Remove/comment this line before code check
            } else if ("auth".equals(command)){
                auth();
            } else if (accessGranted) {
                this.showView(command);
            } else {
                System.out.println(PROVIDE_ACCESS_FOR_APPLICATION);
            }
        }
    }
}
