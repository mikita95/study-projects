package task5;

import semantics.Equation;
import semantics.EquationsSystem;
import semantics.Expression;
import utils.FastScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Task5 {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "task5";


    public static void main(String[] args) {
        new Task5().run();
    }

    void solve() throws IOException {
        List<Equation> equations = new ArrayList<>();
        while (in.hasNext()) {
            String string = in.nextLine().replaceAll(" ", "");
            int i = string.indexOf('=');
            equations.add(new Equation(Expression.parseExpressionInFunction(string.substring(0, i)), Expression.parseExpressionInFunction(string.substring(i + 1, string.length()))));
        }
        EquationsSystem equationsSystem = new EquationsSystem(equations);
        if (equationsSystem.solve())
            for (Equation equation : equationsSystem.getEquations()) out.println(equation.left + "=" + equation.right);
        else out.println("Система не совместна");
        out.close();
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