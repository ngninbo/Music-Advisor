import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        StringReverser reverser = str -> {
            StringBuilder reversedString = new StringBuilder();
            for (int i = str.length() - 1; i >= 0; i--) {
                reversedString.append(str.charAt(i));
            }
            return reversedString.toString();
        };

        System.out.println(reverser.reverse(line));
    }

    interface StringReverser {

        String reverse(String str);
    }

}