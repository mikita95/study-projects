package semantics;

import java.util.Map;
import java.util.Set;

public class TypedAbstraction extends TypedExpression {
    protected TypedVariable variable;
    protected TypedExpression typedExpression;


    @Override
    public TypeResult getType(Map<TypedVariable, TypesVariable> context) {
        TypeResult typedExpressionType = typedExpression.getType(context);
        TypesVariable typesVariable = null;
        if (typedExpressionType.getContext().containsKey(variable))
            typesVariable = typedExpressionType.getContext().get(variable);
        else {
            typesVariable = TypesVariable.nextTypeVariable();
            typedExpressionType.getContext().put(variable, typesVariable);
        }
        return new TypeResult(typedExpressionType.getTypedEquationsSystem(), new TypesImplication(typesVariable, typedExpressionType.getType()), typedExpressionType.getContext());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypedAbstraction && variable.equals(((TypedAbstraction) o).getVariable()) && typedExpression.equals(((TypedAbstraction) o).getTypedExpression());
    }

    @Override
    public int hashCode() {
        return 7 * variable.hashCode() + 11 * typedExpression.hashCode() + 13;
    }

    public TypedAbstraction(TypedVariable variable, TypedExpression typedExpression) {
        this.variable = variable;
        this.typedExpression = typedExpression;
    }

    @Override
    protected TypedAbstraction rename(Map<TypedVariable, TypedVariable> map) {
        boolean isDone = map.containsKey(variable);
        TypedVariable name = null;
        TypedVariable nextVariable = TypedVariable.nextVariable();
        if (isDone) name = map.get(variable);
        map.put(variable, nextVariable);
        TypedExpression expression = typedExpression.rename(map);
        if (isDone) map.put(variable, name);
        else map.remove(variable);
        return new TypedAbstraction(nextVariable, expression);
    }

    @Override
    protected Set<TypedVariable> getVariables() {
        Set<TypedVariable> result = typedExpression.getVariables();
        result.add(variable);
        return result;
    }

    @Override
    public String toString() {
        return "(" + "\\" + variable + "." + typedExpression + ")";
    }

    public TypedVariable getVariable() {
        return variable;
    }

    public void setVariable(TypedVariable variable) {
        this.variable = variable;
    }

    public TypedExpression getTypedExpression() {
        return typedExpression;
    }

    public void setTypedExpression(TypedExpression typedExpression) {
        this.typedExpression = typedExpression;
    }
}
