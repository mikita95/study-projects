package visitors;

import tokens.Brace;
import tokens.NumberToken;
import tokens.Operation;

/**
 * Created by nikita on 15.12.16.
 */
public class PrintVisitor implements TokenVisitor {

    private StringBuilder stringBuilder;

    public PrintVisitor() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void visit(NumberToken number) {
        stringBuilder.append(number.value).append(" ");
    }

    @Override
    public void visit(Operation operation) {
        stringBuilder.append(operation.getType()).append(" ");
    }

    @Override
    public void visit(Brace brace) {
        stringBuilder.append(brace.isOpen() ? "(" : ")");
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
