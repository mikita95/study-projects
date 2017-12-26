public class Multiply extends Binary {
    public Multiply(Expression3 x, Expression3 y) {
        super(x, y, "*");
    }

    protected int run(int x, int y) {
        return (int) (x * y);
    }

}