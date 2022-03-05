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
    private Map<String, Item<String>> categoryMap;

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

        responseBody = client.createHttpRequest(accessToken, tokenType, resourceUrl + CATEGORIES_ENDPOINT)
                .sendHttpRequest();

        return HttpResponseParser.extractCategoryMap(responseBody);
    }

    @Override
    public List<Item<String>> getNewReleases() {

        responseBody = client.createHttpRequest(accessToken, tokenType, resourceUrl + RELEASE_ENDPOINT).sendHttpRequest();

        return HttpResponseParser.extractReleases(responseBody);
    }

    @Override
    public List<Item<String>> getFeaturedPlaylist() {

        responseBody = client.createHttpRequest(accessToken, tokenType, resourceUrl + FEATURED_PLAYLIST_ENDPOINT)
                .sendHttpRequest();

        return HttpResponseParser.extractPlaylists(responseBody);
    }

    @Override
    public List<Item<String>> getPlaylistByCategory(String category) {
        Optional<String> id = getCategoryId(category);
        if (id.isPresent()) {
            responseBody = client.createHttpRequest(accessToken, 
                    tokenType, 
                    String.format("%s/%s/%s", resourceUrl + CATEGORIES_ENDPOINT, id.get(), PLAYLISTS)
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
        if (categoryMap == null) {
            categoryMap = getCategoryMap();
        }

        return categoryMap.entrySet()
                .stream()
                .filter(el -> category.equalsIgnoreCase(el.getValue().getT()))
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
