package advisor.util;

public class MessageLogger {

    public static void log(String messagesKey) {
        System.out.println(MessageProperties.getMessage(messagesKey));
    }

    public static void log(String messagesKey, Object... args) {
        System.out.println(MessageProperties.getMessage(messagesKey, args));
    }
}
