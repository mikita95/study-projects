package semantics;

import utils.Memoization;

import java.util.Map;
import java.util.Set;

public class Application extends Expression {
    protected Expression left, right;

    @Override
    public Application substitute(Variable variable, Expression expression) {
        return new Application(left.substitute(variable, expression), right.substitute(variable, expression));
    }

    @Override
    protected Variable bounds(Variable variable, Set<Variable> variables, Set<Variable> freeVariables) {
        Variable bounds = left.bounds(variable, variables, freeVariables);
        return bounds != null ? bounds : right.bounds(variable, variables, freeVariables);
    }

    @Override
    protected Application rename(Map<Variable, Variable> names) {
        return new Application(left.rename(names), right.rename(names));
    }

    @Override
    public Expression normalize() {
        if (Memoization.isContain1(this)) return Memoization.get1(this);
        Expression normalizeBeginning = normalizeBeginning((this).getLeft());
        if (normalizeBeginning instanceof Abstraction) {
            Abstraction abstraction = (Abstraction) normalizeBeginning;
            Expression normalForm = abstraction.getExpression().substitute(abstraction.getVariable(), this.getRight()).normalize();
            Memoization.write1(this, normalForm);
            return normalForm;
        } else {
            Application application = new Application(normalizeBeginning.normalize(), this.getRight().normalize());
            Memoization.write1(this, application);
            return application;
        }
    }


    private Expression normalizeBeginning(Expression expression) {
        if (Memoization.isContain2(expression)) return Memoization.get2(expression);
        if (expression instanceof Variable) {
            return expression;
        } else if (expression instanceof Abstraction) {
            Abstraction abstraction = new Abstraction(((Abstraction) expression).getVariable(), normalizeBeginning(((Abstraction) expression).getExpression()));
            Memoization.write2(expression, abstraction);
            return abstraction;
        } else if (expression instanceof Application) {
            Expression leftDone = normalizeBeginning(((Application) expression).getLeft());
            if (leftDone instanceof Abstraction) {
                Expression normalizeBeginning = normalizeBeginning(((Abstraction) leftDone).getExpression().substitute(((Abstraction) leftDone).getVariable(), ((Application) expression).getRight()));
                Memoization.write2(expression, normalizeBeginning);
                return normalizeBeginning;
            } else {
                Application application = new Application(leftDone, ((Application) expression).getRight());
                Memoization.write2(expression, application);
                return application;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Application && left.equals(((Application) o).getLeft()) && right.equals(((Application) o).getRight());
    }

    public Application(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Set<Variable> getVariables() {
        return null;
    }

    @Override
    protected Set<Variable> getFreeVariables(Set<Variable> variables) {
        Set<Variable> freeVariables = left.getFreeVariables(variables);
        freeVariables.addAll(right.getFreeVariables(variables));
        return freeVariables;
    }

    @Override
    public int hashCode() {
        return 2 * left.hashCode() + 3 * right.hashCode() + 5;
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
