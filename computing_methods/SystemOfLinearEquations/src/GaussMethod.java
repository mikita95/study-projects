import javafx.util.Pair;

public class GaussMethod implements SolvingMethod {
    /*@Override
    public Vector solve(LinearEquationsSystem system, double epsilon) {
        Matrix A = system.getA();
        Vector b = system.getB();
        for (int i = 0; i < system.size(); i++) {
            final int[] t = {i};
            final int f = i;
            IntStream.range(i, system.size()).filter(c -> Math.abs(A.get(f, c)) >= epsilon).forEach(c -> t[0] = c);
            A.swapRows(i, t[0]);
            b.swapValues(i, t[0]);
            if (Math.abs(A.get(i, i)) >= epsilon) {
                IntStream.range(0, A.getWidth()).filter(k -> f != k).parallel().forEach(k ->
                        IntStream.range(0, system.size()).filter(j -> f != j).forEach(
                                j -> A.set(j, k, A.get(j, k) - A.get(j, f) / A.get(f, f) * A.get(f, k))));
                IntStream.range(0, system.size()).parallel().filter(k -> f != k).forEach(
                        k -> b.set(k, b.get(k) - A.get(k, f) / A.get(f, f) * b.get(f)));
                IntStream.range(0, system.size()).parallel().filter(k -> f != k).forEach(
                        k -> A.set(k, f, 0.));
            }
        }
        IntStream.range(0, A.getWidth()).parallel().forEach(
                i -> {
                    if (Math.abs(A.get(i, i)) < epsilon)
                        b.set(i, 0.);
                    else b.set(i, b.get(i) / A.get(i, i));
                });
        return b;
    }*/

    @Override
    public Pair<Vector, Integer> solve(LinearEquationsSystem s, double epsilon) {
        Matrix a = s.getA();
        Vector b = s.getB();
        int n = a.getHeight();
        Matrix extended = Matrix.getExtended(a, b);
        for (int i = 0; i < n; i++) extended = extended.excludeVariableForward(i);
        double[] answer = extended.getDiagonal();
        for (int i = n - 1; i >= 0; i--) {
            answer[i] = extended.get(i, n) / extended.get(i, i);
            extended = extended.excludeVariableBackward(i);
        }
        return new Pair<>(new Vector(answer), 0);
    }
}
