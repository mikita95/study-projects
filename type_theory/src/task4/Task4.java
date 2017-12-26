package task4;

import semantics.Expression;
import utils.FastScanner;

import java.io.*;

public class Task4 {
    private FastScanner in;
    private static PrintWriter out;
    private final static String FILE_NAME = "task4";

    public static void main(String[] args) {
        new Task4().run();
    }

    void solve() throws IOException {
        out.println(Expression.parseExpression(in.nextLine()).rename().normalize().toString());
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