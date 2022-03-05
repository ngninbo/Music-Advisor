package advisor.controller;

import advisor.auth.BaseOAuth;
import advisor.models.Item;
import advisor.services.MusicService;
import advisor.services.MusicServiceBuilder;
import advisor.view.Viewer;
import advisor.view.ViewerContext;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static advisor.util.GlobalVariables.*;

public class AdvisorControllerImpl implements AdvisorController {

    private BaseOAuth oAuth;
    private final String host;
    private MusicService musicService;
    private final String resourceUrl;
    private boolean accessGranted;
    private final int page;

    private final Viewer viewer;

    private List<Item<String>> items;

    {
        viewer = new Viewer();
    }

    public AdvisorControllerImpl(String[] args) {
        host = args[1] != null ? args[1] : ACCOUNTS_SPOTIFY_URL;
        int page = args[5] != null ? Integer.parseInt(args[5]) : 5;
        resourceUrl = args[3];
        this.page = page;
    }

    @Override
    public void processCommand(String command) {
        Pattern pattern = Pattern.compile(PLAYLISTS);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
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
                viewer.nextPage();
                break;
            case "prev":
                viewer.prevPage();
                break;
            default:
                System.out.println(UNKNOWN_COMMAND);
        }
    }

    @Override
    public void auth() {
        if (!accessGranted) {
            oAuth = BaseOAuth.init(host).authorizeAccess();
            accessGranted = oAuth.isCodeReceived();
            musicService = MusicServiceBuilder.init()
                    .withClient()
                    .withResourceUrl(resourceUrl)
                    .withAccessToken(oAuth.getAccessToken())
                    .withTokenType(oAuth.getTokenType())
                    .build();
        } else {
            System.out.println(ACCESS_ALREADY_PROVIDED);
        }
    }

    @Override
    public void viewNewAlbums() {
        items = musicService.getNewReleases();

        viewer.setStrategy(new ViewerContext(items, page)).nextPage();
    }

    @Override
    public void viewFeaturePlaylist() {
        items = musicService.getFeaturedPlaylist();

        viewer.setStrategy(new ViewerContext(items, page)).nextPage();
    }

    @Override
    public void viewCategories() {
        items = musicService.getCategories();

        viewer.setStrategy(new ViewerContext(items, page)).nextPage();
    }

    @Override
    public void viewPlaylistByCategory(String category) {
        items = musicService.getPlaylistByCategory(category);

        if (items.size() > 0) {
            viewer.setStrategy(new ViewerContext(items, page)).nextPage();
        } else {
            System.out.println(UNKNOWN_CATEGORY_NAME);
        }

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
                this.processCommand(command);
            } else {
                System.out.println(PROVIDE_ACCESS_FOR_APPLICATION);
            }
        }
    }
}
