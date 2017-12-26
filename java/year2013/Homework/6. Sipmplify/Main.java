public class Main {

    public static void main (String[] args) {
        String s ="(2+2)*3";
        ExpressionParser m = new ExpressionParser();
        Expression3 tmp = m.parse(s);
        System.err.println(tmp.toString1());

        System.err.println(tmp.simplify());
    }
}
