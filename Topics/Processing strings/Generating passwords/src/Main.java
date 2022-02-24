import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int upperLetters = scanner.nextInt();
        int lowerLetters = scanner.nextInt();
        int digits = scanner.nextInt();
        int symbols = scanner.nextInt();

        String password = generatePassword(upperLetters, lowerLetters, digits, symbols);
        System.out.println(password);
    }

    private static String  generatePassword(int upperLetters, int lowerLetters, int digits, int symbols) {
        StringBuilder passwordBuilder = new StringBuilder();

        char ch;
        int digit;
        for (int i = 0; i < symbols; i++) {
            if (i < upperLetters) {
                ch = generateCharacter(passwordBuilder, i, 65, 90);
                passwordBuilder.append(ch);
            } else if (i < upperLetters + lowerLetters) {
                ch = generateCharacter(passwordBuilder, i, 97, 122);
                passwordBuilder.append(String.valueOf(ch).toLowerCase());
            } else if (i >= upperLetters + lowerLetters && i < upperLetters + lowerLetters + digits) {
                digit = getDigit(passwordBuilder, i);
                passwordBuilder.append(digit);
            } else {
                ch = generateCharacter(passwordBuilder, i, 65, 90);
                passwordBuilder.append(ch);
            }

        }

        return passwordBuilder.toString();
    }

    private static boolean isDigit(char ch) {
        return (int) ch >= 48 && (int) ch <= 57;
    }

    private static int getDigit(StringBuilder passwordBuilder, int i) {
        int digit;
        digit = ThreadLocalRandom.current().nextInt(0, 9);
        while (i - 1 >= 0 && isDigit(passwordBuilder.charAt(i - 1)) && digit == Integer.parseInt(String.valueOf(passwordBuilder.charAt(i - 1)))) {
            digit = ThreadLocalRandom.current().nextInt(0, 9);
        }
        return digit;
    }

    private static char generateCharacter(StringBuilder passwordBuilder, int i, int origin, int bound) {
        char ch;
        ch = (char) ThreadLocalRandom.current().nextInt(origin, bound);
        while (i - 1 != -1 && ch == passwordBuilder.charAt(i - 1)) {
            ch = (char) ThreadLocalRandom.current().nextInt(origin, bound);
        }
        return ch;
    }
}