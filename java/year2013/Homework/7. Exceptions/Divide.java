public class Divide extends Binary {


    public Divide(Expression3 x, Expression3 y) {

        super(x, y);

    }

    protected int run(int x, int y) throws Exception{
        if (y == 0)
            throw new DivisionByZero();
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new Overflow();
        }
        return (int) (x / y);

    }

}