package tokens;

import visitors.TokenVisitor;

/**
 * Created by nikita on 15.12.16.
 */
public class NumberToken implements Token {

    public Integer value;

    public NumberToken(Integer value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
