package advisor.controller;

import advisor.auth.BaseOAuth;
import advisor.models.Item;
import advisor.services.MusicService;
import advisor.services.MusicServiceBuilder;
import advisor.util.ArgumentMapper;
import advisor.util.MessageProperties;
import advisor.util.PropertiesLoader;
import advisor.view.ItemViewStrategy;
import advisor.view.ViewerContext;
import advisor.view.Viewer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class AdvisorControllerImpl implements AdvisorController {

    private final String host;
    private MusicService musicService;
    private final String resourceUrl;
    private boolean accessGranted;
    private final int page;
    private final ViewerContext viewerContext;
    private Properties properties;


    {
        viewerContext = new ViewerContext();
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
    public boolean isAccessGranted() {
        return accessGranted;
    }

    @Override
    public void log(String messagesKey) {
        System.out.println(MessageProperties.getMessage(messagesKey));
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
        viewerContext.setStrategy(new Viewer(musicService.getNewReleases(), page)).nextPage();
    }

    @Override
    public void viewFeaturePlaylist() {
        viewerContext.setStrategy(new Viewer(musicService.getFeaturedPlaylist(), page)).nextPage();
    }

    @Override
    public void viewCategories() {
        viewerContext.setStrategy(new Viewer(musicService.getCategories(), page)).nextPage();
    }

    @Override
    public void viewPlaylistByCategory(String category) {
        List<Item<String>> items = musicService.getPlaylistByCategory(category);

        if (items.size() > 0) {
            viewerContext.setStrategy(new Viewer(items, page)).nextPage();
        } else {
            log("UNKNOWN_CATEGORY_NAME");
        }

    }

    @Override
    public void next() {
        Optional.ofNullable(viewerContext.getStrategy()).ifPresentOrElse(ItemViewStrategy::next, this::printListIsEmpty);
    }

    @Override
    public void prev() {
        Optional.ofNullable(viewerContext.getStrategy()).ifPresentOrElse(ItemViewStrategy::prev, this::printListIsEmpty);
    }

    private void printListIsEmpty() {
        log("SELECT_LIST_FIRST");
    }
}
