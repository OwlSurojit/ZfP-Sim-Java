package misc;

public class Tools {
    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < Math.pow(10, -10);
    }
}