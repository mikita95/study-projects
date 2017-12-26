package tokens;

import visitors.TokenVisitor;

/**
 * Created by nikita on 15.12.16.
 */
public interface Token {
    void accept(TokenVisitor visitor);
}
