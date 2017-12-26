public class Variable extends Unary {


    private int flag;
    public Variable (final String name) {
        super(new Expression3() {
            @Override
            public int evaluate(int x, int y, int z) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String toString1() {
                return name;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getOperation() {
                return name;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getPriority() {
                return 1;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String simplify() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, name);

        if (name.equals("x")) {
            flag = 1;
        } else
        if (name.equals("y")) {
            flag = 2;
        } else
        if (name.equals("z")) {
            flag = 3;
        }  		
    }
	

    public int evaluate (int x, int y, int z) {

        if (flag == 1)
            return x;

        if (flag == 2)
            return y;

        if (flag == 3)
            return z;

        return 0;
    }

    protected int run(int x) {
        return 0;
    }

}