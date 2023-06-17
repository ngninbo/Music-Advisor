package advisor.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageProperties {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public static String getMessage(String messageKey) {
        return resourceBundle.getString(messageKey);
    }

    public static String getMessage(String messageKey, Object... args) {
        return MessageFormat.format(getMessage(messageKey), args);
    }
}
