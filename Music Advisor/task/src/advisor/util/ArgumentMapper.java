package advisor.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArgumentMapper {

    public static Map<String, String> convertToMap(String[] args) {

        int length = args.length;
        return IntStream.range(0, length)
                .filter(i -> i + 1 < length)
                .boxed()
                .collect(
                        Collectors.toMap(
                                i -> args[i].replace("-", ""),
                                i -> args[i + 1], (a, b) -> b
                        )
                );
    }
}
