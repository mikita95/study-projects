import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {

    public List<String> getAllVariables(String line) {
        Set<String> matches = new HashSet<String>();
        String regexp = "[A-Z][0-9]*";
        Matcher m = Pattern.compile("(?=(" + regexp + "))").matcher(line);
        while (m.find()) {
            matches.add(m.group(1));
        }
        return Lists.newArrayList(matches);
    }

    private static class StringWithBrackets {
        private final String string;
        private final int[] indexOfPairBracket;

        private StringWithBrackets(String string) {
            this.string = string;
            this.indexOfPairBracket = new int[string.length()];
            Stack<Integer> stack = new Stack<Integer>();
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '(') {
                    stack.push(i);
                } else if (string.charAt(i) == ')') {
                    Preconditions.checkArgument(!stack.empty(), "Input string has no pair opening bracket for bracket at index=" + i);
                    int openIndex = stack.pop();
                    indexOfPairBracket[openIndex] = i;
                    indexOfPairBracket[i] = openIndex;
                }
            }
            Preconditions.checkArgument(stack.empty(), "Input string has unclosed bracket");
        }

        public char charAt(int index) {
            return string.charAt(index);
        }

        public int getClosingBracket(int openIndex) {
            Preconditions.checkArgument(string.charAt(openIndex) == '(', "This is not an opening bracket");
            return indexOfPairBracket[openIndex];
        }

        public int getOpeningBracket(int closeIndex) {
            Preconditions.checkArgument(string.charAt(closeIndex) == ')', "This is not an closing bracket");
            return indexOfPairBracket[closeIndex];
        }
    }

    public Expression parseExpression(String source) {
        return parseExpression(new StringWithBrackets(source), 0, source.length());
    }

    private Expression parseExpression(StringWithBrackets source, int from, int to) {
        for (int i = from; i < to; i++) {
            if (source.charAt(i) == '(') {
                i = source.getClosingBracket(i);
                continue;
            }
            if (source.charAt(i) == '-') {
                Preconditions.checkArgument(source.charAt(i + 1) == '>', "Incomplete symbol '->'");
                return new Sequence(parseDisjunction(source, from, i), parseExpression(source, i + 2, to));
            }
        }
        return parseDisjunction(source, from, to);
    }

    private Expression parseDisjunction(StringWithBrackets source, int from, int to) {
        for (int i = to - 1; i >= from; i--) {
            if (source.charAt(i) == ')') {
                i = source.getOpeningBracket(i);
                continue;
            }
            if (source.charAt(i) == '|') {
                return new Disjunction(parseDisjunction(source, from, i), parseConjunction(source, i + 1, to));
            }
        }
        return parseConjunction(source, from, to);
    }

    private Expression parseConjunction(StringWithBrackets source, int from, int to) {
        for (int i = to - 1; i >= from; i--) {
            if (source.charAt(i) == ')') {
                i = source.getOpeningBracket(i);
                continue;
            }
            if (source.charAt(i) == '&') {
                return new Conjunction(parseConjunction(source, from, i), parseNegation(source, i + 1, to));
            }
        }
        return parseNegation(source, from, to);
    }

    private Expression parseNegation(StringWithBrackets source, int from, int to) {
        if (source.charAt(from) == '!') {
            return new Negation(parseNegation(source, from + 1, to));
        } else if (source.charAt(from) == '(') {
            Preconditions.checkState(source.getClosingBracket(from) == to - 1);
            return parseExpression(source, from + 1, to - 1);
        } else {
            return createNamed(source.string.substring(from, to));
        }
    }

    protected Expression createNamed(String name) {
        Preconditions.checkState(name.matches("[A-Z][0-9]*"), "Incorrect variable name: " + name);
        return new Variable(name);
    }

}