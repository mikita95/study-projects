import java.util.List;

public class Conjunction extends AbstractBinaryOperation {
    public Conjunction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "&" + right.toString() + ")";
    }

    public List<Pair> pathToFirstFreeEntry(String x) {
        return pathToFirstFreeEntryImpl(x, Main.CONJUCNTION);
    }

    public String toStringWithReplacedVar(Term term, String var) {
        return toStringWithReplacedVarImpl(term, var, "&");
    }

    public boolean isFreeToReplace(Term term, String var) {
        return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
    }
}
