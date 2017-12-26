import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private double[][] values;

    public Matrix(int height, int width) {
        assert !(height <= 0 || width <= 0);
        this.values = new double[height][width];
    }

    public Matrix(double[]... values) {
        assert !(values == null || values.length == 0);
        int width = values[0].length;
        for (double[] component : values)
            assert component.length == width;
        this.values = values;
    }

    public double[][] getValues() {
        double[][] result = new double[values.length][];
        for (int i = 0; i < result.length; i++) result[i] = Arrays.copyOf(values[i], values[i].length);
        return result;
    }

    public int getHeight() {
        return values.length;
    }

    public int getWidth() {
        return values[0].length;
    }

    public void swapRows(int i, int j) {
        if (i == j) return;
        for (int k = 0; k < getWidth(); k++) {
            double temp = values[i][k];
            values[i][k] = values[j][k];
            values[j][k] = temp;
        }
    }

    public void set(int i, int j, double value) {
        values[i][j] = value;
    }

    public double get(int i, int j) {
        return values[i][j];
    }

    public boolean equals(Matrix matrix, double epsilon) {
        if (getHeight() != matrix.getHeight() || getWidth() != matrix.getWidth()) return false;
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                if (!(Math.abs(get(i, j) - matrix.get(i, j)) < epsilon))
                    return false;
        return true;
    }

    public double norm() {
        double norm = 0.;
        for (int i = 0; i < getHeight(); i++) {
            double sum = 0.;
            for (int j = 0; j < getWidth(); j++) sum += Math.abs(get(i, j));
            if (sum > norm) norm = sum;
        }
        return norm;
    }

    public double conditionality() {
        return norm() * reversed().norm();
    }

    public Matrix add(Matrix matrix) {
        assert !(getHeight() != matrix.getHeight() || getWidth() != matrix.getWidth());
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < result[i].length; j++) result[i][j] = get(i, j) + matrix.get(i, j);
        }
        return new Matrix(result);
    }

    public Matrix subtract(Matrix b) {
        return add(b.multiply(-1.));
    }

    public Matrix multiply(Matrix matrix) {
        assert getWidth() == matrix.getHeight();
        double[][] result = new double[getHeight()][matrix.getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++)
                for (int k = 0; k < getWidth(); k++) result[i][j] += get(i, k) * matrix.get(k, j);
        }
        return new Matrix(result);
    }

    public Matrix multiply(double v) {
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) result[i][j] = get(i, j) * v;
        }
        return new Matrix(result);
    }

    public Vector multiply(Vector vector) {
        assert getWidth() == vector.getDimensions();
        double[] result = new double[getHeight()];
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++) result[i] += get(i, j) * vector.get(j);
        return new Vector(result);
    }

    /*public double determinant() {
        assert getHeight() == getWidth();
        if (getHeight() == 1) return get(0, 0);
        if (getHeight() == 2) return get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
        double result = 0.;
        for (int i = 0; i < getHeight(); i++) {
            double temp = get(i, 0) * minor(i, 0).determinant();
            result += (i % 2 == 0 ? 1 : -1) * temp;
        }
        return result;
    }*/

    public Pair<Matrix, Matrix> croutLUDecomposition() {
        int n = getHeight();
        Matrix U = new Matrix(n, n);
        Matrix L = new Matrix(n, n);
        double sum;
        for (int i = 0; i < n; i++) U.set(i, i, 1);
        for (int j = 0; j < n; j++) {
            for (int i = j; i < n; i++) {
                sum = 0;
                for (int k = 0; k < j; k++) sum = sum + L.get(i, k) * U.get(k, j);
                L.set(i, j, get(i, j) - sum);
            }
            for (int i = j; i < n; i++) {
                sum = 0;
                for (int k = 0; k < j; k++) sum = sum + L.get(j, k) * U.get(k, i);
                if (L.get(j, j) == 0) return null;
                U.set(j, i, (get(j, i) - sum) / L.get(j, j));
            }
        }
        return new Pair<>(U, L);
    }

    public double determinant() {
        double result;
        Pair<Matrix, Matrix> UL = croutLUDecomposition();
        if (UL == null) return 0.;
        result = UL.getKey().get(0, 0) * UL.getValue().get(0, 0);
        for (int i = 1; i < getHeight(); i++)
            result *= UL.getKey().get(i, i) * UL.getValue().get(i, i);
        return result;
    }

    public Matrix reversed() {
        double determinant = determinant();
        if (Math.abs(determinant) < 1e-18)
            return null;
        double[][] result = new double[getWidth()][getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                double temp = minor(i, j).determinant() / determinant;
                result[j][i] = ((i + j) % 2 == 0 ? 1 : -1) * temp;
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose() {
        double[][] result = new double[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) result[i][j] = get(j, i);
        }
        return new Matrix(result);
    }

    public Matrix minor(int a, int b) {
        double[][] result = new double[getHeight() - 1][getWidth() - 1];
        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < result[i].length; j++) result[i][j] = get(i < a ? i : i + 1, j < b ? j : j + 1);
        return new Matrix(result);
    }

    public static Matrix unit(int dimensions) {
        double[][] result = new double[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++)
            for (int j = 0; j < dimensions; j++)
                result[i][j] = i == j ? 1. : 0.;
        return new Matrix(result);
    }

    public static Matrix zero(int dimensions) {
        return zero(dimensions, dimensions);
    }

    public static Matrix zero(int height, int width) {
        return new Matrix(new double[height][width]);
    }

    public double[] getDiagonal() {
        double[] result = new double[getHeight()];
        for (int i = 0; i < getHeight(); i++) result[i] = get(i, i);
        return result;
    }

    public static Matrix byDiagonal(double[] trace) {
        double[][] result = new double[trace.length][trace.length];
        for (int i = 0; i < result.length; i++) result[i][i] = trace[i];
        return new Matrix(result);
    }

    public Vector getRow(int i) {
        assert !(i < 0 || i >= values.length);
        return new Vector(values[i].clone());
    }

    public Matrix excludeVariableForward(int i) {
        assert !(i < 0 || i >= values[0].length);
        double[][] result = getValues();
        Vector row = getRow(i);
        for (int k = values.length - 1; k >= i + 1; k--)
            result[k] = getRow(k).add(row.multiply(-get(k, i) / get(i, i))).getValues();
        return new Matrix(result);
    }

    public Matrix excludeVariableBackward(int i) {
        assert !(i < 0 || i >= values[0].length);
        double[][] result = getValues();
        Vector row = new Vector(getRow(i).getValues());
        row = row.multiply(1 / row.get(i));
        for (int j = i - 1; j >= 0; --j) result[j] = getRow(j).add(row.multiply(-get(j, i))).getValues();
        return new Matrix(result);
    }

    public static Matrix getExtended(Matrix a, Vector b) {
        double[][] result = new double[a.getHeight()][a.getWidth() + 1];
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++)
                result[i][j] = a.get(i, j);
            result[i][result[i].length - 1] = b.get(i);
        }
        return new Matrix(result);
    }


    public static Matrix parse(String s) {
        ArrayList<Integer> splitPoints = new ArrayList<>();
        int bracesBalance = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '{') {
                ++bracesBalance;
            } else if (s.charAt(i) == '}') {
                --bracesBalance;
            } else if (s.charAt(i) == ',' && bracesBalance == 0) {
                splitPoints.add(i);
            }
        }
        splitPoints.add(s.length());
        double[][] result = new double[splitPoints.size()][];
        for (int i = 0; i < splitPoints.size(); i++) {
            String[] line = s.substring(i == 0 ? 0 : splitPoints.get(i - 1) + 1, splitPoints.get(i) - 1).trim()
                    .replaceAll("[\\}\\{,]", " ").split("\\s+");
            result[i] = new double[line.length - 1];
            for (int j = 1; j < line.length; j++) {
                result[i][j - 1] = Double.parseDouble(line[j]);
            }
        }
        return new Matrix(result);
    }

    public static final int TO_STRING_DIGITS = 3;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++)
                result.append(String.format("% " + TO_STRING_DIGITS + "." + TO_STRING_DIGITS + "f ", get(i, j)));
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix)) return false;
        Matrix matrix = (Matrix) o;
        if (getHeight() != matrix.getHeight() || getWidth() != matrix.getWidth()) return false;
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++) if (get(i, j) != matrix.get(i, j)) return false;
        return true;
    }
}
