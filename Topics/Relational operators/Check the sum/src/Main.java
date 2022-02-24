import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        boolean result = checkSum(a, b, c);
        System.out.println(result);
    }

    private static boolean checkSum(int a, int b, int c) {
        if (a + b == 20) {
            return true;
        }
        if (a + c == 20 ) {
            return true;
        }

        return b + c == 20;
    }
}