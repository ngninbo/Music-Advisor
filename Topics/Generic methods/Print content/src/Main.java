// do not remove imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

class ArrayUtils {
    // define info method here
    public static <T> String info(T[] array) {
        StringBuilder output = new StringBuilder("[");

        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                output.append(array[i]);
                if (i < array.length - 1) {
                    output.append(", ");
                } else {
                    output.append("]");
                }
            }
        } else {
            output.append("]");
        }

        return output.toString();
    }
}