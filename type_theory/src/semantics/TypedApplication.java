package semantics;

import java.util.Map;
import java.util.Set;

public class TypedApplication extends TypedExpression {
    protected TypedExpression left, right;

    public TypedApplication(TypedExpression left, TypedExpression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public TypeResult getType(Map<TypedVariable, TypesVariable> context) {
        TypeResult leftType = left.getType(context);
        TypeResult rightType = right.getType(leftType.getContext());
        TypesVariable typesVariable = TypesVariable.nextTypeVariable();
        TypedEquationsSystem typedEquationsSystem = new TypedEquationsSystem(leftType.getTypedEquationsSystem(), rightType.getTypedEquationsSystem(), leftType.getType(), new TypesImplication(rightType.getType(), typesVariable));
        Map<TypedVariable, TypesVariable> context1 = leftType.getContext();
        context1.putAll(rightType.getContext());
        return new TypeResult(typedEquationsSystem, typesVariable, context1);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypedApplication && left.equals(((TypedApplication) o).getLeft()) && right.equals(((TypedApplication) o).getRight());
    }

    @Override
    public int hashCode() {
        return 2 * left.hashCode() + 3 * right.hashCode() + 5;
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }

    public TypedExpression getLeft() {
        return left;
    }

    public void setLeft(TypedExpression left) {
        this.left = left;
    }

    @Override
    protected TypedApplication rename(Map<TypedVariable, TypedVariable> map) {
        return new TypedApplication(left.rename(map), right.rename(map));
    }

    @Override
    protected Set<TypedVariable> getVariables() {
        Set<TypedVariable> variables = left.getVariables();
        variables.addAll(right.getVariables());
        return variables;
    }

    public TypedExpression getRight() {
        return right;
    }

    public void setRight(TypedExpression right) {
        this.right = right;
    }
}
