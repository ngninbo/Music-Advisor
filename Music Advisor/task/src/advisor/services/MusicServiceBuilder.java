package advisor.services;

import advisor.client.Client;

public class MusicServiceBuilder {

    private Client client;
    private String resourceUrl;
    private String accessToken;
    private String tokenType;

    private MusicServiceBuilder() {
    }

    public static MusicServiceBuilder init() {
        return new MusicServiceBuilder();
    }

    public MusicServiceBuilder withClient() {
        this.client = Client.getInstance();
        return this;
    }

    public MusicServiceBuilder withResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public MusicServiceBuilder withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public MusicServiceBuilder withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public MusicService build() {
        return new MusicService(client, resourceUrl, accessToken, tokenType);
    }
}
