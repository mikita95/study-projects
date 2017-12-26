package task1;

import semantics.Expression;
import utils.FastScanner;

import java.io.*;

public class Task1 {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "task1";

    public static void main(String[] args) {
        new Task1().run();
    }

    void solve() throws IOException {
        out.println(Expression.parseExpression(in.nextLine()));
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