public class Subtract extends Ordinal {
    Ordinal left;
    Ordinal right;

    Subtract(Ordinal left, Ordinal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + "-" + right;
    }

    CNF toCNF() {
        return left.toCNF().subtract(right.toCNF());
    }
}
