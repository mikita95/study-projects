public class Main {

	

    public static void main (String[] args) {

        double x = Double.parseDouble(args[0]);
	
        double y = Double.parseDouble(args[1]);
	
        double z = Double.parseDouble(args[2]);
	
	//x^2-2y+z

        double res = new Add(
		
	    new Subtract(
	
	        new Multiply(
	
	            new Variable("x"),

	            new Variable("x")

	        ),
			
                new Multiply(
	
                    new Const(2),
	
                    new Variable("y")
                )
		
	    ),
		
	    new Variable("z")	
	    ).evaluate(x, y, z);
	
	System.out.println(res);
	
    }

}
