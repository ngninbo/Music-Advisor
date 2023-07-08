package advisor.util;

import advisor.models.Item;
import advisor.models.ItemList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseParser {

    public static final String NAME = "name";

    /**
     * Extract all playlists from http responses body
     *
     * @param response Body of http response
     * @return List of Items
     */
    public static ItemList extractPlaylists(String response) {
        return parseHttpResponseAndGetItems(response, "playlists")
                .asList()
                .stream()
                .map(JsonElement::getAsJsonObject)
                .map(HttpResponseParser::getPlaylist)
                .collect(Collectors.toCollection(ItemList::new));
    }

    /**
     * Parse given http response body for extracting new albums
     *
     * @param responseBody Http response body
     * @return List of new releases
     */
    public static ItemList extractReleases(String responseBody) {
        return parseHttpResponseAndGetItems(responseBody, "albums")
                .asList()
                .stream()
                .map(JsonElement::getAsJsonObject)
                .map(HttpResponseParser::getNewRelease)
                .collect(Collectors.toCollection(ItemList::new));
    }

    private static String getExternalUrl(JsonObject jsonObject) {
        return jsonObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    }

    public static JsonObject parseHttpResponse(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }

    public static JsonArray parseHttpResponseAndGetItems(String responseBody, String key) {
        return parseHttpResponse(responseBody)
                .get(key)
                .getAsJsonObject()
                .getAsJsonArray("items");
    }

    public static boolean httpResponseBodyContainsError(String httpResponseBody) {
        return parseHttpResponse(httpResponseBody).has("error");
    }

    public static String getErrorMessage(String httpResponseBody) {
        return parseHttpResponse(httpResponseBody).get("error").getAsJsonObject().get("message").getAsString();
    }

    public static String extractAccessToken(String httpResponseBody) {
        return parseHttpResponse(httpResponseBody).get("access_token").getAsString();
    }

    public static String extractTokenType(String httpResponseBody) {
        return parseHttpResponse(httpResponseBody).get("token_type").getAsString();
    }

    public static Map<String, Item<String>> extractCategoryMap(String responseBody) {
        return parseHttpResponseAndGetItems(responseBody, "categories")
                .asList()
                .stream()
                .map(JsonElement::getAsJsonObject)
                .collect(Collectors.toMap(item -> item.get("id").getAsString(),
                        item -> new Item<>(item.get(NAME).getAsString()), (a, b) -> b));
    }

    private static Item<String> getPlaylist(JsonObject playlist) {
        String name = playlist.get(NAME).getAsString();
        String url = getExternalUrl(playlist);
        return new Item<>(String.format("%s\n%s\n", name, url));
    }

    private static Item<String> getNewRelease(JsonObject album) {
        String title = album.get(NAME).getAsString();
        List<String> artists = album.get("artists").getAsJsonArray().asList()
                .stream()
                .map(JsonElement::getAsJsonObject)
                .map(artist -> artist.get(NAME).getAsString())
                .collect(Collectors.toList());

        String externalUrl = getExternalUrl(album);
        return new Item<>(String.format("%s\n%s\n%s\n", title, artists, externalUrl));
    }
}
