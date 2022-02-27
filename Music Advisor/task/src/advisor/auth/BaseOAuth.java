package advisor.auth;

import advisor.client.Client;
import advisor.util.HttpResponseParser;
import advisor.server.Server;

import static advisor.util.GlobalVariables.*;

public class BaseOAuth {

    private final String host;
    private String accessCode;
    private String accessToken;
    private String tokenType;

    protected BaseOAuth(String host) {
        this.host = host;
    }

    public static BaseOAuth init(String host) {
        return new BaseOAuth(host);
    }

    public void authorizeAccess() {
        Server server = Server.getServerInstance();
        String uri = host + AUTHORIZE_ENDPOINT;
        String authorizationLink = String.format(AUTHORIZATION_LINK,
                uri, CLIENT_ID, REDIRECT_URL, RESPONSE_TYPE);

        server.start();
        server.createContext();
        System.out.println(USER_REDIRECT_LINK_MSG);
        System.out.println(authorizationLink);
        System.out.println(WAITING_FOR_CODE_MSG);

        accessCode = server.getCode();

        while (accessCode == null) {
            accessCode = server.getCode();
        }

        System.out.println(CODE_RECEIVED_MSG);
        System.out.println(MAKING_HTTP_REQUEST_FOR_ACCESS_TOKEN);
        requestAccessToken();
        System.out.println(SUCCESS_MSG);
        server.stop();
    }

    private void requestAccessToken() {
        String requestData = String.format(REQUEST_PATH_PARAMETERS,
                GRANT_TYPE, accessCode, REDIRECT_URL, CLIENT_ID, CLIENT_SECRET);

        String response = Client.getClientInstance().createHttpPostRequest(host + TOKEN_ENDPOINT, requestData)
                .sendHttpRequest();
        extractToken(response);
    }

    private void extractToken(String response) {
        setAccessToken(HttpResponseParser.extractAccessToken(response));
        setTokenType(HttpResponseParser.extractTokenType(response));
    }

    public String getAccessToken() {
        return accessToken;
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    private void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isCodeReceived() {
        return accessCode != null;
    }
}
