public class GenericParser {

    private static <E extends MyType<E>> void run(Expression3<E> expression, E t) {

        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                try {
                    System.out.println(expression.evaluate(t.getValue(x), t.getValue(y), t.getValue(0)).myTypeToString());
                } catch (DivisionByZero e) {
                    System.out.println("error");
                } catch (Throwable e) {
                    System.out.println("fatal error");
                }

            }
        }
    }

    public static void main(String[] args) throws Throwable {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            s.append(args[i]);

        if (args[0].equals("-i")) {
            run(ExpressionParser.parse(s.toString(), new MyInteger()), new MyInteger());
        } else if (args[0].equals("-d")) {
            run(ExpressionParser.parse(s.toString(), new MyDouble()), new MyDouble());
        } else if (args[0].equals("-bi")) {
            run(ExpressionParser.parse(s.toString(), new MyBigInteger()), new MyBigInteger());
        }


    }
}
