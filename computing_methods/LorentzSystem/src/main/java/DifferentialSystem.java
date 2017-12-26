import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by nikita on 11.09.16.
 */
public class DifferentialSystem {
    private List<BiFunction<Double, List<Double>, Double>> functions; // f_i(t, y_1(t), ..., y_n(t))

    public DifferentialSystem(List<BiFunction<Double, List<Double>, Double>> functions) {
        this.functions = functions;
    }

    public List<BiFunction<Double, List<Double>, Double>> getFunctions() {
        return functions;
    }

    public BiFunction<Double, List<Double>, Double> get(int i) { return functions.get(i); }

    public void setFunctions(List<BiFunction<Double, List<Double>, Double>> functions) {
        this.functions = functions;
    }

    public int size() {
        return functions.size();
    }
}
