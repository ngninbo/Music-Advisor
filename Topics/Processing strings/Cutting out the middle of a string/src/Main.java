import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        System.out.println(skipMiddleCharacters(text));
    }

    public static String skipMiddleCharacters(String text) {

        char[] textArray = text.toCharArray();
        int middle = textArray.length / 2;
        textArray[middle] = ' ';
        String finalString = String.valueOf(textArray).replace(" ", "");
        if (finalString.length() % 2 != 0) {
            textArray[middle - 1] = ' ';
            finalString = String.valueOf(textArray).replace(" ", "");
        }


        return finalString;
    }
}