public class Add extends Binary {
    public Add(Expression3 x, Expression3 y) {
        super(x, y, "+");
    }

    protected int run (int x, int y) {
        return x + y;
    }

}