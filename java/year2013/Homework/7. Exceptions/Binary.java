public abstract class Binary implements Expression3 {
    private Expression3 a, b;

    public Binary(Expression3 a, Expression3 b) {
        this.a = a;
        this.b = b;
    }



    public int evaluate(int x, int y, int z) throws Exception{
        int c1 = 1;
        int c2 = 1;

            c1 = a.evaluate(x, y, z);
            c2 = b.evaluate(x, y, z);
            return run(c1, c2);


    }

    protected abstract int run(int c1, int c2) throws Exception;

}