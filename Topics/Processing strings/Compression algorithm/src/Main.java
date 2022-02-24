import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        StringBuilder outputString = new StringBuilder();
        int counter;
        for (int i = 0; i < userInput.length(); i++) {
            counter = 1;

            while (i < userInput.length() - 1 && userInput.charAt(i) == userInput.charAt(i + 1)) {
                counter++;
                i++;
            }
            outputString.append(userInput.charAt(i)).append(counter);
        }

        System.out.println(outputString);
    }
}