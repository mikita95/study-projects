package task3;

import semantics.Expression;
import utils.FastScanner;
import semantics.Variable;

import java.io.*;

public class Task3 {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "task3";

    public static void main(String[] args) {
        new Task3().run();
    }

    void solve() throws IOException {
        String line = in.nextLine();

        int left = 0;
        while (line.charAt(left) != '[') left++;
        int right = left;
        while (line.charAt(right) != ':') right++;
        Expression expression1 = Expression.parseExpression(line.substring(0, left));
        Variable variable1 = new Variable(line.substring(left + 1, right));

        Expression expression2 = Expression.parseExpression(line.substring(right + 2, line.length() - 1));
        Variable variable2 = expression1.getNonFreeVariable(variable1, expression2);

        if (variable2 == null) out.println(expression1.substitute(variable1, expression2));
        else out.println("Нет свободы для подстановки для переменной " + variable2);

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