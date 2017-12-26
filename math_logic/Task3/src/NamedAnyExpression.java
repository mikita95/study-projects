import java.util.Map;


public class NamedAnyExpression extends Variable {

    public NamedAnyExpression(String name) {
        super(name);
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        throw new UnsupportedOperationException("Patterns can not be evaluated!");
    }
}
