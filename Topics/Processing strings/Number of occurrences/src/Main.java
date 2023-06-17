import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String substring = scanner.nextLine();

        final Pattern pattern = Pattern.compile(substring, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(text);

        System.out.println(matcher.results().count());
    }
}