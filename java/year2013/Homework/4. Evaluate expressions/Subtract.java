public class Subtract extends Binary {

	

    public Subtract(Expression3 x, Expression3 y) {
		
        super(x, y);
	
    }
	
	

    protected double run (double x, double y) {
		
        return x - y;
	
    }

}