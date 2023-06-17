import java.util.Arrays;
import java.util.Scanner;

class Main {

    private static final long CONSTANT = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String genome = scanner.nextLine();
        System.out.println(percentageRatioOfSumGCFrequency(genome));
    }

    private static double percentageRatioOfSumGCFrequency(String genome) {

        double sum = sumGCFrequency(genome);

        return sum * CONSTANT / genome.length();
    }

    private static long sumGCFrequency(String genome) {
        return Arrays.stream(genome.split(""))
                .filter(ch -> "G".equalsIgnoreCase(ch) || "C".equalsIgnoreCase(ch))
                .count();
    }
}