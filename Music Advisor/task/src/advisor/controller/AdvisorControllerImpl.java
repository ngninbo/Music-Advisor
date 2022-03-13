package advisor.controller;

import advisor.auth.BaseOAuth;
import advisor.models.Item;
import advisor.services.MusicService;
import advisor.services.MusicServiceBuilder;
import advisor.view.Viewer;
import advisor.view.ViewerContext;

import java.util.List;

import static advisor.util.GlobalVariables.*;

public class AdvisorControllerImpl implements AdvisorController {

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
    public boolean processCommand(String command) {

        switch (command) {
            case "exit":
                System.out.println(GOODBYE);
                return true; // TODO: Remove/comment this line before code check
            case "auth":
                auth();
                break;
            default:
                if (accessGranted) {
                    if (command.startsWith(PLAYLISTS)) {
                        String category = command.substring(PLAYLISTS.length()).trim();
                        viewPlaylistByCategory(category);
                    } else {
                        showViewByName(command);
                    }
                } else {
                    System.out.println(PROVIDE_ACCESS_FOR_APPLICATION);
                }
        }
        return false;
    }

    @Override
    public void showViewByName(String viewName) {
        switch (viewName) {
            case NEW_COMMAND:
                viewNewAlbums();
                break;
            case FEATURED_COMMAND:
                viewFeaturePlaylist();
                break;
            case CATEGORIES_COMMAND:
                viewCategories();
                break;
            case NEXT_COMMAND:
            case PREV_COMMAND:
                viewPage(viewName);
                break;
            default:
                System.out.println(UNKNOWN_COMMAND);
        }
    }

    private void viewPage(String command) {
        if (viewer.getStrategy() != null) {
            if (command.equals(NEXT_COMMAND)) {
                viewer.nextPage();
            } else {
                viewer.prevPage();
            }
        } else {
            System.out.println("Please choose a list first!");
        }
    }

    @Override
    public void auth() {
        if (!accessGranted) {
            BaseOAuth oAuth = BaseOAuth.init(host).authorizeAccess();
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
}
