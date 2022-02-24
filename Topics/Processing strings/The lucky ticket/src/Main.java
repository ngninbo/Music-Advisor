import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();
        int middle = number.length() / 2;
        String result;
        result = findSum(number.substring(0, middle)) == findSum(number.substring(middle)) ? "Lucky" : "Regular";

        System.out.println(result);
    }

    public static int findSum(String stringOfNumbers) {
        int result = 0;
        for (int i = 0; i < stringOfNumbers.length(); i++) {
            result += Integer.parseInt(String.valueOf(stringOfNumbers.charAt(i)));
        }
        return result;
    }
}