package semantics;

import utils.Memoization;

import java.util.Map;
import java.util.Set;

public class Abstraction extends Expression {
    protected Variable variable;
    protected Expression expression;

    public Variable getVariable() {
        return variable;
    }

    public Abstraction(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }


    @Override
    public Abstraction substitute(Variable var, Expression e) {
        Variable variable1 = this.getVariable();
        Expression expression1 = this.getExpression();
        if (variable1.equals(var)) return this;
        else if (!expression1.getFreeVariables().contains(var)) return this;
        if (!e.getFreeVariables().contains(variable1))
            return new Abstraction(variable1, expression1.substitute(var, e));
        Variable nextVariable = Variable.nextVariable();
        Expression substitute = expression1.substitute(variable1, nextVariable);
        return new Abstraction(nextVariable, substitute.substitute(var, e));
    }

    @Override
    protected Variable bounds(Variable var, Set<Variable> variables, Set<Variable> freeVariables) {
        if (var.equals(variable)) return null;
        boolean flag = variables.contains(variable);
        if (!flag) variables.add(variable);
        Variable result = expression.bounds(var, variables, freeVariables);
        if (!flag) variables.remove(variable);
        return result;
    }

    @Override
    protected Abstraction rename(Map<Variable, Variable> names) {
        Variable v = names.get(variable);
        Variable nextVariable = Variable.nextVariable();
        names.put(variable, nextVariable);
        Expression rename = expression.rename(names);
        if (v == null) names.remove(variable);
        else names.put(variable, v);
        return new Abstraction(nextVariable, rename);
    }

    @Override
    public Expression normalize() {
        if (Memoization.isContain1(this)) return Memoization.get1(this);
        Abstraction abstraction = new Abstraction((this).getVariable(), this.getExpression().normalize());
        Memoization.write1(this, abstraction);
        return abstraction;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Abstraction && variable.equals(((Abstraction) o).getVariable()) && expression.equals(((Abstraction) o).getExpression());
    }

    @Override
    public Set<Variable> getVariables() {
        return null;
    }

    @Override
    protected Set<Variable> getFreeVariables(Set<Variable> variables) {
        boolean flag = variables.contains(variable);
        if (!flag) variables.add(variable);
        Set<Variable> freeVariables = expression.getFreeVariables(variables);
        if (!flag) variables.remove(variable);
        return freeVariables;
    }

    @Override
    public int hashCode() {
        return 7 * variable.hashCode() + 11 * expression.hashCode() + 13;
    }

    @Override
    public String toString() {
        return "(" + "\\" + variable + "." + expression + ")";
    }

    public Expression getExpression() {
        return expression;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
