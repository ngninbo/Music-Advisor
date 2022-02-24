class ManufacturingController {

    private static int numberOfProducts;
    private static int increment = 0;

    public static String requestProduct(String product) {
        // write your code here
        numberOfProducts += 1;
        increment += 1;
        return increment + ". Requested " + product;
    }

    public static int getNumberOfProducts() {
        // write your code here
        return numberOfProducts;
    }
}
