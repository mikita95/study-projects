public class ExpressionParser {
    public static String input;
    public static Character currToken;
    public static StringBuilder number;

    public static boolean flag = false;
    private static int currentIndex;

    public static void getChar() throws Exception {
        if (currentIndex > input.length()) {
            throw new BadToken("parse fail");
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
        while (currentIndex < input.length() && (Character.isDigit(input.charAt(currentIndex)))) {
            currToken = input.charAt(currentIndex);
            number.append(String.valueOf(input.charAt(currentIndex)));
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

    public static Expression3 parse(String s) throws Exception {
        s = s.replaceAll("mod", "%");
        input = s;
        Expression3 v = new Const(0);
        currentIndex = 0;
        if (!input.isEmpty()) {

            getChar();
            v = expression(false);
        }
        if (currentIndex - 1 != input.length())
            throw new BadToken("parse fail");

        return v;
    }

    public static Expression3 primary(boolean get) throws Exception {
        if (get) {
            getChar();
        }
        if (Character.isDigit(currToken)) {
            int tmp1 = 0;

            if (number.toString().equals(String.valueOf(Integer.MIN_VALUE).substring(1))) {
                flag = true;
            }
            try {
                if (flag) {
                    number = new StringBuilder();
                    number.append(Integer.MIN_VALUE);
                }

                tmp1 = Integer.parseInt(number.toString());
            } catch (Exception e) {
                throw new BadToken("parse fail");
            }

            Expression3 tmp = new Const(tmp1);
            getChar();
            if (currentIndex <= input.length() && (currToken == 'x' || currToken == 'y' || currToken == 'z' || Character.isDigit(currToken)))
                throw new BadToken("parse fail");
            return tmp;
        } else if (currToken == '-') {
            flag = false;
            Expression3 tmp = primary(true);
            if (!flag)
                return new Subtract(new Const(0), tmp);
            else
            {
                flag = false;
                return tmp;
            }
        } else if (currToken == '~') {
            return new Not(primary(true));
        } else if (currToken == '(') {
            Expression3 tmp = expression(true);
            if (currToken != ')') {
                throw new BadToken("parse fail");
            }
            getChar();
            return tmp;
        } else if (currToken == ')') {
            throw new BadToken("parse fail");
        } else if (currToken == 'x' || currToken == 'y' || currToken == 'z') {
            Expression3 tmp = new Variable(String.valueOf(currToken));
            getChar();
            if (currentIndex <= input.length() && (Character.isDigit(currToken) || currToken == 'x' || currToken == 'y' || currToken == 'z'))
                throw new BadToken("parse fail");
            return tmp;
        } else
            throw new BadToken("parse fail");

    }

    public static Expression3 terminal(boolean get) throws Exception {
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

    public static Expression3 expression(boolean get) throws Exception {
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
