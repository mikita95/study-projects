import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 11.09.16.
 */
public interface Solver {
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, int numberOfIterations);
    public ArrayList<ArrayList<Double>> solve(double step, List<Pair<Double, Double>> init, double max);
}
