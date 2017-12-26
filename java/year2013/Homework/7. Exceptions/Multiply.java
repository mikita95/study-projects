public class Multiply extends Binary {

    public Multiply(Expression3 x, Expression3 y) {
        super(x, y);
    }

    protected int run(int x, int y) throws Exception{
        if (x == Integer.MIN_VALUE && y == Integer.MIN_VALUE) {
            throw new Overflow();
        }
        Long tmp1 = Long.valueOf(String.valueOf(x));
        Long tmp2 = Long.valueOf(String.valueOf(y));
        if (tmp1 * tmp2 != Long.valueOf(String.valueOf(x * y)))
            throw new Overflow();
        return (x * y);
    }

}