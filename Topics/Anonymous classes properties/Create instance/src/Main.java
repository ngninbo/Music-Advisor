
class CreateInstance {

    public static SuperClass create() {

        SuperClass instance = new SuperClass() {

            @Override
            public void method2() {
                System.out.println("method2");
            }

            @Override
            public void method3() {
                System.out.println("method3");
            }
        };

        // call the overridden methods
        instance.method2();
        instance.method3();

        return instance;
    }
}

// Don't change the code below

abstract class SuperClass {

    public static void method1() { 
        System.out.println("It's a static method.");
    }

    public void method2() {
        System.out.println("It's not a static method.");
    }

    public abstract void method3();
}

interface Resizable {

    void resize(float scale);
}

abstract class GraphicObject implements Resizable{
    int xPos, yPos;

    abstract void draw();
}

class Rectangle extends GraphicObject {

    // fields and methods

    @Override
    public void resize(float scale) { /* do something */ }

    @Override
    void draw() {

    }
}

class Circle extends GraphicObject {

    // fields and methods

    @Override
    public void resize(float scale) { /* do something */ }

    @Override
    void draw() {

    }
}