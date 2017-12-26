import java.math.BigInteger;

public class Number extends Ordinal {
    Integer number;

    Number(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.toString();
    }

    CNF toCNF() {
        try {
            return new Atom(BigInteger.valueOf(number));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
