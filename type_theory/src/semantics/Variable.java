package semantics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variable extends Expression {
    protected String name;
    private static long number;

    @Override
    protected Variable bounds(Variable variable, Set<Variable> variables, Set<Variable> freeVariables) {
        if (variable.equals(this)) for (Variable v : freeVariables) if (variables.contains(v)) return v;
        return null;
    }

    @Override
    protected Variable rename(Map<Variable, Variable> map) {
        Variable variable = map.get(this);
        if (variable == null) {
            Variable nextVariable = Variable.nextVariable();
            map.put(this, nextVariable);
            return nextVariable;
        }
        return variable;
    }

    @Override
    public Expression normalize() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && name.equals(((Variable) o).getName());
    }

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();
        variables.add(this);
        return variables;
    }

    public Variable(String name) {
        this.name = name;
    }

    public static Variable nextVariable() {
        return new Variable("v" + number++);
    }

    @Override
    protected Set<Variable> getFreeVariables(Set<Variable> variables) {
        Set<Variable> freeVariables = new HashSet<>();
        if (!variables.contains(this)) freeVariables.add(this);
        return freeVariables;
    }

    @Override
    public Expression substitute(Variable variable, Expression expression) {
        return variable.equals(this) ? expression : this;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getNumber() {
        return number;
    }

    public static void setNumber(long number) {
        Variable.number = number;
    }
}
