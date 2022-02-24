package advisor;

public class Main {

    public static void main(String[] args) {
        AdvisorBuilder.init(args)
                .withAdvisorController()
                .build()
                .start();
    }
}
