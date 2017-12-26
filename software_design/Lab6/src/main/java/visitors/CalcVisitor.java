package visitors;

import tokens.Brace;
import tokens.NumberToken;
import tokens.Operation;

import java.util.Stack;

/**
 * Created by nikita on 15.12.16.
 */
public class CalcVisitor implements TokenVisitor {

    private Stack<Double> stack;

    public CalcVisitor() {
        this.stack = new Stack<>();
    }

    public Double getResult() {
        if (stack.size() != 1) {
            throw new IllegalStateException("Parse error");
        }
        return stack.peek();
    }

    @Override
    public void visit(NumberToken number) {
        stack.push(Double.valueOf(number.value));
    }

    @Override
    public void visit(Operation operation) {
        Double first = stack.pop();
        Double second = stack.pop();
        stack.push(operation.body().apply(first, second));
    }

    @Override
    public void visit(Brace brace) {
        throw new IllegalStateException("Polish notation shouldn't contains braces");
    }
}
