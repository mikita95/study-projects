public class Subtract extends Binary {

    public Subtract(Expression3 x, Expression3 y) {
        super(x, y, "-");
    }

    protected int run (int x, int y) {
        return x - y;
    }

}