import java.util.Scanner;
import java.util.Locale;

public class Main {

    public static String toUpperCase(String str) {
        String inputString = str == null ? "" : str;
        return inputString.toUpperCase(Locale.ENGLISH);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = "none".equalsIgnoreCase(line) ? null : line;
        System.out.println(toUpperCase(line));
    }
}