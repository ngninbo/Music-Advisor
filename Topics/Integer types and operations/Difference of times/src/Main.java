import java.util.Scanner;

class Main {
    public static final int NUM_SECONDS_PER_HOUR = 3600;
    public static final int NUM_SECONDS_PER_MINUTE = 60;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] inputs = new int[6];

        int i = 0;

        while (i < inputs.length) {
            inputs[i] = scanner.nextInt();
            i++;
        }

        int differenceOfTimes;
        int sumOfSecondsFirstTime = inputs[0] * NUM_SECONDS_PER_HOUR + inputs[1] * NUM_SECONDS_PER_MINUTE + inputs[2];
        int sumOfSecondsSecondTime = inputs[3] * NUM_SECONDS_PER_HOUR + inputs[4] * NUM_SECONDS_PER_MINUTE + inputs[5];
        differenceOfTimes = sumOfSecondsSecondTime - sumOfSecondsFirstTime;
        System.out.println(differenceOfTimes);
    }
}