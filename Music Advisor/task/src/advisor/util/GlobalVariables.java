package advisor.util;

public class GlobalVariables {

    // Authorization parameters
    public static final String AUTHORIZE_ENDPOINT = "/authorize";
    public static final String TOKEN_ENDPOINT = "/api/token";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String RESPONSE_TYPE = "code";

    // client parameters
    public static final String CLIENT_ID = "<CLIENT_ID>";
    public static final String CLIENT_SECRET = "<CLIENT_SECRET>";
    public static final String REDIRECT_URL = "http://localhost:8080";
    public static final String ACCOUNTS_SPOTIFY_URL = "https://accounts.spotify.com";

    // Server parameters
    public static final int PORT = 8080;
    public static final int IP = 0;
    public static final int MAX_DELAY = 1;

    // Resource requests parameters
    public static final String CATEGORIES_ENDPOINT = "/v1/browse/categories";
    public static final String RELEASE_ENDPOINT = "/v1/browse/new-releases";
    public static final String FEATURED_PLAYLIST_ENDPOINT = "/v1/browse/featured-playlists";

    // Text constants
    public static final String REQUEST_PATH_PARAMETERS = "grant_type=%s&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s";
    public static final String AUTHORIZATION_LINK = "%s?client_id=%s&redirect_uri=%s&response_type=%s";
    public static final String USER_REDIRECT_LINK_MSG = "use this link to request the access code:";
    public static final String WAITING_FOR_CODE_MSG = "waiting for code...";
    public static final String CODE_RECEIVED_MSG = "code received";
    public static final String MAKING_HTTP_REQUEST_FOR_ACCESS_TOKEN = "making http request for access_token...";
    public static final String SUCCESS_MSG = "---SUCCESS---";
    public static final String NO_MORE_PAGES = "No more pages.";
    public static final String CURRENT_PAGE_REPORT = "---PAGE %s OF %s---\n";
    public static final String PROVIDE_ACCESS_FOR_APPLICATION = "Please, provide access for application.";
    public static final String GOODBYE = "---GOODBYE!---";
    public static final String AUTHORIZATION_CODE_NOT_FOUND_TRY_AGAIN_TEXT = "<p style=\"color: red\">Authorization code not found. Try again.</p>";
    public static final String GOT_THE_CODE_RETURN_BACK_TEXT = "<p style=\"color: green\">Got the code. Return back to your program.</p>";
    public static final String UNKNOWN_CATEGORY_NAME = "Unknown category name.";
    public static final String UNKNOWN_COMMAND = "Unknown command";
    public static final String ACCESS_ALREADY_PROVIDED = "Access already provided!";
    public static final String REGEX = "^%s[\\s\\w].*[\\w]$";
    public static final String PLAYLISTS = "playlists";
    public static final String PLAYLISTS_REGEX = String.format(REGEX, PLAYLISTS);
}
