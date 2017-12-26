package utils;

import semantics.Expression;

public class ParseResult {
    private Expression result;
    private int tail;

    public ParseResult(Expression result, int tail) {
        this.result = result;
        this.tail = tail;
    }

    public Expression getResult() {
        return result;
    }

    public int getTail() {
        return tail;
    }

    public void setResult(Expression result) {
        this.result = result;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }
}