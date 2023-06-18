package advisor.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * This singleton class provide methods useful e.g. for the communication between client and Spotify Web API
 * @author Beauclair Dongmo Ngnintedem
 */
public class Client {

    private static final Client INSTANCE = new Client();
    private final HttpClient httpClient;
    private String responseBody;
    private HttpRequest request;

    {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public static Client getInstance() {
        return Optional.ofNullable(INSTANCE).orElse(new Client());
    }

    /**
     * Create HTTP Request for querying spotify web api
     * @param accessToken access token (user specific)
     * @param tokenType Token type (user specific)
     * @param url Full endpoint url
     * @return Client instance
     */
    public Client createHttpGetRequest(String accessToken, String tokenType, String url) {
        request = HttpRequest.newBuilder()
                .header("Authorization", tokenType + " " + accessToken)
                .uri(URI.create(url))
                .GET()
                .build();

        return INSTANCE;
    }

    /**
     * Send HTTP requests for current created request
     * @return Http Response Body
     */
    public String sendHttpRequest() {

        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    /**
     * Send POST request (used for authorization)
     * @param url Full endpoint url
     * @param requestData Request data must be formatted as key-value pair format e.g. application/x-www-form-urlencoded
     * @return Client instance
     */
    public Client createHttpPostRequest(String url, String requestData) {

        request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestData))
                .build();

        return INSTANCE;
    }
}
