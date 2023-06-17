import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String longestWord = Arrays.stream(scanner.nextLine().split("\\s+"))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();

        System.out.println(longestWord);
    }
}