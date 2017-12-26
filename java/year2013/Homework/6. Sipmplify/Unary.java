public abstract class Unary implements Expression3 {
    private Expression3 a;
    private String operation;
    private int priority;

    public Unary(Expression3 a, String operation) {
        this.a = a;
        this.operation = operation;
    }

    public int getPriority() {
        return 1;
    }

    public String getOperation() {
        return this.operation;
    }

    public String toString1() {
        return this.operation;
    }

    public String simplify() {
        return this.operation;
    }

    public int evaluate (int x, int y, int z) {
        if (a.getOperation().equals("x") || a.getOperation().equals("y") || a.getOperation().equals("z"))
            return a.evaluate(x, y, z);
        int c = a.evaluate(x, y, z);
        return run(c);
    }

    protected abstract int run(int c);

}
