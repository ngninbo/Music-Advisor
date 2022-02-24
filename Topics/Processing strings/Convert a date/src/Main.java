import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        String outputFormatted = formatDate(date);
        System.out.println(outputFormatted);
    }

    private static String formatDate(String date) {
        String[] dateParts = date.split("-");
        int i = 0;
        String outputFormatted;
        outputFormatted = dateParts[i + 1] + "/" + dateParts[i + 2] + "/" + dateParts[i];
        return outputFormatted;
    }
}