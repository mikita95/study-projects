public class Not extends Unary {

	public Not(Expression3 value) {
        super(value);
	}

    protected int run(int x) {
        return ~(x);
	}
}