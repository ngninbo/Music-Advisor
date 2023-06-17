import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sequence = scanner.nextLine();
        System.out.println(encode(sequence));
    }

    private static String encode(String sequence) {

        StringBuilder result = new StringBuilder();
        int counter;

        for (int i = 0; i < sequence.length(); i++) {
            counter = 1;

            while (i < sequence.length() - 1 && sequence.charAt(i) == sequence.charAt(i + 1)) {
                counter++;
                i++;
            }
            result.append(sequence.charAt(i)).append(counter);
        }

        return result.toString();
    }
}