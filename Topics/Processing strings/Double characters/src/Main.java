import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int repeat = 2;
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < repeat; j++) {
                System.out.print(text.charAt(i));
            }
        }
    }
}