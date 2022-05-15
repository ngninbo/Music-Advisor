package advisor.services;

import advisor.client.Client;
import advisor.models.Item;
import advisor.util.HttpResponseParser;
import advisor.util.PropertiesLoader;

import java.io.IOException;
import java.util.*;

/**
 * Service class implementing methods for getting list of categories, new albums, playlists for the users spotify account
 * using the spotify web resourceUrl
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class MusicService implements RemoteMusicService {
    
    private String responseBody;
    private final Client client;
    private final String resourceUrl;
    private final String accessToken;
    private final String tokenType;
    private Map<String, Item<String>> categoryMap = Map.of();

    private Properties properties;

    {
        try {
            properties = PropertiesLoader.loadProperties("application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MusicService(Client client, String resourceUrl, String accessToken, String tokenType) {
        this.client = client;
        this.resourceUrl = resourceUrl;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
    
    @Override
    public List<Item<String>> getCategories() {

        return new ArrayList<>(getCategoryMap().values());
    }

    public Map<String, Item<String>> getCategoryMap() {

        responseBody = client
                .createHttpRequest(
                        accessToken,
                        tokenType,
                        getUrl("CATEGORIES_ENDPOINT"))
                .sendHttpRequest();

        return HttpResponseParser.extractCategoryMap(responseBody);
    }

    @Override
    public List<Item<String>> getNewReleases() {

        responseBody = client.createHttpRequest(accessToken, tokenType, getUrl("RELEASE_ENDPOINT")).sendHttpRequest();

        return HttpResponseParser.extractReleases(responseBody);
    }


    @Override
    public List<Item<String>> getFeaturedPlaylist() {

        responseBody = client.createHttpRequest(accessToken, tokenType, getUrl("FEATURED_PLAYLIST_ENDPOINT"))
                .sendHttpRequest();

        return HttpResponseParser.extractPlaylists(responseBody);
    }

    @Override
    public List<Item<String>> getPlaylistByCategory(String category) {
        Optional<String> id = getCategoryId(category);
        if (id.isPresent()) {
            responseBody = client.createHttpRequest(accessToken,
                    tokenType,
                    String.format("%s/%s/playlists", getUrl("CATEGORIES_ENDPOINT"), id.get())
                    ).sendHttpRequest();
            if (HttpResponseParser.httpResponseBodyContainsError(responseBody)) {
                System.out.println(HttpResponseParser.getErrorMessage(responseBody));
            } else {
                return HttpResponseParser.extractPlaylists(responseBody);
            }
        }

        return List.of();
    }

    /**
     * Find category id of given playlist name before doing http requests
     *
     * @param category category name
     * @return Optional Category id
     */
    private Optional<String> getCategoryId(String category) {
        if (categoryMap.isEmpty()) {
            categoryMap = getCategoryMap();
        }

        return categoryMap.entrySet()
                .stream()
                .filter(el -> category.equalsIgnoreCase(el.getValue().getT()))
                .findFirst()
                .map(Map.Entry::getKey);
    }

    private String getUrl(String endpointKey) {
        return resourceUrl + properties.getProperty(endpointKey);
    }
}
