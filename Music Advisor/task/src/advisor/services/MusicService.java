package advisor.services;

import advisor.client.Client;
import advisor.models.Item;
import advisor.util.HttpResponseParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static advisor.util.GlobalVariables.*;

/**
 * Service class implementing methods for getting list of categories, new albums, playlists for the users spotify account
 * using the spotify web api
 *
 * @author Beauclair Dongmo Ngnintedem
 */
public class MusicService {

    private final String api;
    private static String responseBody;
    private static final Client client;
    private Map<String, Item<String>> categoryMap;

    static {
        client = Client.getClientInstance();
    }

    private MusicService(String api) {
        this.api = api;
    }

    public static MusicService init(String api) {
        return new MusicService(api);
    }

    /**
     * Get list of categories
     *
     * @param accessToken access token (user specific)
     * @param tokenType   token type (user specific)
     * @return List of categories
     */
    public List<Item<String>> getCategories(String accessToken, String tokenType) {

        responseBody = client.createHttpRequest(accessToken, tokenType, api + CATEGORIES_ENDPOINT)
                .sendHttpRequest();

        categoryMap = HttpResponseParser.extractCategoryMap(responseBody);

        return HttpResponseParser.extractCategories(responseBody);
    }

    public Map<String, Item<String>> getCategoryMap(String accessToken, String tokenType) {

        responseBody = client.createHttpRequest(accessToken, tokenType, api + CATEGORIES_ENDPOINT)
                .sendHttpRequest();

        return HttpResponseParser.extractCategoryMap(responseBody);
    }

    public List<Item<String>> getNewReleases(String accessToken, String tokenType) {

        responseBody = client.createHttpRequest(accessToken, tokenType, api + RELEASE_ENDPOINT).sendHttpRequest();

        return HttpResponseParser.extractReleases(responseBody);
    }

    /**
     * Fetch available featured playlists
     *
     * @param accessToken access token
     * @param tokenType   token type
     * @return List of playlists
     */
    public List<Item<String>> getFeaturedPlaylist(String accessToken, String tokenType) {

        responseBody = client.createHttpRequest(accessToken, tokenType, api + FEATURED_PLAYLIST_ENDPOINT)
                .sendHttpRequest();

        return HttpResponseParser.extractPlaylists(responseBody);
    }


    /**
     * Fetch all playlist by given category
     *
     * @param category    category name
     * @param accessToken access Token
     * @param tokenType   token type
     * @return List of playlists
     */
    public List<Item<String>> getPlaylistByCategory(String category, String accessToken, String tokenType) {
        List<Item<String>> playlists = new ArrayList<>();
        Optional<String> categoryId = getCategoryId(category, accessToken, tokenType);
        if (categoryId.isPresent()) {
            responseBody = client.createHttpRequest(accessToken, tokenType, api + CATEGORIES_ENDPOINT
                    + "/" + categoryId.get() + "/playlists").sendHttpRequest();
            if (HttpResponseParser.httpResponseBodyContainsError(responseBody)) {
                System.out.println(HttpResponseParser.getErrorMessage(responseBody));
            } else {
                playlists = HttpResponseParser.extractPlaylists(responseBody);
            }
        } else {
            System.out.println(UNKNOWN_CATEGORY_NAME);
        }
        return playlists;
    }

    /**
     * Find category id of given playlist name before doing http requests
     *
     * @param category category name
     * @return Optional Category id
     */
    private Optional<String> getCategoryId(String category, String accessToken, String tokenType) {
        if (categoryMap == null) {
            categoryMap = getCategoryMap(accessToken, tokenType);
        }

        return categoryMap.entrySet()
                .stream()
                .filter(el -> category.equalsIgnoreCase(el.getValue().getT()))
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
