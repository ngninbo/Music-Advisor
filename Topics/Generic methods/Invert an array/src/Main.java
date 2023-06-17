// do not remove imports
import java.util.*;
import java.util.function.Function;

class ArrayUtils {
    static <T> T[] invert(T[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            T temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        return array;
    }
}