package tokenizer;

import tokens.NumberToken;

/**
 * Created by nikita on 15.12.16.
 */
public class NumberState implements State {

    @Override
    public void parse(Tokenizer tokenizer) {
        Integer value = 0;
        while (tokenizer.getCurrent() != null && Character.isDigit(tokenizer.getCurrent())) {
            value = value * 10 + tokenizer.getCurrent() - 48;
            tokenizer.nextChar();
        }
        tokenizer.addToken(new NumberToken(value));
        tokenizer.setState(new StartState());
    }
}
