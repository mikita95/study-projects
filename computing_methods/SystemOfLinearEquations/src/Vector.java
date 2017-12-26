import java.util.Arrays;

public class Vector implements Cloneable {

    private double[] values;

    public double[] getValues() {
        return Arrays.copyOf(values, values.length);
    }

    public Vector(int dimension) {
        assert dimension > 0;
        this.values = new double[dimension];
    }

    public void fill(double v) {
        Arrays.fill(values, v);
    }

    public Vector(double... values) {
        assert values != null;
        this.values = values;
    }

    public int getDimensions() {
        return values.length;
    }

    public double get(int i) {
        return values[i];
    }

    public void set(int i, double v) {
        values[i] = v;
    }

    public void swapValues(int i, int j) {
        double temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }

    public Vector add(Vector vector) {
        double[] result = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++)
            result[i] = get(i) + vector.get(i);
        return new Vector(result);
    }

    public Vector subtract(Vector vector) {
        return add(vector.multiply(-1));
    }

    public Vector multiply(double a) {
        double[] result = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++)
            result[i] = a * get(i);
        return new Vector(result);
    }

    public double multiply(Vector vector) {
        assert getDimensions() == vector.getDimensions();
        double result = 0.;
        for (int i = 0; i < getDimensions(); i++) result += get(i) * vector.get(i);
        return result;
    }

    public boolean equals(Vector vector, double epsilon) {
        if (getDimensions() != vector.getDimensions())
            return false;
        for (int i = 0; i < getDimensions(); i++)
            if (!(Math.abs(get(i) - vector.get(i)) < epsilon))
                return false;
        return true;
    }

    public boolean hasNaN() {
        for (int i = 0; i < getDimensions(); i++) if (get(i) == Double.NaN) return true;
        return false;
    }

    public double norm() {
        return Vector.distance(this, Vector.zero(getDimensions()));
    }

    public static double distance(Vector a, Vector b) {
        assert a.getDimensions() == b.getDimensions();
        double sum = 0.;
        for (int i = 0; i < a.getDimensions(); i++) sum += (b.get(i) - a.get(i)) * (b.get(i) - a.get(i));
        return Math.sqrt(sum);
    }

    public static Vector zero(int dimensions) {
        return new Vector(new double[dimensions]);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("(");
        for (int i = 0; i < getDimensions(); i++) {
            result.append(get(i));
            if (i != getDimensions() - 1)
                result.append(", ");
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector)) return false;
        Vector v = (Vector) o;
        if (getDimensions() != v.getDimensions()) return false;
        for (int i = 0; i < getDimensions(); i++)
            if (get(i) != v.get(i))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int factor = 1;
        for (int i = 0; i < getDimensions(); i++) {
            hash += Double.hashCode(get(i)) * factor;
            factor *= 31;
        }
        return hash;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Vector result = (Vector) super.clone();
        result.values = result.values.clone();
        return result;
    }

}