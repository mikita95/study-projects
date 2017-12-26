package semantics;

import java.util.Set;

public class TypesImplication extends Type {
    protected Type left, right;

    @Override
    public boolean equals(Object o) {
        return o instanceof TypesImplication && left.equals(((TypesImplication) o).getLeft()) && right.equals(((TypesImplication) o).getRight());
    }

    @Override
    public int hashCode() {
        return 2 * left.hashCode() + 3 * right.hashCode() + 5;
    }

    @Override
    public String toString() {
        return "(" + left + ")->(" + right + ")";
    }

    public Type getLeft() {
        return left;
    }

    public TypesImplication(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public TypesImplication substitute(TypesVariable typesVariable, Type t) {
        return new TypesImplication(left.substitute(typesVariable, t), right.substitute(typesVariable, t));
    }

    @Override
    public Set<TypesVariable> getVariables() {
        Set<TypesVariable> variables = left.getVariables();
        variables.addAll(right.getVariables());
        return variables;
    }

    public void setLeft(Type left) {
        this.left = left;
    }

    public Type getRight() {
        return right;
    }

    public void setRight(Type right) {
        this.right = right;
    }
}
