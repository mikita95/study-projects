package semantics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypedEquationsSystem {
    private List<Type> left, right;

    public TypedEquationsSystem() {
        left = new ArrayList<>();
        right = new ArrayList<>();
    }

    private boolean isSolved() {
        Set<TypesVariable> typesVariables = new HashSet<>();
        for (Type t : left)
            if (t instanceof TypesVariable) {
                if (typesVariables.contains(t)) return false;
                typesVariables.add((TypesVariable) t);
            } else return false;
        for (TypesVariable variable : typesVariables) {
            for (Type t : right) if (t.getVariables().contains(variable)) return false;
        }
        return true;
    }

    private boolean isBad() {
        for (int i = 0; i < left.size(); i++) {
            if (!left.get(i).equals(right.get(i)) && left.get(i) instanceof TypesVariable && right.get(i).getVariables().contains(left.get(i)))
                return true;
        }
        return false;
    }

    public TypedEquationsSystem(TypedEquationsSystem es1, TypedEquationsSystem es2, Type leftType, Type rightType) {
        left = es1.getLeft();
        right = es1.getRight();
        left.addAll(es2.getLeft());
        right.addAll(es2.getRight());
        left.add(leftType);
        right.add(rightType);
    }

    private void deleteLeft() {
        for (int i = 0; i < left.size(); i++) {
            if (left.get(i) instanceof TypesVariable) {
                for (int j = 0; j < left.size(); j++) {
                    if (i != j) {
                        if (right.get(i) instanceof TypesImplication && left.get(i).equals(left.get(j)))
                            left.set(j, right.get(i));
                        left.set(j, left.get(j).substitute((TypesVariable) left.get(i), right.get(i)));
                        right.set(j, right.get(j).substitute((TypesVariable) left.get(i), right.get(i)));
                    }
                }
            }
        }
    }

    void changeEquations(int i) {
        left.add(((TypesImplication) left.get(i)).getLeft());
        right.add(((TypesImplication) right.get(i)).getLeft());
        left.add(((TypesImplication) left.get(i)).getRight());
        right.add(((TypesImplication) right.get(i)).getRight());
        left.remove(i);
        right.remove(i);
    }

    public boolean solve() {
        while (!isSolved()) {
            if (isBad()) return false;
            for (int i = 0; i < left.size(); i++) {
                if (left.get(i).equals(right.get(i))) {
                    left.remove(i);
                    right.remove(i--);
                } else if (left.get(i) instanceof TypesImplication && right.get(i) instanceof TypesVariable) {
                    Type type = left.get(i);
                    left.set(i, right.get(i));
                    right.set(i, type);
                } else if (left.get(i) instanceof TypesImplication && right.get(i) instanceof TypesImplication) {
                    changeEquations(i--);
                }
            }
            deleteLeft();
        }
        return true;
    }


    public List<Type> getLeft() {
        return left;
    }

    public List<Type> getRight() {
        return right;
    }

    public void setLeft(List<Type> left) {
        this.left = left;
    }

    public void setRight(List<Type> right) {
        this.right = right;
    }
}
