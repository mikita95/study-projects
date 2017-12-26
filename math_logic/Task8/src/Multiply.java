public class Multiply extends Ordinal {
    Ordinal left;
    Ordinal right;

    Multiply(Ordinal left, Ordinal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + "*" + right;
    }

    CNF toCNF() {
        return left.toCNF().mul(right.toCNF());
    }
}