import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int digit = scanner.nextInt();
        final int decimal = 10;
        System.out.println(digit / decimal);
    }
}