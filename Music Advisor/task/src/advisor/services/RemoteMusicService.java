package advisor.services;

import advisor.models.ItemList;

public interface RemoteMusicService {

    ItemList getFeaturedPlaylist();
    ItemList getPlaylistByCategory(String category);
    ItemList getNewReleases();
    ItemList getCategories();
}
