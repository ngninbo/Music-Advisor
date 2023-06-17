package advisor.controller;

import advisor.util.MessageProperties;

import java.util.Arrays;

public enum Command {
    AUTH,
    NEW,
    FEATURED,
    CATEGORIES,
    PLAYLISTS,
    NEXT,
    PREV,
    EXIT,
    UNKNOWN_COMMAND;

    public static Command from(String input) {

        if (input.startsWith(PLAYLISTS.getText())) {
            return PLAYLISTS;
        }

        return Arrays.stream(values())
                .filter(command -> command.getText().equalsIgnoreCase(input))
                .findFirst().orElse(UNKNOWN_COMMAND);
    }

    public String getText() {
        return MessageProperties.getMessage(name());
    }
}
