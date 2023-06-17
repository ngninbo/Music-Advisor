import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        final int length = text.length();
        final int middle = length / 2;

        System.out.println(text.substring(0, length % 2 == 0 ? middle - 1 : middle) + text.substring(middle + 1));
    }
}