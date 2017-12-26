package ru.ifmo.md.lesson4;

public class ExpressionParser {
    private static StringBuilder input;
    private static Character currToken;
    private static StringBuilder number;
    private static int currentIndex;


    private static void getChar() throws Throwable {

        if (currentIndex > input.length()) {
            throw new Throwable();
        }

        if (currentIndex >= input.length()) {
            currentIndex++;

            return;
        }

        while (currentIndex < input.length() && Character.isWhitespace(input.charAt(currentIndex))) {
            currentIndex++;
        }
        number = new StringBuilder();
        boolean flag = false;
        while (currentIndex < input.length() && (Character.isDigit(input.charAt(currentIndex)) || input.charAt(currentIndex) == '.' || input.charAt(currentIndex) == 'E')) {
            currToken = input.charAt(currentIndex);
            number.append(String.valueOf(input.charAt(currentIndex)));
            currentIndex++;
            if (input.charAt(currentIndex - 1) == 'E' && currentIndex < input.length() && input.charAt(currentIndex) == '-') {
                currToken = input.charAt(currentIndex);
                number.append(String.valueOf(input.charAt(currentIndex)));
                currentIndex++;
            }
            flag = true;
        }

        if (flag) return;
        if (currentIndex >= input.length()) return;
        if (!(Character.isDigit(input.charAt(currentIndex)))) {

            char tmp = input.charAt(currentIndex);
            if (tmp == 'm') {
                currentIndex++;
                tmp = '%';
            }
            currentIndex++;
            currToken = tmp;

        }
    }

    public static <E extends MyType<E>> Expression3<E> parse(String s, E format) throws Throwable {
        s = s.replaceAll(" ","");
        s = s.replaceAll("\t","");
        input = new StringBuilder(s);
        Expression3<E> v;
        currentIndex = 0;
        if (s.isEmpty())
            return new Const<E>(format.stringToMyType("0"));
        getChar();
        v = expression(false, format);
        if (currentIndex - 1 != input.length())
            throw new Throwable();
        return v;
    }

    private static <E extends MyType<E>> Expression3<E> primary(boolean get, E f) throws Throwable {
        if (get) {
            getChar();
        }
        if (Character.isDigit(currToken) || currToken == '.') {
            Expression3<E> tmp;

            tmp = new Const<E>(f.stringToMyType(number.toString()));


            getChar();
            return tmp;
        } else if (currToken == '-') {
            return new Neg<E>(primary(true, f));
        } else if (currToken == '+') {
            return new Plus<E>(primary(true, f));

        } else if (currToken == '~') {
            return new Not<E>(primary(true, f));

        } else if (currToken == 'a') {
            getChar();
            getChar();
            getChar();
            Expression3<E> tmp = new Abs<E>(expression(true, f));
            getChar();
            return tmp;
        } else if (currToken == '(') {
            Expression3<E> tmp = expression(true, f);
            getChar();
            return tmp;
        } else {
            Expression3<E> tmp = new Variable<E>(String.valueOf(currToken));

            getChar();
            return tmp;
        }
    }

    private static <E extends MyType<E>> Expression3<E> terminal(boolean get, E f) throws Throwable {
        Expression3<E> left = primary(get, f);
        while (currentIndex < input.length()) {
            switch (currToken) {
                case '*': {
                    Expression3<E> tmp = primary(true, f);
                    left = new Multiply<E>(left, tmp);
                    break;
                }
                case '/': {
                    Expression3<E> tmp = primary(true, f);
                    left = new Divide<E>(left, tmp);
                    break;
                }
                case '%': {
                    Expression3<E> tmp = primary(true, f);
                    left = new Mod<E>(left, tmp);
                    break;
                }
                default:
                    return left;
            }

        }
        return left;
    }

    private static <E extends MyType<E>> Expression3<E> expression(boolean get, E f) throws Throwable {
        Expression3<E> left = terminal(get, f);
        while (currentIndex < input.length()) {
            switch (currToken) {
                case '+': {
                    left = new Add<E>(left, terminal(true, f));
                    break;
                }
                case '-': {
                    left = new Subtract<E>(left, terminal(true, f));
                    break;
                }
                default:
                    return left;
            }
        }
        return left;
    }

}
