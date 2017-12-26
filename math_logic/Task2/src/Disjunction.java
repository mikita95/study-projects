
public class Disjunction extends BinaryOp {

    public Disjunction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean apply(boolean leftVal, boolean rightVal) {
        return (leftVal || rightVal);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Disjunction) {
            Disjunction c = (Disjunction) o;
            return (left.equals(c.left) && right.equals(c.right));
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "(" + left.toString() + "|" +  right.toString() + ")";
    }
}
