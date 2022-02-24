import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        System.out.println(isPieceOfAlphabet(string));
    }

    public static boolean isPieceOfAlphabet(String string) {
        boolean isTrue = true;

        for (int i = 0; i < string.length(); i++) {
            if (i + 1 < string.length() && string.charAt(i) + 1 != string.charAt(i + 1)) {
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }
}