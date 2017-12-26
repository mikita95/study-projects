import javafx.util.Pair;

public interface SolvingMethod {
    Pair<Vector, Integer> solve(LinearEquationsSystem system, double epsilon);
}
