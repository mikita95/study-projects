import javafx.util.Pair;

import java.math.BigInteger;
import java.util.LinkedList;

public abstract class CNF implements Comparable<CNF> {



    CNF restn(Integer n) {
        switch (n) {
            case 0:
                return this;
            default:
                return this.rest().restn(n - 1);
        }
    }

    CNF first() {
        if (this.isAtom()) {
            return this;
        } else {
            try {
                LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>(((OrdList) this).list.subList(0, 1));
                return new OrdList(list, new Atom(BigInteger.ZERO));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    CNF rest() {
        if (!this.isAtom()) {
            if (((OrdList) this).list.size() == 1)
                return ((OrdList) this).atom;
            else {
                LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>(((OrdList) this).list.subList(((OrdList) this).list.size() - 1, ((OrdList) this).list.size()));
                try {
                    return new OrdList(list, ((OrdList) this).atom);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else return null;
    }

    CNF firstn(Integer n) {
        if (this.first().isAtom()) return this.first();
        else {
            OrdList list = (OrdList) this.first();
            if (n > 1) {
                CNF temp = this.first().rest().firstn(n - 1);
                if (temp.isAtom()) {
                    try {
                        return new OrdList(list.list, (Atom) temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    LinkedList<Pair<CNF, BigInteger>> list1 = ((OrdList) temp).list;
                    try {
                        LinkedList<Pair<CNF, BigInteger>> finalList = new LinkedList<Pair<CNF, BigInteger>>(list.list);
                        finalList.addAll(list1);
                        return new OrdList(finalList, ((OrdList) temp).atom);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            } else {
                CNF temp = this.first().rest();
                if (temp.isAtom()) {
                    try {
                        return new OrdList(list.list, (Atom) temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    LinkedList<Pair<CNF, BigInteger>> list1 = ((OrdList) temp).list;
                    try {
                        LinkedList<Pair<CNF, BigInteger>> finalList = new LinkedList<Pair<CNF, BigInteger>>(list.list);
                        finalList.addAll(list1);
                        return new OrdList(finalList, new Atom(BigInteger.ZERO));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }

                }
            }
        }
    }

    CNF fe() {
        if (this.isAtom()) {
            try {
                return new Atom(BigInteger.ZERO);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return ((OrdList) this).list.getFirst().getKey();
        }
    }

    BigInteger fc() {
        if (this.isAtom()) {
            try {
                return ((Atom) this).value;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return ((OrdList) this).list.getFirst().getValue();
        }
    }

    Integer length() {
        if (this.isAtom()) {
            return 0;
        } else {
            return 1 + ((OrdList) this).rest().length();
        }
    }

    Integer size() {
        if (this.isAtom()) {
            return 1;
        } else {
            return ((OrdList) this).fe().size() + ((OrdList) this).rest().size();
        }
    }

    Boolean limitp() {
        if (this.isAtom()) {
            return ((Atom) this).value.equals(BigInteger.ZERO);
        } else {
            return this.rest().limitp();
        }
    }

    CNF addNext(Pair<CNF, BigInteger> pair, CNF cnf) {
        if (cnf.isAtom()) {
            LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
            list.add(pair);
            try {
                return new OrdList(list, (Atom) cnf);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            LinkedList<Pair<CNF, BigInteger>> list = ((OrdList) cnf).list;
            list.addFirst(pair);
            try {
                return new OrdList(list, ((OrdList) cnf).atom);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    CNF limitpart() {
        if (this.isAtom()) {
            try {
                return new Atom(BigInteger.ZERO);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            if (!this.first().isAtom()) {
                OrdList list = (OrdList) this.first();
                CNF temp = this.rest().limitpart();
                if (temp.isAtom()) {
                    try {
                        return new OrdList(list.list, (Atom) temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    LinkedList<Pair<CNF, BigInteger>> finalList = new LinkedList<Pair<CNF, BigInteger>>(list.list);
                    finalList.addAll(((OrdList) temp).list);
                    try {
                        return new OrdList(finalList, ((OrdList) temp).atom);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }


                }
            } else return null;
        }
    }

    Atom natpart() {
        return this.isAtom() ? (Atom) this : this.rest().natpart();
    }

    abstract Boolean isAtom();

    CNF plus(CNF cnf) {
        if (this.isAtom() && cnf.isAtom()) {
            try {
                return new Atom(((Atom) this).value.add(((Atom) cnf).value));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            if (this.fe().compareTo(cnf.fe()) < 0) {
                return cnf;
            } else {
                if (this.fe().compareTo(cnf.fe()) == 0) {
                    return this.addNext(new Pair<CNF, BigInteger>(this.fe(), this.fc().add(cnf.fc())), cnf.rest());
                } else
                    return this.addNext(new Pair<CNF, BigInteger>(this.fe(), this.fc()), this.rest().plus(cnf));
            }
        }
    }

    CNF subtract(CNF myCNF) {
        if (this.isAtom()) {
            if (myCNF.isAtom()) {
                try {
                    if (((Atom) this).value.compareTo(((Atom) myCNF).value) < 0)
                        return new Atom(BigInteger.ZERO);
                    else
                        return new Atom(((Atom) this).value.subtract(((Atom) myCNF).value));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else return null;
        } else {
            try {
                if (this.fe().compareTo(myCNF.fe()) < 0) return new Atom(BigInteger.ZERO);
                else if (this.fe().compareTo(myCNF.fe()) > 0) return this;
                else if (this.fc().compareTo(myCNF.fc()) < 0) return new Atom(BigInteger.ZERO);
                else if (this.fc().compareTo(myCNF.fc()) > 0)
                    return this.addNext(new Pair<CNF, BigInteger>(this.fe(), this.fc().subtract(myCNF.fc())), this.rest());
                else return this.rest().subtract(myCNF.rest());

            } catch (Exception e) {
                return null;
            }
        }
    }

    CNF mul(CNF myCNF) {
        try {
            if (myCNF.isAtom() && ((Atom) myCNF).value.equals(BigInteger.ZERO))
                return new Atom(BigInteger.ZERO);
            if (this.isAtom() && ((Atom) this).value.equals(BigInteger.ZERO))
                return new Atom(BigInteger.ZERO);
            if (myCNF.isAtom()) {
                if (this.isAtom()) return new Atom(((Atom) this).value.multiply(((Atom) myCNF).value));
                else
                    return this.addNext(new Pair<CNF, BigInteger>(this.fe(), this.fc().multiply(((Atom) myCNF).value)), this.rest());
            }
            return this.addNext(new Pair<CNF, BigInteger>(this.fe().plus(myCNF.fe()), myCNF.fc()), this.mul(myCNF.rest()));
        } catch (Exception e) {
            return null;
        }
    }

    CNF pow(CNF myCNF) {
        try {
            if (this.isAtom() && ((Atom) this).value.equals(BigInteger.ONE)) return new Atom(BigInteger.ONE);
            if (myCNF.isAtom() && ((Atom) myCNF).value.equals(BigInteger.ZERO)) return new Atom(BigInteger.ONE);
            if (this.isAtom() && ((Atom) this).value.equals(BigInteger.ZERO)) return new Atom(BigInteger.ZERO);
            if (this.isAtom()) {
                if (myCNF.isAtom()) {
                    return new Atom(((Atom) this).value.pow(((Atom) myCNF).value.intValue()));
                } else return this.exp1((Atom) this, myCNF);
            }
            if (myCNF.isAtom()) {
                return this.exp3(this, (Atom) myCNF);
            }
            return this.exp4(this, myCNF);
        } catch (Exception e) {
            return null;
        }
    }

    CNF exp1(Atom arg1, CNF arg2) {
        try {
            if (arg2.isAtom() && ((Atom) arg2.fe()).value.equals(BigInteger.ONE)) {
                LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
                list.add(new Pair<CNF, BigInteger>(new Atom(arg2.fc()), arg1.value.pow(((Atom) arg2.rest()).value.intValue())));
                return new OrdList(list, new Atom(BigInteger.ZERO));
            }
            if (arg2.isAtom()) {
                LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
                list.add(new Pair<CNF, BigInteger>(((Atom) arg2).fe().subtract(new Atom(BigInteger.ONE)), arg2.fc()));
                OrdList ordList = new OrdList(list, new Atom(BigInteger.ZERO));
                LinkedList<Pair<CNF, BigInteger>> list1 = new LinkedList<Pair<CNF, BigInteger>>();
                list1.add(new Pair<CNF, BigInteger>(ordList, arg1.value.pow(((Atom) arg2.rest()).value.intValue())));
                return new OrdList(list1, new Atom(BigInteger.ZERO));
            }
            CNF cnf = exp1(arg1, arg2.rest());
            LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
            CNF addPair = addNext(new Pair<CNF, BigInteger>(arg2.fe().subtract(new Atom(BigInteger.ONE)), arg2.fc()), cnf.fe());
            list.add(new Pair<CNF, BigInteger>(addPair, cnf.fc()));
            return new OrdList(list, new Atom(BigInteger.ZERO));
        } catch (Exception e) {
            return null;
        }
    }

    CNF exp2(CNF arg1, Atom arg2) {
        if (arg2.value.equals(BigInteger.ONE))
            return arg1;
        try {
            LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
            list.add(new Pair<CNF, BigInteger>(arg1.fe().mul(new Atom(((Atom) arg2).value.subtract(BigInteger.ONE))), BigInteger.ONE));
            return (new OrdList(list, new Atom(BigInteger.ZERO))).mul(arg1);
        } catch (Exception e) {
            return null;
        }
    }

    CNF exp3(CNF arg1, Atom arg2) {
        try {
            if (arg2.value.equals(BigInteger.ZERO)) return new Atom(BigInteger.ONE);
            if (arg2.value.equals(BigInteger.ONE)) return arg1;
            if (arg1.limitp()) return exp2(arg1, arg2);
            return exp3(arg1, (Atom) (new Atom(((Atom) arg2).value.subtract(BigInteger.ONE)))).mul(arg1);
        } catch (Exception e) {
            return null;
        }
    }

    CNF exp4(CNF arg1, CNF arg2) {
        try {
            LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
            list.add(new Pair<CNF, BigInteger>(arg1.fe().mul(arg2.limitpart()), BigInteger.ONE));
            return (new OrdList(list, new Atom(BigInteger.ZERO))).mul(exp3(arg1, arg2.natpart()));
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public int compareTo(CNF o) {
        if (this.isAtom()) {
            if (o.isAtom()) return ((Atom) this).value.compareTo(((Atom) o).value);
            else return -1;
        }
        if (o.isAtom()) return 1;
        if (this.fe().compareTo(o.fe()) != 0) {
            return this.fe().compareTo(o.fe());
        }

        if (!this.fc().equals(o.fc())) {
            return this.fc().compareTo(o.fc());
        }
        return this.rest().compareTo(o.rest());
    }
}
