package visitors;

import tokens.Brace;
import tokens.NumberToken;
import tokens.Operation;

/**
 * Created by nikita on 15.12.16.
 */
public interface TokenVisitor {
    void visit(NumberToken number);
    void visit(Operation operation);
    void visit(Brace brace);
}
