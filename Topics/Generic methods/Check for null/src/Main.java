// do not remove imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

class ArrayUtils {
    // define hasNull method here
    public static <T> boolean hasNull(T[] array) {
        for (T t : array) {
            if (t == null) {
                return true;
            }
        }
        return false;
    }
}
