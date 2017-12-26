public class Power extends Ordinal {
    Ordinal left;
    Ordinal right;

    Power(Ordinal left, Ordinal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + "^" + right.toString();
    }

    CNF toCNF() {
        return left.toCNF().pow(right.toCNF());
    }
}

