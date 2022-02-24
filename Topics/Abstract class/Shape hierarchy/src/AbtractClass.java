abstract class AbstractClass {

    int field1;
    int field2;

    public AbstractClass(int field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    abstract void method1();
    abstract int method2();

    public static void doSomeThing() {

    }
}


interface SomeInterface {

    void doSomeThing();

    static void doSomething() {

    }
}

class SomeInterfaceImpl implements SomeInterface {

    @Override
    public void doSomeThing() {

    }
}