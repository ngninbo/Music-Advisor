import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = scanner.nextLine().split("\\s+");
        String word = inputs[0];
        int number = Integer.parseInt(inputs[1]);
        String result = number > word.length() ? word : word.substring(number) + word.substring(0, number);
        System.out.println(result);
    }
}