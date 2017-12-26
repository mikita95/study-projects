package semantics;

import java.util.Map;

public class TypeResult {
    public TypedEquationsSystem typedEquationsSystem;
    public Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public TypeResult(TypedEquationsSystem typedEquationsSystem, Type type, Map<TypedVariable, TypesVariable> context) {
        this.type = type;
        this.typedEquationsSystem = typedEquationsSystem;
        this.context = context;
    }

    public Map<TypedVariable, TypesVariable> context;

    public TypedEquationsSystem getTypedEquationsSystem() {
        return typedEquationsSystem;
    }

    public void setTypedEquationsSystem(TypedEquationsSystem typedEquationsSystem) {
        this.typedEquationsSystem = typedEquationsSystem;
    }

    public Type getType() {
        return type;
    }


    public Map<TypedVariable, TypesVariable> getContext() {
        return context;
    }

    public void setContext(Map<TypedVariable, TypesVariable> context) {
        this.context = context;
    }
}
