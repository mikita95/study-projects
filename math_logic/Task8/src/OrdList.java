import javafx.util.Pair;

import java.math.BigInteger;
import java.util.LinkedList;

public class OrdList extends CNF {
    LinkedList<Pair<CNF, BigInteger>> list;
    Atom atom;

    OrdList(LinkedList<Pair<CNF, BigInteger>> list, Atom atom) throws Exception {
        if (list.isEmpty())
            throw new Exception();
        this.list = list;
        this.atom = atom;
    }

    @Override
    public String toString() {
        String temp = "";
        for (Pair<CNF, BigInteger> pair : list) {
            temp += "(w";
            try {
                if (pair.getKey() != new Atom(BigInteger.ONE)) {
                    temp += "^(" + pair.getKey().toString() + ")";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp += ")";

            if (!pair.getValue().equals(BigInteger.ONE)) {
                temp += "*" + pair.getValue().toString();
            }
            temp += "+";

        }
        try {
            if (atom != new Atom(BigInteger.ZERO)) {
                temp += atom.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    Boolean isAtom() {
        return false;
    }
}
