package tokenizer;

import tokens.Brace;
import tokens.Operation;

/**
 * Created by nikita on 15.12.16.
 */
public class StartState implements State {
    @Override
    public void parse(Tokenizer tokenizer) {
        if (handleChar(tokenizer)) {
            tokenizer.nextChar();
        } else {
            if (Character.isDigit(tokenizer.getCurrent())) {
                tokenizer.setState(new NumberState());
            } else tokenizer.setState(new ErrorState());
        }
    }

    public boolean handleChar(Tokenizer tokenizer) {
        switch (tokenizer.getCurrent()) {
            case '(': return tokenizer.addToken(new Brace(true));
            case ')': return tokenizer.addToken(new Brace(false));
            case '+': return tokenizer.addToken(new Operation(Operation.Type.ADD));
            case '-': return tokenizer.addToken(new Operation(Operation.Type.SUB));
            case '*': return tokenizer.addToken(new Operation(Operation.Type.MUL));
            case '/': return tokenizer.addToken(new Operation(Operation.Type.DIV));
            case ' ': return true;
            case '\n': return true;
            default: return false;
        }
    }
}
