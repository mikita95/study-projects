package tokens;

import visitors.TokenVisitor;

import java.util.function.BiFunction;

/**
 * Created by nikita on 15.12.16.
 */
public class Operation implements Token {

    public enum Type {
        ADD("+"), SUB("-"), MUL("*"), DIV("/");

        private String name;
        Type(String name) {
            this.name = name;
        }

        public BiFunction<Double, Double, Double> get() {
            switch (this) {
                case ADD: return (x, y) -> x + y;
                case SUB: return (x, y) -> x - y;
                case MUL: return (x, y) -> x * y;
                case DIV: return (x, y) -> x / y;
            }
            return null;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private Type type;

    public Operation(Type type) {
        this.type = type;
    }

    public BiFunction<Double, Double, Double> body() {
        return this.type.get();
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
