public class Main {

    public static void main(String[] args) {

        SingleMethodInterface instance = () -> System.out.println("The anonymous class does something");

        instance.doSomething();
    }
}

interface SingleMethodInterface {

    void doSomething();
}