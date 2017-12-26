package semantics;

import java.util.Set;

public abstract class Type {
    public abstract Set<TypesVariable> getVariables();
    public abstract Type substitute(TypesVariable typesVariable, Type type);
}
