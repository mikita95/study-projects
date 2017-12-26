package utils;

import semantics.TypedExpression;

public class TypedParseResult {
    public TypedExpression result;
    public int tail;

    public TypedParseResult(TypedExpression result, int tail) {
        this.result = result;
        this.tail = tail;
    }

    public TypedExpression getResult() {
        return result;
    }

    public void setResult(TypedExpression result) {
        this.result = result;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }
}