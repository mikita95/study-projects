import java.util.Map;


public interface Expression {

    public abstract boolean evaluate(Map<String, Boolean> variables);
}
