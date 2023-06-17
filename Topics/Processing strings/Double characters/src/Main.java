import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        Arrays.stream(text.split("")).forEach(s -> System.out.print(s + s));
    }
}