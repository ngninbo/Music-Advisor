package advisor;

import advisor.controller.AdvisorController;
import advisor.controller.AdvisorControllerImpl;
import advisor.controller.Command;

import java.util.Scanner;

import static advisor.util.MessageLogger.log;

public class MusicAdvisor {

    private final AdvisorController advisorController;

    private Command command;

    private String input;

    public static MusicAdvisor init(String[] args) {
        return new MusicAdvisor(new AdvisorControllerImpl(args));
    }

    private MusicAdvisor(AdvisorController advisorController) {
        this.advisorController = advisorController;
    }

    public void start() {
        while (!Command.EXIT.equals(command)) {

            input = new Scanner(System.in).nextLine();
            command = Command.from(input);
            process(command);
        }
    }

    public void process(Command command) {

        switch (command) {
            case EXIT:
                log("GOODBYE");
                break;
            case AUTH:
                advisorController.auth();
                break;
            default:
                execute(command);
        }
    }


    private void execute(Command command) {

        if (!advisorController.isAccessGranted()) {
            log("PROVIDE_ACCESS_FOR_APPLICATION");
            return;
        }

        switch (command) {
            case NEW:
                advisorController.viewNewAlbums();
                break;
            case FEATURED:
                advisorController.viewFeaturePlaylist();
                break;
            case CATEGORIES:
                advisorController.viewCategories();
                break;
            case PLAYLISTS:
                String category = input.substring(Command.PLAYLISTS.getText().length()).trim();
                advisorController.viewPlaylistByCategory(category);
                break;
            case NEXT:
                advisorController.next();
                break;
            case PREV:
                advisorController.prev();
                break;
            default:
                log("UNKNOWN_COMMAND");
        }
    }
}
