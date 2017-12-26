package tokenizer;

/**
 * Created by nikita on 15.12.16.
 */
public interface State {
    void parse(Tokenizer tokenizer);
}
