package tokenizer;

/**
 * Created by nikita on 15.12.16.
 */
public class ErrorState implements State {
    @Override
    public void parse(Tokenizer tokenizer) {
        throw new IllegalStateException("Parse error");
    }
}
