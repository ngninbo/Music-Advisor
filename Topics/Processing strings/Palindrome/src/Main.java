import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        StringBuilder reverse = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            reverse.append(input.charAt(i));
        }

        String output = input.equals(reverse.toString()) ? "yes" : "no";
        System.out.println(output);
    }
}