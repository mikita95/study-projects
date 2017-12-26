import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Axioms {
    private static Pattern[] statementsAxioms = {
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)->\\1[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}\\1->" +
                            "[\\(]{1}\\2->(.+)[\\)]{2}->[\\(]{1}\\1->\\3[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)->[\\(]{1}\\1&\\2[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)&(.+)[\\)]{1}->\\1[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)&(.+)[\\)]{1}->\\2[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}\\1\\|(.+)[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)\\|\\1[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}(.+)" +
                            "->\\2[\\)]{1}->[\\(]{2}\\1\\|\\3[\\)]{1}->\\2[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}\\1->!\\2[\\)]{1}->!\\1[\\)]{2}$"),
            Pattern.compile("^[\\(]{1}!!(.+)->\\1[\\)]{1}$")
    };


    private static Pattern[] arithmeticAxioms = {
            Pattern.compile(
                    "^[\\(]{2}(.+)=(.+)[\\)]{1}->[\\(]{1}\\1'=\\2'[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)=(.+)[\\)]{1}->[\\(]{2}\\1=(.+)[\\)]{1}->[\\(]{1}" +
                            "\\2=\\3[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)'=(.+)'[\\)]{1}->[\\(]{1}\\1=\\2[\\)]{2}$"),
            Pattern.compile(
                    "^![\\(]{1}(.+)'=0[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\+(.+)'=[\\(]{1}\\1\\+\\2[\\)]{1}'[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\+0=\\1[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\*0=0[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\*(.+)'=\\1\\*\\2\\+\\1[\\)]{1}$")


    };


    private static boolean flag = false;
    private static String errorMsg = "";


    public static boolean doesMatchAxioms(Expression exp) {

        flag = false;
        errorMsg = "";

        String expAsString = exp.toString();
        Matcher matcher;
        for (Pattern statementsAxiom : statementsAxioms) {
            matcher = statementsAxiom.matcher(expAsString);
            if (matcher.matches()) {
                return true;
            }
        }

        return doesMatchForallAxiom(exp) || !flag && doesMatchExistAxiom(exp) || doesMatchArithmeticsAxioms(exp);


    }

    private static boolean doesMatchForallAxiom(Expression exp) {
        if (exp instanceof Implication) {
            if (((Implication) exp).left instanceof Forall) {
                String var = (((Forall) ((Implication) exp).left).var);
                Expression phi = (((Forall) ((Implication) exp).left).expression);
                Expression newPhi = ((Implication) exp).right;
                return areTwoExpEqualAfterSubst(phi, newPhi, var);
            }
        }
        return false;
    }

    private static boolean doesMatchExistAxiom(Expression exp) {
        if (exp instanceof Implication) {
            if (((Implication) exp).right instanceof Exist) {
                String var = (((Exist) ((Implication) exp).right).var);
                Expression phi = (((Exist) ((Implication) exp).right).expression);
                Expression newPhi = ((Implication) exp).left;
                return areTwoExpEqualAfterSubst(phi, newPhi, var);
            }
        }
        return false;
    }

    private static boolean doesMatchArithmeticsAxioms(Expression exp) {
        Matcher matcher;
        String expAsString = exp.toString();
        Expression tmp;

        Expression left;
        Expression right;
        if (exp instanceof Implication) {
            if (((Implication) exp).left instanceof Equality &&
                    ((Implication) exp).right instanceof Equality) {

                matcher = arithmeticAxioms[0].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }

                left = ((Implication) exp).left;
                right = ((Implication) exp).right;
                if (((Equality) right).left instanceof Increment &&
                        ((Equality) right).right instanceof Increment) {
                    if (((Equality) left).left.toString().equals(
                            ((Increment) ((Equality) right).left).term.toString())
                            &&
                            ((Equality) left).right.toString().equals(
                                    ((Increment) ((Equality) right).right).term.toString())) {
                        return true;
                    }
                }
            }

            if (((Implication) exp).left instanceof Equality &&
                    ((Implication) exp).right instanceof Implication) {
                tmp = ((Implication) exp).right;
                if (((Implication) tmp).left instanceof Equality &&
                        ((Implication) tmp).right instanceof Equality) {
                    matcher = arithmeticAxioms[1].matcher(expAsString);
                    if (matcher.matches()) {
                        return true;
                    }
                }

            }

            if (((Implication) exp).left instanceof Equality &&
                    ((Implication) exp).right instanceof Equality) {
                matcher = arithmeticAxioms[2].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }

                left = ((Implication) exp).left;
                right = ((Implication) exp).right;
                if (((Equality) left).left instanceof Increment &&
                        ((Equality) left).right instanceof Increment) {
                    if (((Equality) right).left.toString().equals(
                            ((Increment) ((Equality) left).left).term.toString())
                            &&
                            ((Equality) right).right.toString().equals(
                                    ((Increment) ((Equality) left).right).term.toString())) {
                        return true;
                    }
                }

            }
        }

        if (exp instanceof Negation &&
                ((Negation) exp).expression instanceof Equality) {

            matcher = arithmeticAxioms[3].matcher(expAsString);
            if (matcher.matches()) {
                return true;
            }
        }

        if (exp instanceof Equality) {
            for (int i = 4; i < 8; i++) {
                matcher = arithmeticAxioms[i].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }
            }


            if (((Equality) exp).left instanceof Plus &&
                    ((Equality) exp).right instanceof Increment) {
                left = ((Equality) exp).left;
                right = ((Equality) exp).right;
                if (((Increment) right).term instanceof Plus &&
                        ((Plus) left).right instanceof Increment) {
                    // if (a1 == a2 && b1 == b2)
                    if (((Plus) left).left.toString().equals(
                            ((Plus) ((Increment) right).term).left.toString())
                            &&
                            ((Increment) ((Plus) left).right).term.toString().equals(
                                    ((Plus) ((Increment) right).term).right.toString())) {
                        return true;
                    }
                }
            }

            if (((Equality) exp).left instanceof Multiply &&
                    ((Equality) exp).right instanceof Plus) {

                left = ((Equality) exp).left;
                right = ((Equality) exp).right;
                if (((Multiply) left).right instanceof Increment &&
                        ((Plus) right).left instanceof Multiply) {
                    if (((Multiply) left).left.toString().equals(
                            ((Plus) right).right.toString())
                            &&
                            ((Multiply) left).left.toString().equals(
                                    ((Multiply) ((Plus) right).left).left.toString())
                            &&
                            ((Increment) ((Multiply) left).right).term.toString().equals(
                                    ((Multiply) ((Plus) right).left).right.toString())) {

                        return true;
                    }
                }
            }
        }

        Expression phi;
        Expression phiWithZero;
        Expression phiWithInc;
        String x;
        if (exp instanceof Implication) {
            phi = ((Implication) exp).right;
            if (((Implication) exp).left instanceof Conjunction) {
                tmp = ((Conjunction) (((Implication) exp).left)).right;
                if (tmp instanceof Forall) {
                    x = ((Forall) tmp).getVar();
                    phiWithZero = ((Conjunction) (((Implication) exp).left)).left;
                    String tmpString = phi.toStringWithReplacedVar(new Zero(), x);
                    if (tmpString.equals(phiWithZero.toString())) {
                        tmp = ((Forall) tmp).getExpression();
                        if (tmp instanceof Implication) {
                            phiWithInc = ((Implication) tmp).getRight();
                            tmpString = phi.toStringWithReplacedVar(new Increment(new Variable(x)), x);
                            if (tmpString.equals(phiWithInc.toString())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean areTwoExpEqualAfterSubst(Expression phi, Expression newPhi, String var) {
        List<Pair> pathToFirstFreeEntry = phi.pathToFirstFreeEntry(var);
        if (pathToFirstFreeEntry == null) {
            return (newPhi.toString().equals(phi.toString()));
        }
        Term replacingTerm = getReplacingTerm(pathToFirstFreeEntry, newPhi);
        if (replacingTerm != null) {
            String expWithReplacedVar = phi.toStringWithReplacedVar(replacingTerm, var);
            if (expWithReplacedVar.equals(newPhi.toString())) {
                if (phi.isFreeToReplace(replacingTerm, var)) {
                    return true;
                } else {
                    flag = true;
                    errorMsg = ("терм " + replacingTerm.toString() +
                            " не свободен для подстановки в формулу " +
                            phi.toString() + " вместо переменной " + var);
                }
            }
        }
        return false;
    }

    private static Term getReplacingTerm(List<Pair> path, Expression newPhi) {
        Expression exp = newPhi;
        for (Pair expPair : path) {
            switch (expPair.expressionType) {
                case Main.IMPLICATION:
                    if (exp instanceof Implication) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Implication) exp).left;
                        } else {
                            exp = ((Implication) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.CONJUCNTION:
                    if (exp instanceof Conjunction) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Conjunction) exp).left;
                        } else {
                            exp = ((Conjunction) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.DISJUCNTION:
                    if (exp instanceof Disjunction) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Disjunction) exp).left;
                        } else {
                            exp = ((Disjunction) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.EQUALITY:
                    if (exp instanceof Equality) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Equality) exp).left;
                        } else {
                            exp = ((Equality) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.NEGATION:
                    if (exp instanceof Negation) {
                        exp = ((Negation) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Main.FORALL:
                    if (exp instanceof Forall &&
                            ((Forall) exp).var.equals(expPair.additionalInfo)) {

                        exp = ((Forall) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Main.EXIST:
                    if (exp instanceof Exist &&
                            ((Exist) exp).var.equals(expPair.additionalInfo)) {

                        exp = ((Exist) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Main.PREDICATE:
                    if (exp instanceof Predicate) {
                        Pair pair = (Pair) expPair.additionalInfo;
                        List<Term> subTerms = ((Predicate) exp).getSubTerms();

                        if (pair.expressionType.equals(((Predicate) exp).getValue()) &&
                                subTerms.size() > ((Integer) pair.additionalInfo)) {

                            exp = subTerms.get(((Integer) pair.additionalInfo));
                            continue;
                        }
                    }
                    return null;
                case Main.PLUS:
                    if (exp instanceof Plus) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Plus) exp).left;
                        } else {
                            exp = ((Plus) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.MULTIPLY:
                    if (exp instanceof Multiply) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Multiply) exp).left;
                        } else {
                            exp = ((Multiply) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Main.INCREMENT:
                    if (exp instanceof Increment) {
                        exp = ((Increment) exp).term;
                        continue;
                    } else {
                        return null;
                    }
                case Main.TERMWITHARGS:
                    if (exp instanceof TermWithArgs) {

                        Pair pair = (Pair) expPair.additionalInfo;
                        String value = ((TermWithArgs) exp).getValue();
                        List<Term> subTerms = ((TermWithArgs) exp).getSubTerms();
                        if (value.equals(pair.expressionType) &&
                                subTerms.size() > ((Integer) pair.additionalInfo)) {
                            exp = subTerms.get(((Integer) pair.additionalInfo));
                            continue;
                        }
                    } else {
                        return null;
                    }
                case Main.BRACKETS:
                    if (exp instanceof Brackets) {
                        exp = ((Brackets) exp).term;
                        continue;
                    } else {
                        return null;
                    }
                case Main.VARIABLE:
                    if (exp instanceof Term) {
                        return (Term) exp;
                    } else {
                        return null;
                    }
            }
        }
        return null;
    }

    public static boolean getFlag() {
        return flag;
    }

    public static String getErrorMsg() {
        return errorMsg;
    }
}
