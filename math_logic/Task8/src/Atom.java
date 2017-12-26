import java.math.BigInteger;

public class Atom extends CNF {
    BigInteger value;

    Atom(BigInteger value) throws Exception {
        if (value.compareTo(BigInteger.ZERO) < 0)
            throw new Exception();
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    Boolean isAtom() {
        return true;
    }
}
