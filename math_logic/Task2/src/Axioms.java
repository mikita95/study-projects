public class Axioms {

    private static boolean check1(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getRight();
                return (impl1.getLeft().equals(impl2.getRight()));
            }
        }
        return false;
    }

    private static boolean check2(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl3 = (Implication) impl1.getRight();
                    if (impl3.getLeft() instanceof Implication) {
                        Implication impl4 = (Implication) impl3.getLeft();
                        if (impl4.getRight() instanceof Implication) {
                            Implication impl5 = (Implication) impl4.getRight();
                            if (impl3.getRight() instanceof Implication) {
                                Implication impl6 = (Implication) impl3.getRight();
                                boolean fl1 = impl2.getLeft().equals(impl4.getLeft()) && impl2.getLeft().equals(impl6.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl5.getLeft());
                                boolean fl3 = impl5.getRight().equals(impl6.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check3(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getRight();
                if (impl2.getRight() instanceof Conjunction) {
                    Conjunction conj1 = (Conjunction) impl2.getRight();
                    boolean fl1 = impl1.getLeft().equals(conj1.getLeft());
                    boolean fl2 = impl2.getLeft().equals(conj1.getRight());
                    return (fl1 && fl2);
                }
            }
        }
        return false;
    }

    private static boolean check4(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Conjunction) {
                Conjunction conj1 = (Conjunction) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean check5(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Conjunction) {
                Conjunction conj1 = (Conjunction) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getRight()));
            }
        }
        return false;
    }

    private static boolean check6(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Disjunction) {
                Disjunction disj1 = (Disjunction) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean check7(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Disjunction) {
                Disjunction disj1 = (Disjunction) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getRight()));
            }
        }
        return false;
    }

    private static boolean check8(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl3 = (Implication) impl1.getRight();
                    if (impl3.getLeft() instanceof Implication) {
                        Implication impl4 = (Implication) impl3.getLeft();
                        if (impl3.getRight() instanceof Implication) {
                            Implication impl5 = (Implication) impl3.getRight();
                            if (impl5.getLeft() instanceof Disjunction) {
                                Disjunction disj1 = (Disjunction) impl5.getLeft();
                                boolean fl1 = impl2.getLeft().equals(disj1.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl4.getRight()) && impl2.getRight().equals(impl5.getRight());
                                boolean fl3 = impl4.getLeft().equals(disj1.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check9(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication)e;
            if (impl1.getLeft() instanceof Implication){
                Implication impl2 = (Implication)impl1.getLeft();
                if(impl1.getRight() instanceof Implication){
                    Implication impl3 = (Implication)impl1.getRight();
                    if (impl3.getLeft() instanceof Implication){
                        Implication impl4 = (Implication)impl3.getLeft();
                        if (impl4.getRight() instanceof Negation){
                            Negation neg1 = (Negation) impl4.getRight();
                            if (impl3.getRight() instanceof Negation) {
                                Negation neg2 = (Negation) impl3.getRight();
                                boolean fl1 = neg2.getExpr().equals(impl2.getLeft());
                                fl1 = fl1 && neg2.getExpr().equals(impl4.getLeft());
                                boolean fl2 = impl2.getRight().equals(neg1.getExpr());
                                return (fl1 && fl2);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check10(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Negation) {
                Negation neg1 = (Negation) impl1.getLeft();
                if (neg1.getExpr() instanceof Negation) {
                    Negation neg2 = (Negation) neg1.getExpr();
                    return impl1.getRight().equals(neg2.getExpr());
                }
            }
        }
        return false;
    }

    public static int checker(Expression e){
        if (check1(e)) return 1;
        if (check2(e)) return 2;
        if (check3(e)) return 3;
        if (check4(e)) return 4;
        if (check5(e)) return 5;
        if (check6(e)) return 6;
        if (check7(e)) return 7;
        if (check8(e)) return 8;
        if (check9(e)) return 9;
        if (check10(e)) return 10;
        return -1;
    }
}
