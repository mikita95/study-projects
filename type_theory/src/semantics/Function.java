package semantics;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Function extends Expression {
    public String name;
    public List<Expression> arguments;

    @Override
    protected Expression rename(Map<Variable, Variable> map) {
        return null;
    }

    @Override
    public Expression normalize() {
        return null;
    }

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();
        for (Expression e : arguments) variables.addAll(e.getVariables());
        return variables;
    }

    @Override
    protected Set<Variable> getFreeVariables(Set<Variable> variables) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Function && name.equals(((Function) o).getName()) && arguments.size() == ((Function) o).getArguments().size()) {
            for (int i = 0; i < arguments.size(); i++)
                if (!arguments.get(i).equals(((Function) o).getArguments().get(i))) return false;
            return true;
        }
        return false;
    }

    public Function(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Expression substitute(Variable v, Expression e) {
        List<Expression> expressions = arguments.stream().map(expression -> expression.substitute(v, e)).collect(Collectors.toList());
        return new Function(name, expressions);
    }

    @Override
    protected Variable bounds(Variable variable, Set<Variable> variables, Set<Variable> freeVariables) {
        return null;
    }


    @Override
    public int hashCode() {
        int hashCode = name.hashCode();
        for (Expression arg : arguments) hashCode += arg.hashCode();
        return hashCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String result = name + "(";
        for (int i = 0; i < arguments.size(); i++) result += arguments.get(i) + (i == arguments.size() - 1 ? "" : ",");
        return result + ")";
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }
}
