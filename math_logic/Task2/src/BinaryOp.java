import java.util.Map;

public abstract class BinaryOp implements Expression {

    protected Expression left;
    protected Expression right;

    public BinaryOp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft(){
        return left;
    }

    public Expression getRight(){
        return right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return apply(left.evaluate(var), right.evaluate(var));
    }

    protected abstract boolean apply(boolean leftVal, boolean rightVal);

}
