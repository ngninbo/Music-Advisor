package advisor.util;

import advisor.models.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpResponseParser {

    private static List<Item<String>> results;

    /**
     * Extract all playlists from http responses body
     *
     * @param response Body of http response
     * @return List of Items
     */
    public static List<Item<String>> extractPlaylists(String response) {

        results = new ArrayList<>();
        JsonArray playlists = parseHttpResponseAndGetItems(response, "playlists");
        for (int i = 0; i < playlists.size(); i++) {
            JsonObject playlist = playlists.get(i).getAsJsonObject();
            String name = playlist.get("name").getAsString();
            String url = getExternalUrl(playlist);
            results.add(new Item<>(String.format("%s\n%s\n", name, url)));
        }

        return results;
    }

    /**
     * Parse given http response body for extracting new albums
     *
     * @param responseBody Http response body
     * @return List of new releases
     */
    public static List<Item<String>> extractReleases(String responseBody) {
        results = new ArrayList<>();
        List<String> artistNames;

        JsonArray albums = parseHttpResponseAndGetItems(responseBody, "albums");

        for (int i = 0; i < albums.size(); i++) {
            JsonObject album = albums.get(i).getAsJsonObject();
            String title = album.get("name").getAsString();
            JsonArray artists = album.get("artists").getAsJsonArray();

            artistNames = new ArrayList<>();
            for (int j = 0; j < artists.size(); j++) {
                JsonObject artist = artists.get(j).getAsJsonObject();
                String artistName = artist.get("name").getAsString();
                artistNames.add(artistName);
            }

            String externalUrl = getExternalUrl(album);
            Item<String> newRelease = new Item<>(String.format("%s\n%s\n%s\n",
                    title, artistNames, externalUrl));
            results.add(newRelease);
        }
        return results;
    }

    private static String getExternalUrl(JsonObject jsonObject) {
        return jsonObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    }

    public static JsonObject parseHttpResponse(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }

    public static JsonArray parseHttpResponseAndGetItems(String responseBody, String key) {
        return JsonParser.parseString(responseBody)
                .getAsJsonObject()
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

        JsonArray categories = parseHttpResponseAndGetItems(responseBody, "categories");

        return IntStream.range(0, categories.size())
                .mapToObj(i -> categories.get(i).getAsJsonObject())
                .collect(Collectors.toMap(
                        item -> item.get("id").getAsString(),
                        item -> new Item<>(item.get("name").getAsString()),
                        (a, b) -> b)
                );
    }
}
