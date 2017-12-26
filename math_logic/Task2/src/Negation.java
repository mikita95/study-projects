import java.util.Map;


public class Negation implements Expression {
    private Expression expr;

    public Negation(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return !expr.evaluate(var);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Negation) {
            Negation c = (Negation) o;
            return (expr.equals(c.expr));
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "(" + "!" + expr.toString() + ")";
    }
}
