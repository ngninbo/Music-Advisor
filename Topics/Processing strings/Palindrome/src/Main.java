import java.util.Scanner;
import java.util.function.Predicate;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(isPalindrome().test(input) ? "yes" : "no");
    }

    public static Predicate<String> isPalindrome() {
        return word -> word.equals(new StringBuilder(word).reverse().toString());
    }
}