package utils;

import semantics.Expression;

import java.util.HashMap;
import java.util.Map;

public class Memoization {
    private static Map<Expression, Expression> memory1 = new HashMap<>();
    private static Map<Expression, Expression> memory2 = new HashMap<>();

    public static void write1(Expression from, Expression to) {
        if (!memory1.containsKey(from)) {
            memory1.put(from, to);
        }
    }

    public static void write2(Expression from, Expression to) {
        if (!memory2.containsKey(from)) {
            memory2.put(from, to);
        }
    }

    public static Expression get1(Expression e) { return  memory1.get(e); }
    public static Expression get2(Expression e) { return  memory2.get(e); }

    public static boolean isContain1(Expression e) { return memory1.containsKey(e); }
    public static boolean isContain2(Expression e) { return memory2.containsKey(e); }

    public static Map<Expression, Expression> getMemory1() {
        return memory1;
    }

    public static void setMemory1(Map<Expression, Expression> memory1) {
        Memoization.memory1 = memory1;
    }

    public static Map<Expression, Expression> getMemory2() {
        return memory2;
    }

    public static void setMemory2(Map<Expression, Expression> memory2) {
        Memoization.memory2 = memory2;
    }
}
