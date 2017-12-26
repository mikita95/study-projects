package tokens;

import visitors.TokenVisitor;

/**
 * Created by nikita on 15.12.16.
 */
public class Brace implements Token {
    private boolean isOpen;

    public Brace(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isOpen() {
        return this.isOpen;
    }
}
