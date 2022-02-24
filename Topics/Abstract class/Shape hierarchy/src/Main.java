
abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {

    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double getPerimeter() {
        return a + b + c;
    }

    @Override
    double getArea() {
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

class Rectangle extends Shape {

    private final double a;
    private final double b;

    public Rectangle(double a, double b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    double getPerimeter() {
        return (a + b) * 2;
    }

    @Override
    double getArea() {
        return a * b;
    }
}

class Circle extends Shape {

    private final double r;

    Circle(double r) {
        super();
        this.r = r;
    }

    @Override
    double getPerimeter() {
        return 2 * Math.PI * r;
    }

    @Override
    double getArea() {
        return Math.PI * r * r;
    }
}

abstract class A {

    public static void staticFoo() { }

    public void instanceBar() { }

    public abstract void abstractFoo();

    public abstract void abstractBar();
}

abstract class B extends A {

    public static void anotherStaticFoo() { }

    public void anotherInstanceBar() { }

    @Override
    public void abstractBar() { }

    public abstract void qq();
}

class C extends B {

    @Override
    public void abstractFoo() {

    }

    @Override
    public void qq() {

    }
}