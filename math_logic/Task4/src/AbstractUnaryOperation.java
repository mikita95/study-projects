public abstract class AbstractUnaryOperation implements Expression {
    protected Expression expression;

    public AbstractUnaryOperation(Expression expression) {
        this.expression = expression;
    }
}
