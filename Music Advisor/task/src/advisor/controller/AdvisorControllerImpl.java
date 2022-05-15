package advisor.controller;

import advisor.auth.BaseOAuth;
import advisor.models.Item;
import advisor.services.MusicService;
import advisor.services.MusicServiceBuilder;
import advisor.util.ArgumentMapper;
import advisor.util.PropertiesLoader;
import advisor.view.Viewer;
import advisor.view.ViewerContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AdvisorControllerImpl implements AdvisorController {

    private final String host;
    private MusicService musicService;
    private final String resourceUrl;
    private boolean accessGranted;
    private final int page;
    private final Viewer viewer;
    private Properties properties;

    public static final String NEXT_COMMAND = "next";
    public static final String PREV_COMMAND = "prev";
    public static final String CATEGORIES_COMMAND = "categories";
    public static final String FEATURED_COMMAND = "featured";
    public static final String NEW_COMMAND = "new";

    {
        viewer = new Viewer();
        try {
            properties = PropertiesLoader.loadProperties("application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AdvisorControllerImpl(String[] args) {
        Map<String, String> argMap = ArgumentMapper.convertToMap(args);
        host = argMap.getOrDefault("access", properties.getProperty("ACCOUNTS_SPOTIFY_URL"));
        resourceUrl = argMap.get("resource");
        this.page = argMap.containsKey("page") ?
                Integer.parseInt(argMap.get("page")) :
                Integer.parseInt(properties.getProperty("DEFAULT_PAGE"));
    }

    @Override
    public boolean processCommand(String command) {

        switch (command) {
            case "exit":
                log("GOODBYE");
                return true; // TODO: Remove/comment this line before code check
            case "auth":
                auth();
                break;
            default:
                execute(command);
        }
        return false;
    }

    private void execute(String command) {
        if (accessGranted) {
            String playlists = properties.getProperty("PLAYLISTS");
            if (command.startsWith(playlists)) {
                String category = command.substring(playlists.length()).trim();
                viewPlaylistByCategory(category);
            } else {
                showViewByName(command);
            }
        } else {
            log("PROVIDE_ACCESS_FOR_APPLICATION");
        }
    }

    private void log(String propertyKey) {
        System.out.println(properties.getProperty(propertyKey));
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
                log("UNKNOWN_COMMAND");
        }
    }


    @Override
    public void auth() {
        if (!accessGranted) {
            BaseOAuth oAuth = BaseOAuth.init(host).provideAccess();
            accessGranted = oAuth.isCodeReceived();
            musicService = MusicServiceBuilder.init()
                    .withClient()
                    .withResourceUrl(resourceUrl)
                    .withAccessToken(oAuth.getAccessToken())
                    .withTokenType(oAuth.getTokenType())
                    .build();
        } else {
            log("ACCESS_ALREADY_PROVIDED");
        }
    }

    @Override
    public void viewNewAlbums() {
        viewer.setStrategy(new ViewerContext(musicService.getNewReleases(), page))
                .nextPage();
    }

    @Override
    public void viewFeaturePlaylist() {
        viewer.setStrategy(new ViewerContext(musicService.getFeaturedPlaylist(), page))
                .nextPage();
    }

    @Override
    public void viewCategories() {
        viewer.setStrategy(new ViewerContext(musicService.getCategories(), page))
                .nextPage();
    }

    @Override
    public void viewPlaylistByCategory(String category) {
        List<Item<String>> items = musicService.getPlaylistByCategory(category);

        if (items.size() > 0) {
            viewer.setStrategy(new ViewerContext(items, page)).nextPage();
        } else {
            log("UNKNOWN_CATEGORY_NAME");
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
}
