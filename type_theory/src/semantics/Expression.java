package semantics;

import utils.FunctionSemantics;
import utils.LambdaSemantics;
import utils.Parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Expression {

    public static Expression parseExpressionInFunction(String s) {
        return Parser.parseExpressionInFunction(FunctionSemantics.getSemantics(s), 0).getResult();
    }

    protected abstract Set<Variable> getFreeVariables(Set<Variable> bounded);

    protected abstract Variable bounds(Variable variable, Set<Variable> variables, Set<Variable> variableSet);

    public Expression rename() {
        return rename(new HashMap<>());
    }

    protected abstract Expression rename(Map<Variable, Variable> names);

    public static Expression parseExpression(String s) {
        return Parser.parseExpression(LambdaSemantics.getSemantics(s));
    }

    public Set<Variable> getFreeVariables() {
        return getFreeVariables(new HashSet<>());
    }

    public abstract Set<Variable> getVariables();

    public abstract Expression normalize();

    public abstract Expression substitute(Variable var, Expression e);

    public Variable getNonFreeVariable(Variable var, Expression e) {
        return bounds(var, new HashSet<>(), e.getFreeVariables());
    }


}
