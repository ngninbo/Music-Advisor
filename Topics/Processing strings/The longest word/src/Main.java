import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        System.out.println(longestWord(text));
    }

    public static String longestWord(String text) {
        String[] textArray = text.split(" ");
        String longestWord = textArray[0];
        for (String s : textArray) {
            if (s.length() > longestWord.length()) {
                longestWord = s;
            }
        }
        return longestWord;
    }
}