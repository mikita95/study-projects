package tokenizer;

import tokens.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 15.12.16.
 */
public class Tokenizer {
    private InputStream inputStream;
    private List<Token> tokens;
    private State state;
    private Character current;

    public Tokenizer(InputStream inputStream) {
        this.inputStream = inputStream;
        this.tokens = new ArrayList<>();
        this.state = new StartState();
        this.current = null;
    }

    public Character getCurrent() {
        return this.current;
    }

    void nextChar() {
        try {
            int c = inputStream.read();
            if (c < 0) {
                current = null;
                return;
            }
            current = (char) c;
        } catch (IOException e) {
            throw new IllegalStateException("Read error");
        }
    }

    public List<Token> getTokens() {
        nextChar();
        while (current != null) {
            state.parse(this);
        }
        setState(new EndState());
        return tokens;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean addToken(Token token) {
        return this.tokens.add(token);
    }
}
