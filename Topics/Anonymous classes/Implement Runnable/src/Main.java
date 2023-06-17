import java.util.stream.IntStream;

class Create {

    public static Runnable createRunnable(String text, int repeats) {
        return () -> IntStream.range(0, repeats).mapToObj(i -> text).forEach(System.out::println);
    }
}