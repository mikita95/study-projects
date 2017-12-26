package semantics;

import utils.Parser;
import utils.TypedLambdaSemantics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TypedExpression {

    protected abstract Set<TypedVariable> getVariables();

    public TypedExpression rename() {
        TypedVariable.initialize(getVariables());
        return rename(new HashMap<>());
    }

    public static TypedExpression parseExpression(String s) {
        return Parser.parseTypedAbstraction(TypedLambdaSemantics.getSemantics(s), 0).getResult();
    }

    public TypeResult getType() {
        return getType(new HashMap<>());
    }

    public abstract TypeResult getType(Map<TypedVariable, TypesVariable> context);

    protected abstract TypedExpression rename(Map<TypedVariable, TypedVariable> map);


}
