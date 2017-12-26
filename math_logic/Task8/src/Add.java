public class Add extends Ordinal {
    Ordinal left;
    Ordinal right;

    Add(Ordinal left, Ordinal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + "+" + right;
    }

    CNF toCNF() {
        return left.toCNF().plus(right.toCNF());
    }
}
