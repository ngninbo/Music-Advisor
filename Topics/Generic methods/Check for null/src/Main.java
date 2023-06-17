// do not remove imports
import java.util.*;
import java.util.function.Function;

class ArrayUtils {
    static <T> boolean hasNull(T[] arr) {
        return Arrays.stream(arr).anyMatch(Objects::isNull);
    }
}