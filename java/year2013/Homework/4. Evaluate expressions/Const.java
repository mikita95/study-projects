public class Const implements Expression3 {


	
    private int number;
	


    public int evaluate (int x, int y, int z) {
		
        return number;

    }
		
    public Const (int element) {
		
        number = element;
	
    }

}