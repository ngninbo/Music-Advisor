package advisor.auth;

import advisor.client.Client;
import advisor.util.HttpResponseParser;
import advisor.server.Server;
import advisor.util.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class BaseOAuth {

    private final String host;
    private String accessCode;
    private String accessToken;
    private String tokenType;

    private Properties properties;

    {
        try {
            properties = PropertiesLoader.loadProperties("application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BaseOAuth(String host) {
        this.host = host;
    }

    public static BaseOAuth init(String host) {
        return new BaseOAuth(host);
    }

    public BaseOAuth provideAccess() {
        Server server = Server.getServerInstance();
        String uri = host + properties.getProperty("AUTHORIZE_ENDPOINT");
        String authorizationLink = String.format(
                properties.getProperty("AUTHORIZATION_LINK"),
                uri,
                properties.getProperty("CLIENT_ID"),
                properties.getProperty("REDIRECT_URL"),
                properties.getProperty("RESPONSE_TYPE"));

        server.start();
        server.createContext();
        log("USER_REDIRECT_LINK_MSG");
        System.out.println(authorizationLink);
        log("WAITING_FOR_CODE_MSG");

        accessCode = server.getCode();

        while (accessCode == null) {
            accessCode = server.getCode();
        }

        log("CODE_RECEIVED_MSG");
        log("MAKING_HTTP_REQUEST_FOR_ACCESS_TOKEN");
        requestAccessToken();
        log("SUCCESS_MSG");
        server.stop();
        return this;
    }

    private void log(String propertyKey) {
        System.out.println(properties.getProperty(propertyKey));
    }

    private void requestAccessToken() {
        String requestData = String.format(
                properties.getProperty("REQUEST_PATH_PARAMETERS"),
                properties.getProperty("GRANT_TYPE"),
                accessCode,
                properties.getProperty("REDIRECT_URL"),
                properties.getProperty("CLIENT_ID"),
                properties.getProperty("CLIENT_SECRET"));

        String response = Client.getClientInstance()
                .createHttpPostRequest(host + properties.getProperty("TOKEN_ENDPOINT"), requestData)
                .sendHttpRequest();
        extractToken(response);
    }

    private void extractToken(String response) {
        this.accessToken = HttpResponseParser.extractAccessToken(response);
        this.tokenType = HttpResponseParser.extractTokenType(response);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public boolean isCodeReceived() {
        return accessCode != null;
    }
}
