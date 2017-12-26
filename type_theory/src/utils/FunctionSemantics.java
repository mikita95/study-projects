package utils;

import java.util.ArrayList;
import java.util.List;

public class FunctionSemantics {

    public static List<String> getSemantics(String s) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == ',') strings.add(s.substring(i, i + 1));
            else {
                String name = s.substring(i, ++i);
                while (i <  s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '`')) name += s.charAt(i++);
                strings.add(name);
                i--;
            }
        }
        return strings;
    }
}