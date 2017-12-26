public class Mod extends Binary {
    public Mod(Expression3 x, Expression3 y) {
        super(x, y, "%");
    }

    protected int run(int x, int y) {
	
        if (y == 0)
    	    	throw new ArithmeticException();	
        int result = x % y;
        return result;
	
    }

}