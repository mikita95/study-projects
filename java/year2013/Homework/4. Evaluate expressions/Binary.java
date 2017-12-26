public abstract class Binary implements Expression3 {

	
    private Expression3 a, b;
	

	
    public Binary (Expression3 c1, Expression3 c2) {

        a = c1;
		
        b = c2;
	
    }
	
	

    public int evaluate (int x, int y, int z) {
		
        int c1 = a.evaluate(x, y, z);
		
        int c2 = b.evaluate(x, y, z);
		
        return run(c1, c2);

    }
	

	
    protected abstract int run(int c1, int c2);

}