public class LinearEquationsSystem {
    private Matrix A;
    private Vector b;

    public LinearEquationsSystem(Matrix A, Vector b) {
        assert A.getHeight() == b.getDimensions();
        this.A = A;
        this.b = b;
    }

    public LinearEquationsSystem(Matrix A) {
        this.A = A;
        this.b = Vector.zero(A.getHeight());
    }

    public Matrix getA() {
        return A;
    }

    public void setA(Matrix a) {
        A = a;
    }

    public Vector getB() {
        return b;
    }

    public void setB(Vector b) {
        this.b = b;
    }

    public int size() {
        return b.getDimensions();
    }
}
