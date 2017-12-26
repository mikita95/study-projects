import java.util.*;
import java.util.function.BiFunction;

public class Solver {
    private double initValue;
    private BiFunction<Double, Double, Double> function;
    private double epsilon;
    private double maxNumberOfIterations;

    public Solver(double initValue, BiFunction<Double, Double, Double> function, double epsilon, double maxNumberOfIterations) {
        this.initValue = initValue;
        this.function = function;
        this.epsilon = epsilon;
        this.maxNumberOfIterations = maxNumberOfIterations;
    }

    public ArrayList<Double> solveInLimit(double r) {
        ArrayList<Double> values = new ArrayList<>();
        TreeMap<Double, Integer> used = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double a, Double b) {
                if (a < b - epsilon) return -1;
                else if (a < b + epsilon) return 0;
                else return 1;
            }
        });

        double current = initValue;
        int k = 0;
        while (!used.containsKey(current)) {
            used.put(current, values.size());
            values.add(current);
            current = function.apply(current, r);
            if (k > maxNumberOfIterations || Double.isInfinite(current))
                return new ArrayList<>();
            k++;
        }

        ArrayList<Double> result = new ArrayList<>();
        for (int i = values.size() - used.get(current); i > 0; i--) {
            result.add(current);
            current = function.apply(current, r);
        }
        return result;
    }

    public Map<Double, ArrayList<Double>> solveSequence(double r) {
        ArrayList<Double> values = new ArrayList<>();
        TreeMap<Double, Integer> used = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double a, Double b) {
                if (a < b - epsilon) return -1;
                else if (a < b + epsilon) return 0;
                else return 1;
            }
        });

        double current = initValue;
        int k = 0;
        Map<Double, ArrayList<Double>> result = new HashMap<>();
        while (!used.containsKey(current)) {
            used.put(current, values.size());
            values.add(current);
            double c = current;
            ArrayList<Double> temp = new ArrayList<>();

            temp.add(c);
            result.put((double)k, temp);
            current = function.apply(current, r);

            if (k > maxNumberOfIterations || Double.isInfinite(current))
                return new HashMap<>();
            k++;
        }

        return result;
    }
}
