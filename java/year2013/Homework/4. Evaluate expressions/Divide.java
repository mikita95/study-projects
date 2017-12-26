public class Divide extends Binary {

	

    public Divide(Expression3 x, Expression3 y) {

        super(x, y);
	
    }
	
	

    protected int run (int x, int y) {
	
        if (y != 0)
		
            return x / y;
		
        else
			
            return 0;
	
    }

}