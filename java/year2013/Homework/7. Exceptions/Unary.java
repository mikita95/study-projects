public abstract class Unary implements Expression3 {
    private Expression3 a;

    public Unary(Expression3 a) {
        this.a = a;
    }

    public int evaluate (int x, int y, int z) throws Exception{
        int c = a.evaluate(x, y, z);
        return run(c);
    }

    protected abstract int run(int c) throws Exception;


}
