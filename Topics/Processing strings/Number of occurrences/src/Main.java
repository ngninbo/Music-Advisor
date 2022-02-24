import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String searchTerm = scanner.nextLine();

        int indexFirstOccurrence = text.indexOf(searchTerm);
        int numOfOccurrence = indexFirstOccurrence != -1 ? 1 : 0;
        int i = indexFirstOccurrence + searchTerm.length();
        do {
            text = text.substring(i);
            indexFirstOccurrence = text.indexOf(searchTerm);
            if (indexFirstOccurrence != -1) {
                numOfOccurrence += 1;
            }
            i = indexFirstOccurrence + searchTerm.length();
        } while (i < text.length());

        System.out.println(numOfOccurrence);
    }
}