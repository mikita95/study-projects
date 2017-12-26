import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by nikita on 18.09.16.
 */
public class RungeKuttaSolver implements Solver {

    private DifferentialSystem system;

    public RungeKuttaSolver(DifferentialSystem system) {
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

        for (int j = 0; j < numberOfIterations; j++) {
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
        return answer;
    }

    @Override
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, double max) {
        return solve(step, init, (int) (max / step) + 1);
    }
}
