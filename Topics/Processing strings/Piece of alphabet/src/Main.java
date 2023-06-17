import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(isPieceOfAlphabet().test(line));
    }

    private static Predicate<String> isPieceOfAlphabet() {
        return line -> IntStream.range(0, line.length())
                .noneMatch(i -> i + 1 < line.length() && line.charAt(i) + 1 != line.charAt(i + 1));
    }
}