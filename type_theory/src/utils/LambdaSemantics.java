package utils;

public class LambdaSemantics {

    public static SemanticsPool getSemantics(String s) {
        SemanticsPool pool = new SemanticsPool();
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '\\' || s.charAt(i) == '(' || s.charAt(i) == ')') pool.add(s.substring(i, i + 1));
            else if (Character.isLetter(s.charAt(i))) {
                String name = s.substring(i, ++i);
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '`')) name += s.charAt(i++);
                i--;
                pool.add(name);
            }
        return pool;
    }
}