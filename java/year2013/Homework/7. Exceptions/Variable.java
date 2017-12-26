public class Variable implements Expression3 {

	
    private int flag;
    public Variable (String name) {

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

}