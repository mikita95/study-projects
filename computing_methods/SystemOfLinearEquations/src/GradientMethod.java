import javafx.util.Pair;

public class GradientMethod implements SolvingMethod {
    public final int iterationsLimit;

    public GradientMethod(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }

    @Override
    public Pair<Vector, Integer> solve(LinearEquationsSystem system, double epsilon) {
        Matrix A = system.getA();
        Vector b = system.getB();
        Matrix at = A.transpose();
        Vector x = new Vector(b.getDimensions());
        for (int i = 0; i < b.getDimensions(); i++) x.set(i, -b.get(i) / A.get(i, i));
        Vector rp = A.multiply(x).subtract(b);
        int iterations = 0;
        while (rp.norm() > epsilon) {
            Vector w = A.multiply(at).multiply(rp);
            double mu = rp.multiply(w) / (w.norm() * w.norm());
            x = x.subtract(A.multiply(rp).multiply(mu));
            iterations++;
            if (iterations > iterationsLimit || x.hasNaN())
                return null;
            rp = A.multiply(x).subtract(b);
        }
        return new Pair<>(x, iterations);
    }
}
