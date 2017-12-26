import java.util.Map;


public class Variable implements Expression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return var.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Variable) {
            Variable v = (Variable) o;
            return v.name.equals(name);
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return name;
    }

}
