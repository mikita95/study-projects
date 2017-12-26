import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 11.09.16.
 */
public class EulerSolver implements Solver {
    private DifferentialSystem system;

    public EulerSolver(DifferentialSystem system) {
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
            for (int i = 0; i < n; i++) {
                final double a = system.get(i).apply(x.get(i), prevY);
                y.set(i, prevY.get(i) + step * a);
                x.set(i, x.get(i) + step);
                answer.get(i).add(y.get(i));

            }
            prevY = new ArrayList<>(y);
        }
        return answer;
    }

    @Override
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, double max) {
        return solve(step, init, (int)(max / step) + 1);
    }
}
