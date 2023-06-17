import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int[] UPPERCASE_RANGE = {65, 90};
    private static final int[] LOWERCASE_RANGE = {97, 112};
    private static final int[] DIGIT_RANGE = {48, 57};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numUpperLetters = scanner.nextInt();
        int numLowerLetters = scanner.nextInt();
        int numOfDigits = scanner.nextInt();
        int numOfSymbols = scanner.nextInt();

        String password = generatePassword(numUpperLetters, numLowerLetters, numOfDigits, numOfSymbols);
        System.out.println(password);
    }

    private static String  generatePassword(int numUpperLetters,
                                            int numLowerLetters,
                                            int numOfDigits,
                                            int numOfSymbols) {

        StringBuilder passwordBuilder = new StringBuilder();

        char ch;
        int digit;
        for (int i = 0; i < numOfSymbols; i++) {
            if (i < numUpperLetters) {
                ch = generateCharacter(passwordBuilder, i, UPPERCASE_RANGE);
                passwordBuilder.append(ch);
            } else if (i < numUpperLetters + numLowerLetters) {
                ch = generateCharacter(passwordBuilder, i, LOWERCASE_RANGE);
                passwordBuilder.append(String.valueOf(ch).toLowerCase());
            } else if (i >= numUpperLetters + numLowerLetters && i < numUpperLetters + numLowerLetters + numOfDigits) {
                digit = getDigit(passwordBuilder, i);
                passwordBuilder.append(digit);
            } else {
                ch = generateCharacter(passwordBuilder, i, UPPERCASE_RANGE);
                passwordBuilder.append(ch);
            }

        }

        return passwordBuilder.toString();
    }

    private static boolean isDigit(char ch) {
        return (int) ch >= DIGIT_RANGE[0] && (int) ch <= DIGIT_RANGE[1];
    }

    private static int getDigit(StringBuilder passwordBuilder, int i) {
        int digit;
        final int bound = 9;
        digit = ThreadLocalRandom.current().nextInt(0, bound);
        while (i - 1 >= 0 &&
                isDigit(passwordBuilder.charAt(i - 1)) &&
                digit == Integer.parseInt(String.valueOf(passwordBuilder.charAt(i - 1)))) {
            digit = ThreadLocalRandom.current().nextInt(0, bound);
        }
        return digit;
    }

    private static char generateCharacter(StringBuilder passwordBuilder, int i, int[] range) {
        char ch;
        ch = (char) ThreadLocalRandom.current().nextInt(range[0], range[1]);
        while (i - 1 != -1 && ch == passwordBuilder.charAt(i - 1)) {
            ch = (char) ThreadLocalRandom.current().nextInt(range[0], range[1]);
        }
        return ch;
    }
}