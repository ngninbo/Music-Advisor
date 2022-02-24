import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int h = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        int numberOfDays = 1;

        while (h > a) {
            h -= a - b;
            numberOfDays += 1;
        }

        System.out.println(numberOfDays);

    }
}