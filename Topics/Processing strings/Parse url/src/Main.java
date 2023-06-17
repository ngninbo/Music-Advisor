import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine().replaceAll("[?&=/:]", " ");
        String[] splitUrl = url.split(" ");
        List<String> keywords = List.of("pass", "port", "name", "cookie", "host");

        Map<String, String> parameters = new LinkedHashMap<>();

        for (int i = 0; i < splitUrl.length; i++) {
            String key = splitUrl[i].trim();
            if (i + 1 < splitUrl.length) {
                String value = splitUrl[i + 1].trim();
                if (keywords.contains(key)) {
                    parameters.put(key, value.isEmpty() ? "not found" : value);
                }
            }
        }

        parameters.forEach((key, value) -> System.out.printf("%s : %s\n", key, value));

        if (parameters.containsKey("pass")) {
            System.out.printf("password : %s", parameters.get("pass"));
        }
    }
}