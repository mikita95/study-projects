package semantics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypedVariable extends TypedExpression {
    private static int number;
    private static Set<TypedVariable> set;
    protected String name;


    public static void setSet(Set<TypedVariable> set) {
        TypedVariable.set = set;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected TypedVariable rename(Map<TypedVariable, TypedVariable> map) {
        return map.containsKey(this) ? map.get(this) : this;
    }

    @Override
    protected Set<TypedVariable> getVariables() {
        Set<TypedVariable> typedVariables = new HashSet<>();
        typedVariables.add(this);
        return typedVariables;
    }

    @Override
    public TypeResult getType(Map<TypedVariable, TypesVariable> context) {
        if (context.containsKey(this))
            return new TypeResult(new TypedEquationsSystem(), context.get(this), context);
        TypesVariable typesVariable = TypesVariable.nextTypeVariable();
        context.put(this, typesVariable);
        return new TypeResult(new TypedEquationsSystem(), typesVariable, context);
    }

    public static void initialize(Set<TypedVariable> typedVariables) {
        set = typedVariables;
        number = 0;
    }

    public static TypedVariable nextVariable() {
        TypedVariable typedVariable;
        do typedVariable = new TypedVariable("v" + number++); while (set.contains(typedVariable));
        return typedVariable;
    }

    public TypedVariable(String name) {
        this.name = name;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        TypedVariable.number = number;
    }

    public static Set<TypedVariable> getSet() {
        return set;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypedVariable && name.equals(((TypedVariable) o).getName());
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
