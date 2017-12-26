public class Add extends Binary {

    public Add(Expression3 x, Expression3 y) {
        super(x, y);
    }

    protected int run (int x, int y) throws Exception{
        Long tmp1 = Long.valueOf(String.valueOf(x));
        Long tmp2 = (long) y;
        if (tmp1 + tmp2 != Long.valueOf(String.valueOf(x + y)))
            throw new Overflow();
        return x + y;
    }

}