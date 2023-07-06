package advisor.controller;


/**
 * This is an interface defining several methods for reaction on user input in the view
 * @author Beauclair Dongmo Ngnintedem
 */
public interface AdvisorController {
    boolean isAccessGranted();
    void auth();
    void viewNewAlbums();
    void viewFeaturePlaylist();
    void viewCategories();
    void viewPlaylistByCategory(String category);

    void next();

    void prev();
}
