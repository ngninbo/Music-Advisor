import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        parseUrl(url);
    }

    private static void parseUrl(String url) {

        String cleanUrl = url.replaceAll("[?&=/:]", " ");

        String[] splitUrl = cleanUrl.split(" ");
        String separator = " : ";
        StringBuilder sb = new StringBuilder();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < splitUrl.length; i++) {
            String key = splitUrl[i].trim();
            String value;
            if (i + 1 < splitUrl.length) {
                value = splitUrl[i + 1].trim();

                switch (key) {
                    case "pass":
                        String pass = key + separator + value + "\n";
                        sb.append(pass);
                        pass = "password" + separator + value + "\n";
                        password.append(pass);
                        break;
                    case "port":
                    case "name":
                    case "cookie":
                        String cookie = key + separator + (!"".equals(value) ? value : "not found") + "\n";
                        sb.append(cookie);
                        break;
                    case "host":
                        sb.append(key).append(separator).append(value).append("\n");
                        break;
                    default:
                }
            }
        }
        sb.append(password);
        System.out.println(sb);
    }
}