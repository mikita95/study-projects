package task2;

import semantics.Expression;
import utils.FastScanner;
import semantics.Variable;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Task2 {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "task2";

    public static void main(String[] args) {
        new Task2().run();
    }


    void solve() throws IOException {
        Set<Variable> variables = Expression.parseExpression(in.nextLine()).getFreeVariables();
        List<Variable> variableList = new ArrayList<>(variables);
        variableList.sort(new Comparator<Variable>() {
            @Override
            public int compare(Variable o1, Variable o2) {
                String a = o1.getName(), b = o2.getName();
                int minLength = Math.min(a.length(), b.length());
                for (int i = 0; i < minLength; i++) {
                    if (a.charAt(i) < b.charAt(i)) return -1;
                    else if (b.charAt(i) < a.charAt(i)) return 1;
                }
                if (a.length() < b.length()) return -1;
                else if (b.length() < a.length()) return 1;
                return 0;
            }
        });
        variableList.forEach(out::println);
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