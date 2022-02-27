package advisor.util;

import advisor.models.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpResponseParser {

    private static JsonObject jsonObject;
    private static List<Item<String>> results;
    /**
     * Extract all playlists from http responses body
     * @param response Body of http response
     * @return List of Items
     */
    public static List<Item<String>> extractPlaylists(String response) {

        results = new ArrayList<>();
        jsonObject = parseHttpResponse(response);
        jsonObject = jsonObject.get("playlists").getAsJsonObject();
        JsonArray items = jsonObject.get("items").getAsJsonArray();
        for (int i = 0; i < items.size(); i++) {
            JsonObject item = (JsonObject) items.get(i);
            String name = item.get("name").getAsString();
            String url = item.get("external_urls").getAsJsonObject().get("spotify").getAsString();
            results.add(new Item<>(name + "\n" + url + "\n"));
        }

        return results;
    }

    /**
     * Parse given http response body for extracting new albums
     * @param body Http response body
     * @return List of new releases
     */
    public static List<Item<String>> extractReleases(String body) {
        results = new ArrayList<>();
        List<String> artistNames;

        jsonObject = parseHttpResponse(body);
        JsonArray items = jsonObject.get("albums").getAsJsonObject().getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            artistNames = new ArrayList<>();
            JsonObject item = (JsonObject) items.get(i);
            String title = item.get("name").getAsString();
            JsonArray artists = item.get("artists").getAsJsonArray();

            for (int j = 0; j < artists.size(); j++) {
                JsonObject artist = (JsonObject) artists.get(j);
                String artistName = artist.get("name").getAsString();
                artistNames.add(artistName);
            }

            String externalUrl = item.get("external_urls").getAsJsonObject().get("spotify").getAsString();

            String[] artistes = new String[artistNames.size()];

            Item<String> newRelease = new Item<>(title + "\n" + Arrays.toString(artistNames.toArray(artistes)) + "\n"
                    + externalUrl + "\n");
            results.add(newRelease);
        }
        return results;
    }

    public static List<Item<String>> extractCategories(String jsonString) {
        results = new ArrayList<>();
        jsonObject = parseHttpResponse(jsonString);
        JsonArray items = jsonObject.get("categories").getAsJsonObject().getAsJsonArray("items");
        IntStream.range(0, items.size())
                .mapToObj(i -> items.get(i).getAsJsonObject())
                .map(item -> item.get("name").getAsString())
                .forEach(name -> results.add(new Item<>(name)));

        return results;
    }

    public static JsonObject parseHttpResponse(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
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

        jsonObject = parseHttpResponse(responseBody);
        JsonArray items = jsonObject.get("categories").getAsJsonObject().getAsJsonArray("items");

        return IntStream.range(0, items.size())
                .mapToObj(i -> items.get(i).getAsJsonObject())
                .collect(Collectors.toMap(item -> item.get("id")
                        .getAsString(), item -> new Item<>(item.get("name")
                        .getAsString()), (a, b) -> b)
                );
    }
}
