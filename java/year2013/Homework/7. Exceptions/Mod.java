public class Mod extends Binary {

	

    public Mod(Expression3 x, Expression3 y) {
		
        super(x, y);

    }
	

	
    protected int run(int x, int y) throws Exception{
        if (y == 0)
            throw new DivisionByZero();
        int result = x % y;
        return result;
	
    }

}