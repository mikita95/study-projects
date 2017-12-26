import javafx.util.Pair;

public class SeidelMethod implements SolvingMethod {
    public final int iterationsLimit;

    public SeidelMethod(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }

    @Override
    public Pair<Vector, Integer> solve(LinearEquationsSystem system, double epsilon) {
        Matrix A = system.getA();
        Vector b = system.getB();
        int n = b.getDimensions();
        Vector x = Vector.zero(n);
        Vector nextX = x;
        int iterations = 0;
        do {
            x = nextX;
            double[] temp = new double[A.getHeight()];
            for (int i = 0; i < n; i++) {
                double sum = 0.;
                for (int j = 0; j < i; j++) {
                    sum += (-A.get(i, j)) * temp[j];
                }
                for (int j = i + 1; j < n; j++) {
                    sum += (-A.get(i, j)) * x.get(j);
                }
                sum += b.get(i);
                sum /= A.get(i, i);
                temp[i] = sum;
            }
            nextX = new Vector(temp);
            iterations++;
            if (nextX.hasNaN() || iterations > iterationsLimit)
                return null;
        } while (A.multiply(x).subtract(b).norm() > epsilon * 0.1);
        return !(A.multiply(x).subtract(b).norm() <= epsilon * 0.1) && !(Vector.distance(x, nextX) <= epsilon * 0.1) ? null : new Pair<>(nextX, iterations);
    }
}
