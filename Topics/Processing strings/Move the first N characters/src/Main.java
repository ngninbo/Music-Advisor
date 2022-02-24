import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        int n = scanner.nextInt();

        StringBuilder output;
        if (n > text.length()) {
            output = new StringBuilder(text);
        } else {
            output = new StringBuilder();
            output.append(text, n, text.length()).append(text, 0, n);
        }
        System.out.println(output);
    }
}