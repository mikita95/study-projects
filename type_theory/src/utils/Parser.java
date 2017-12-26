package utils;

import semantics.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static Expression parseExpression(SemanticsPool semanticsPool) {
        if (semanticsPool.peek().equals("\\")) {
            semanticsPool.pop();
            return new Abstraction(new Variable(semanticsPool.pop()), parseExpression(semanticsPool));
        }
        Expression expression = parseApplication(semanticsPool);
        if (!semanticsPool.isEmpty())
            if (semanticsPool.peek().equals("\\"))
                expression = new Application(expression, parseExpression(semanticsPool));
        return expression;
    }

    public static Expression parseApplication(SemanticsPool semanticsPool) {
        Expression expression = parseSimple(semanticsPool);
        while (true) {
            if (!(!semanticsPool.isEmpty() && !semanticsPool.peek().equals(")") && !semanticsPool.peek().equals("\\")))
                break;
            expression = new Application(expression, parseSimple(semanticsPool));
        }
        return expression;
    }

    public static Expression parseSimple(SemanticsPool semanticsPool) {
        if (semanticsPool.peek().equals("(")) {
            semanticsPool.pop();
            Expression expression = parseExpression(semanticsPool);
            semanticsPool.pop();
            return expression;
        }
        return parseVariable(semanticsPool);
    }

    public static ParseResult parseExpressionInFunction(List<String> strings, int position) {
        if (position + 1 < strings.size())
            if (strings.get(position + 1).equals("(")) {
                int i = position + 1;
                List<Expression> expressions = new ArrayList<>();
                while (true) {
                    if (strings.get(i).equals(")")) break;
                    ParseResult result = parseExpressionInFunction(strings, i + 1);
                    expressions.add(result.getResult());
                    i = result.getTail();
                }
                return new ParseResult(new Function(strings.get(position), expressions), i + 1);
            } else {
                return new ParseResult(new Variable(strings.get(position)), position + 1);
            }
        return new ParseResult(new Variable(strings.get(position)), position + 1);
    }

    public static Variable parseVariable(SemanticsPool semanticsPool) {
        return new Variable(semanticsPool.pop());
    }

    public static TypedParseResult parseTypedAbstraction(List<String> strings, int position) {
        if (strings.get(position).equals("\\")) {
            TypedParseResult result = parseTypedAbstraction(strings, position + 2);
            return new TypedParseResult(new TypedAbstraction(new TypedVariable(strings.get(position + 1)), result.result), result.tail);
        }
        return parseTypedApplication(strings, position);
    }

    public static TypedParseResult parseTypedApplication(List<String> strings, int position) {
        TypedParseResult typedParseResult = parseTerm(strings, position);
        TypedExpression typedExpression = typedParseResult.result;
        int i = typedParseResult.tail + 1;
        while (i < strings.size() && !strings.get(i).equals(")")) {
            typedParseResult = parseTerm(strings, i);
            typedExpression = new TypedApplication(typedExpression, typedParseResult.result);
            i = typedParseResult.tail + 1;
        }
        return new TypedParseResult(typedExpression, i);
    }

    public static TypedParseResult parseTerm(List<String> strings, int position) {
        return strings.get(position).equals("(") ? parseTypedAbstraction(strings, position + 1) : new TypedParseResult(new TypedVariable(strings.get(position)), position);
    }
}
