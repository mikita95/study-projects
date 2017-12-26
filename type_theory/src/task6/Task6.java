package task6;

import semantics.*;
import utils.FastScanner;

import java.io.*;
import java.util.Map;

public class Task6 {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "task6";

    public static void main(String[] args) {
        new Task6().run();
    }

    void solve() throws IOException {
        TypedExpression parsed = TypedExpression.parseExpression(in.nextLine()).rename();
        TypeResult typeResult = parsed.getType();
        if (typeResult.getTypedEquationsSystem().solve()) {
            Type type = typeResult.getType();
            for (int i = 0; i < typeResult.getTypedEquationsSystem().getLeft().size(); i++)
                type = type.substitute((TypesVariable) typeResult.getTypedEquationsSystem().getLeft().get(i), typeResult.getTypedEquationsSystem().getRight().get(i));
            out.println(parsed);
            out.println(type);
            for (Map.Entry<TypedVariable, TypesVariable> typeVariableEntry : typeResult.getContext().entrySet()) {
                Type t = typeVariableEntry.getValue();
                for (int i = 0; i < typeResult.getTypedEquationsSystem().getLeft().size(); i++)
                    if (typeResult.getTypedEquationsSystem().getLeft().get(i).equals(t))
                        t = typeResult.getTypedEquationsSystem().getRight().get(i);
                out.println(typeVariableEntry.getKey() + ":" + t);
            }
        } else out.println("Лямбда-выражение не имеет типа.");
    }

    public void run() {
        try {
            InputStream is = new FileInputStream(FILE_NAME + ".in");
            in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".out"));
            solve();
            out.close();
        } catch (IOException ignored) {
        }
    }


}