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
        return HttpResponseParser.extractCategoryMap(getClient(getUrl("CATEGORIES_ENDPOINT")).sendHttpRequest());
    }

    @Override
    public List<Item<String>> getNewReleases() {
        return HttpResponseParser.extractReleases(getClient(getUrl("RELEASE_ENDPOINT")).sendHttpRequest());
    }


    @Override
    public List<Item<String>> getFeaturedPlaylist() {
        return HttpResponseParser.extractPlaylists(getClient(getUrl("FEATURED_PLAYLIST_ENDPOINT"))
                .sendHttpRequest());
    }

    @Override
    public List<Item<String>> getPlaylistByCategory(String category) {
        Optional<String> id = getCategoryId(category);
        if (id.isPresent()) {
            String responseBody = getClient(String.format("%s/%s/playlists", getUrl("CATEGORIES_ENDPOINT"), id.get()))
                    .sendHttpRequest();
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

    private Client getClient(String endpoint) {
        return client.createHttpGetRequest(accessToken, tokenType, endpoint);
    }

    private String getUrl(String endpointKey) {
        return resourceUrl + properties.getProperty(endpointKey);
    }
}
