import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by nikita on 18.09.16.
 */
public class AdamsSolver implements Solver {
    private DifferentialSystem system;

    public AdamsSolver(DifferentialSystem system) {
        this.system = system;
    }

    @Override
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, int numberOfIterations) {
        ArrayList<ArrayList<Double>> answer = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> prevY = new ArrayList<>();
        List<Double> x = new ArrayList<>();
        int n = system.size();
        for (int i = 0; i < n; i++) {
            y.add(init.get(i).getValue());
            prevY.add(y.get(i));
            x.add(init.get(i).getKey());
            answer.add(new ArrayList<>());
            answer.get(i).add(init.get(i).getValue());
        }

        for (int j = 0; j < 3; j++) {
            double[] k1 = new double[n];
            double[] k2 = new double[n];
            double[] k3 = new double[n];
            double[] k4 = new double[n];
            for (int i = 0; i < n; i++) {
                k1[i] = system.get(i).apply(x.get(i), prevY);
            }
            for (int i = 0; i < n; i++) {
                final List<Double> finalPrevY = prevY;
                k2[i] = system.get(i).apply(x.get(i) + step / 2d, IntStream.range(0, n).mapToDouble(k -> finalPrevY.get(k) + step * k1[k] / 2d).boxed().collect(Collectors.toList()));
            }
            for (int i = 0; i < n; i++) {
                final List<Double> finalPrevY = prevY;
                k3[i] = system.get(i).apply(x.get(i) + step / 2d, IntStream.range(0, n).mapToDouble(k -> finalPrevY.get(k) + step * k2[k] / 2d).boxed().collect(Collectors.toList()));
            }
            for (int i = 0; i < n; i++) {
                final List<Double> finalPrevY = prevY;
                k4[i] = system.get(i).apply(x.get(i) + step, IntStream.range(0, n).mapToDouble(k -> finalPrevY.get(k) + step * k3[k]).boxed().collect(Collectors.toList()));
            }

            for (int i = 0; i < n; i++) {
                y.set(i, prevY.get(i) + step / 6d * (k1[i] + 2d * k2[i] + 2d * k3[i] + k4[i]));
                x.set(i, x.get(i) + step);
                answer.get(i).add(y.get(i));
            }
            prevY = new ArrayList<>(y);
        }

        for (int j = 4; j < numberOfIterations; j++) {
            List<Double> curveY = new ArrayList<>();
            final int finalJ = j;
            List<Double> prev3 = answer.stream().map(a -> a.get(finalJ - 1)).collect(Collectors.toList());
            List<Double> prev2 = answer.stream().map(a -> a.get(finalJ - 2)).collect(Collectors.toList());
            List<Double> prev1 = answer.stream().map(a -> a.get(finalJ - 3)).collect(Collectors.toList());
            List<Double> prev0 = answer.stream().map(a -> a.get(finalJ - 4)).collect(Collectors.toList());
            for (int i = 0; i < n; i++) {
                final BiFunction<Double, List<Double>, Double> f = system.get(i);
                curveY.add(prevY.get(i) + step * (55d * f.apply(x.get(i) - (j - 3) * step, prev3)
                        - 59d * f.apply(x.get(i) - (j - 2) * step, prev2)
                        + 37d * f.apply(x.get(i) - (j - 1) * step, prev1)
                        - 9d * f.apply(x.get(i) - j * step, prev0)) / 24d);
            }
            for (int i = 0; i < n; i++) {
                final BiFunction<Double, List<Double>, Double> f = system.get(i);
                y.set(i, prevY.get(i) + step * (9d * f.apply(x.get(i) - (j - 2) * step, curveY)
                        + 19d * f.apply(x.get(i) - (j - 1) * step, prev3)
                        - 5d * f.apply(x.get(i) - (j) * step, prev2)
                        + f.apply(x.get(i), prev1)) / 24d);
                answer.get(i).add(y.get(i));
            }
            prevY = new ArrayList<>(y);
        }
        return answer;
    }

    @Override
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, double max) {
        return solve(step, init, (int) (max / step) + 1);
    }
}
