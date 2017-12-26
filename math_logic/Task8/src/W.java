import javafx.util.Pair;

import java.math.BigInteger;
import java.util.LinkedList;

public class W extends Ordinal {
    @Override
    public String toString() {
        return "w";
    }

    CNF toCNF() {
        try {
            LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
            list.add(new Pair<CNF, BigInteger>(new Atom(BigInteger.ONE), BigInteger.ONE));
            return new OrdList(list, new Atom(BigInteger.ZERO));
        } catch (Exception e) {
            return null;
        }
    }
}
