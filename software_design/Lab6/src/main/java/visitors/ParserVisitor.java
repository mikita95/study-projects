package visitors;

import tokens.Brace;
import tokens.NumberToken;
import tokens.Operation;
import tokens.Token;

import java.util.*;

/**
 * Created by nikita on 15.12.16.
 */
public class ParserVisitor implements TokenVisitor {

    private Stack<Token> operations;
    private List<Token> polish;

    private Map<Operation.Type, Integer> priorities = new HashMap<>();
    {
        priorities.put(Operation.Type.ADD, 0);
        priorities.put(Operation.Type.SUB, 0);
        priorities.put(Operation.Type.MUL, 1);
        priorities.put(Operation.Type.DIV, 1);
    }

    public ParserVisitor() {
        this.polish = new ArrayList<>();
        this.operations = new Stack<>();
    }

    @Override
    public void visit(NumberToken number) {
        polish.add(number);
    }

    @Override
    public void visit(Operation operation) {
        while (!operations.isEmpty()) {
            Token peek = operations.peek();
            if (peek instanceof Operation && priorities.get(operation.getType()) <= priorities.get(((Operation) peek).getType())) {
                operations.pop();
                polish.add(peek);
            } else break;
        }
        operations.push(operation);
    }

    @Override
    public void visit(Brace brace) {
        if (brace.isOpen()) operations.push(brace);
        else {
            while (!operations.isEmpty()) {
                Token pop = operations.pop();
                if (pop instanceof Brace && ((Brace) pop).isOpen()) return;
                polish.add(pop);
            }
            throw new IllegalStateException("Can't parse braces");
        }
    }

    public List<Token> getPolish() {
        while (!operations.isEmpty()) polish.add(operations.pop());
        return polish;
    }
}
