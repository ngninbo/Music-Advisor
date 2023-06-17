
class Util {
    // correct this method to avoid NPE
    public static void printLength(String name) {
        String tmp = name == null ? "" : name;
        System.out.println(tmp.length());
    }
}