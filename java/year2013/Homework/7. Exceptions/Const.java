public class Const implements Expression3 {
    private int number;
    public Const(int element) {
        number = element;
    }
    public int evaluate(int x, int y, int z) {

        return number;

    }

}