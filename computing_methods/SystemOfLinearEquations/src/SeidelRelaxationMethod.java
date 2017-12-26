import javafx.util.Pair;

public class SeidelRelaxationMethod implements SolvingMethod {
    public final int iterationsLimit;

    public SeidelRelaxationMethod(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }

    double w = 1.8;

    @Override
    public Pair<Vector, Integer> solve(LinearEquationsSystem system, double epsilon) {
        Matrix A = system.getA();
        Vector b = system.getB();
        int n = b.getDimensions();
        if (n <= 10) { // only for small matrices
            Matrix H = new Matrix(n, n);
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (i != j)
                        H.set(i, j, -A.get(i, j) / A.get(i, i));
                    else H.set(i, j, 0.);
            Matrix Hk = Matrix.unit(n);
            for (int i = 1; i <= 1000; i++)
                Hk = Hk.multiply(H);
            double spectralNumber = Math.pow(Hk.norm(), 1. / 1000.);
            double optimal = 2. / (1. + Math.sqrt(1. - spectralNumber * spectralNumber));
            if (!Double.isNaN(optimal)) w = optimal;
        }
        System.err.println(w);
        int iterations = 0;
        while (w > 1e-6) {
            Vector x = Vector.zero(n);
            Vector nextX = x;
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
                nextX.multiply(w).add(x.multiply(1 - w));
                iterations++;
                if (nextX.hasNaN() || iterations > iterationsLimit) {
                    w /= 1.25;
                    break;
                }
            } while (A.multiply(x).subtract(b).norm() > epsilon * 0.1);
            if (nextX.hasNaN() || iterations > iterationsLimit)
                continue;
            if (!(A.multiply(x).subtract(b).norm() <= epsilon * 0.1) && !(Vector.distance(x, nextX) <= epsilon * 0.1)) {
                w /= 1.25;
            } else return new Pair<>(nextX, iterations);
        }
        return null;
    }
}
