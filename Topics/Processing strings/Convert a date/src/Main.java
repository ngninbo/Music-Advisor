import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        if (date.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}")) {
            String[] splitDate = date.split("-");
            String result = String.format("%s/%s/%s", splitDate[1], splitDate[2], splitDate[0]);
            System.out.println(result);
        } else {
            throw new NumberFormatException("Input format invalid");
        }
    }
}