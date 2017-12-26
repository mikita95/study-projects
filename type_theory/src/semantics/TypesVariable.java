package semantics;

import java.util.HashSet;
import java.util.Set;

public class TypesVariable extends Type {
    private static int number;
    protected String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Type substitute(TypesVariable typesVariable, Type type) {
        return equals(typesVariable) ? type : this;
    }

    @Override
    public Set<TypesVariable> getVariables() {
        Set<TypesVariable> variables = new HashSet<>();
        variables.add(this);
        return variables;
    }

    public TypesVariable(String name) {
        this.name = name;
    }

    public static TypesVariable nextTypeVariable() {
        return new TypesVariable("t" + number++);
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        TypesVariable.number = number;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypesVariable && name.equals(((TypesVariable) o).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
