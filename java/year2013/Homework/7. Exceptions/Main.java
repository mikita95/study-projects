public class Main {

    public static void main(String[] args) {

        String s = "" + String.valueOf(Integer.MIN_VALUE);
        ExpressionParser m = new ExpressionParser();
        try {
            System.out.println(m.parse(s).evaluate(1, 2, 3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
