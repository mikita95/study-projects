public class ExpressionParser {
    public static String input;
    public static Character currToken;
    public static String number;
    private static int currentIndex;

    public static void getChar() {

        if (currentIndex >= input.length()) return;

        while (currentIndex < input.length() && Character.isWhitespace(input.charAt(currentIndex))) {
            currentIndex++;
        }
        number = "";
        boolean flag = false;
        while (currentIndex < input.length() && (Character.isDigit(input.charAt(currentIndex)))) {
            currToken = input.charAt(currentIndex);
            number += String.valueOf(input.charAt(currentIndex));
            currentIndex++;
            flag = true;
        }

        if (flag) return;
        if (currentIndex >= input.length()) return;
        if (!(Character.isDigit(input.charAt(currentIndex)))) {

            char tmp = input.charAt(currentIndex);
            currentIndex++;
            currToken = tmp;

        }
    }

    public static Expression3 parse(String s) {
        s = s.replaceAll("mod", "%");
        input = s;
        Expression3 v = new Const(0);
        currentIndex = 0;
        if (!input.isEmpty()) {

            getChar();
            v = expression(false);
        }
        return v;
    }

    public static Expression3 primary(boolean get) {
        if (get) {
            getChar();
        }
        if (currToken >= '0' && currToken <= '9') {
            Expression3 tmp = new Const((int) Long.parseLong(number));
            getChar();
            return tmp;
        } else if (currToken == '-') {
            return new Subtract(new Const(0), primary(true));
        } else if (currToken == '~') {
            return new Not(primary(true));
        } else if (currToken == '(') {
            Expression3 tmp = expression(true);
            getChar();
            return tmp;
        } else {
            Expression3 tmp = new Variable(String.valueOf(currToken));
            getChar();
            return tmp;
        }
    }

    public static Expression3 terminal(boolean get) {
        Expression3 left = primary(get);
        while (currentIndex < input.length()) {
            switch (currToken) {
                case '*': {
                    Expression3 tmp = primary(true);
                    left = new Multiply(left, tmp);
                    break;
                }
                case '/': {
                    Expression3 tmp = primary(true);
                    left = new Divide(left, tmp);
                    break;
                }
                case '%': {
                    Expression3 tmp = primary(true);
                    left = new Mod(left, tmp);
                    break;
                }
                default:
                    return left;
            }

        }
        return left;
    }

    public static Expression3 expression(boolean get) {
        Expression3 left = terminal(get);
        while (currentIndex < input.length()) {
            switch (currToken) {
                case '+': {
                    left = new Add(left, terminal(true));
                    break;
                }
                case '-': {
                    left = new Subtract(left, terminal(true));
                    break;
                }
                default:
                    return left;
            }
        }
        return left;
    }

}
