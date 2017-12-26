import java.util.List;

public interface Expression {
    @Override
    public String toString();

    public List<Pair> pathToFirstFreeEntry(String x);

    public String toStringWithReplacedVar(Term term, String var);

    public boolean isFreeToReplace(Term term, String var);
}
