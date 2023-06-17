/**
    Box for cakes
*/
class CakeBox extends Box<Cake> {
}

/**
    Box for pies
*/
class PieBox extends Box<Pie> {
}


/**
    Box for tarts
*/
class TartBox extends Box<Tart> {
}

/*
    Hundred more such boring classes OR ...
    magic class for everything everybody is waiting for
*/
class Box<T> {
    T t;

    public void put(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}

// Don't change classes below
class Cake { }

class Pie { }

class Tart { }