import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ticket = scanner.nextLine();

        if (ticket.matches("[\\d]{6}")) {
            System.out.println(isLucky(ticket) ? "Lucky" : "Regular");
        } else {
            throw new NumberFormatException("Invalid input");
        }
    }

    private static boolean isLucky(String ticket) {

        int sumFistDigits = 0;
        int sumLastDigits = 0;

        for (int i = 0; i < ticket.length() / 2; i++) {
            sumFistDigits += ticket.charAt(i);
            sumLastDigits += ticket.charAt(ticket.length() - 1 - i);
        }

        return sumFistDigits == sumLastDigits;
    }
}