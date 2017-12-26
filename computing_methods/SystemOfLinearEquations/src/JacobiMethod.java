import javafx.util.Pair;

public class JacobiMethod implements SolvingMethod {

    public final int iterationsLimit;

    public JacobiMethod(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }
    @Override
    public Pair<Vector, Integer> solve(LinearEquationsSystem system, double epsilon) {
        Matrix D = Matrix.byDiagonal(system.getA().getDiagonal());
        Matrix dReversed = D.reversed();
        if (dReversed == null) return null;
        Matrix B = Matrix.unit(system.getA().getHeight()).subtract(dReversed.multiply(system.getA()));
        Vector g = dReversed.multiply(system.getB());
        Vector x = Vector.zero(system.getB().getDimensions());
        Vector nextX = x;
        int iterations = 0;
        double q = B.norm();
        do {
            x = nextX;
            nextX = B.multiply(x).add(g);
            iterations++;
            if (nextX.hasNaN() || iterations > iterationsLimit)
                return null;
        } while (!x.equals(nextX, epsilon * (1 - q)));
        return new Pair<>(nextX, iterations);
    }
}
