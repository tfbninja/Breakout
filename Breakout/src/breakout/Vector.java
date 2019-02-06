package breakout;

/**
 *
 * @author Timothy
 */
public class Vector {

    private double x;
    private double y;
    private double mag;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double amt) {
        x = amt;
    }

    public void setY(double amt) {
        y = amt;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double magnitude(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }

    public double[] sublist(double[] a, int index1, int index2) {
        double[] newList = new double[index2 - index1];
        for (int i = index1; i < index2; i++) {
            newList[i - index1] = a[i];
        }
        return newList;
    }

    public double determinant(double[] a, double[] b) {
        return a[0] * b[1] - a[1] * b[0];
    }

    public double angleBetween(Vector other) {
        return Math.acos(this.dot(other) / (magnitude() * other.magnitude()));
    }
}
