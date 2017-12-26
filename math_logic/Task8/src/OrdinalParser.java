public class OrdinalParser {
    public static String input;
    public static Character currToken;
    public static StringBuilder number;

    public static boolean flag = false;
    private static int currentIndex;

    public static void getChar() throws Exception {
        if (currentIndex > input.length()) {
            throw new Exception("parse fail");
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

    public static Ordinal parse(String s) throws Exception {
        input = s;
        Ordinal v = new Number(0);
        currentIndex = 0;
        if (!input.isEmpty()) {
            getChar();
            v = expression(false);
        }
        if (currentIndex - 1 != input.length())
            throw new Exception("parse fail");

        return v;
    }

    public static Ordinal primary(boolean get) throws Exception {
        if (get) {
            getChar();
        }
        if (Character.isDigit(currToken)) {
            int tmp1;
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
                throw new Exception("parse fail");
            }

            Ordinal tmp = new Number(tmp1);
            getChar();
            if (currentIndex <= input.length() && (currToken == 'w' || Character.isDigit(currToken)))
                throw new Exception("parse fail");
            return tmp;
        } else if (currToken == '-') {
            flag = false;
            Ordinal tmp = primary(true);
            if (!flag)
                return new Subtract(new Number(0), tmp);
            else {
                flag = false;
                return tmp;
            }
        } else if (currToken == '(') {
            Ordinal tmp = expression(true);
            if (currToken != ')') {
                throw new Exception("parse fail");
            }
            getChar();
            return tmp;
        } else if (currToken == ')') {
            throw new Exception("parse fail");
        } else if (currToken == 'w') {
            Ordinal tmp = new W();
            getChar();
            if (currentIndex <= input.length() && (Character.isDigit(currToken) || currToken == 'w'))
                throw new Exception("parse fail");
            return tmp;
        } else
            throw new Exception("parse fail");

    }

    public static Ordinal subTerminal(boolean get) throws Exception {
        Ordinal left = primary(get);
        while (currentIndex < input.length()) {
            switch (currToken) {
                case '^': {
                    Ordinal tmp = primary(true);
                    left = new Power(left, tmp);
                    break;
                }

                default:
                    return left;
            }

        }

        return left;
    }

    public static Ordinal terminal(boolean get) throws Exception {
        Ordinal left = subTerminal(get);

        while (currentIndex < input.length()) {
            switch (currToken) {
                case '*': {
                    Ordinal tmp = subTerminal(true);
                    left = new Multiply(left, tmp);
                    break;
                }

                default:
                    return left;
            }

        }

        return left;
    }

    public static Ordinal expression(boolean get) throws Exception {
        Ordinal left = terminal(get);
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
