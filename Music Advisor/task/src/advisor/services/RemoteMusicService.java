package advisor.services;

import advisor.models.Item;

import java.util.List;

public interface RemoteMusicService {

    List<Item<String>> getFeaturedPlaylist();
    List<Item<String>> getPlaylistByCategory(String category);
    List<Item<String>> getNewReleases();
    List<Item<String>> getCategories();
}
