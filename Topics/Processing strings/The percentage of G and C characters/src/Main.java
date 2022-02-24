import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String sequence = scanner.nextLine();
        System.out.println(computeGCPercentage(sequence));
    }

    public static double computeGCPercentage(String sequence) {
        int sumGC = 0;
        double result;

        for (int i = 0; i < sequence.length(); i++) {
            switch (sequence.charAt(i)) {
                case 'c':
                case 'C':
                case 'g':
                case 'G':
                    sumGC += 1;
                    break;
                default:
            }
        }

        result = (double) sumGC * 100 / sequence.length();

        return result;
    }
}