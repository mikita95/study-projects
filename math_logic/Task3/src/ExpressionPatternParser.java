import com.google.common.base.Preconditions;

public class ExpressionPatternParser extends ExpressionParser {

    protected Expression createNamed(String name) {
        Preconditions.checkState(name.matches("[A-Z][0-9]*"), "Incorrect variable name: " + name);
        return new NamedAnyExpression(name);
    }

}
