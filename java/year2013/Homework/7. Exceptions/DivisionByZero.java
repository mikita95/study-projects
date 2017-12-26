public class DivisionByZero extends Exception {
    public DivisionByZero() {
        super("division by zero");
    }

    public DivisionByZero(Throwable cause) {
        super("division by zero", cause);
    }

}
