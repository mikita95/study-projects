public class Const extends Unary {
    private int number;
    public Const(final int element) {
        super(new Expression3() {
            @Override
            public int evaluate(int x, int y, int z) {
                return element;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String toString1() {
                return String.valueOf(element);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getOperation() {
                return String.valueOf(element);  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getPriority() {
                return 1;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String simplify() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, String.valueOf(element));
        number = element;
    }
    public int evaluate(int x, int y, int z) {

        return number;

    }

    protected int run(int x) {
        return number;
    }

}