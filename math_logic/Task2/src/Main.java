import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {


    private final static String fileNameInput = "input";
    private final static String fileNameOutput = "output";
    FastScanner in;
    PrintWriter out;

    void solve() throws IOException {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        String s = in.readLine();
        Parser parser;
        String[] argument = s.split(",");
        ArrayList<Expression> added = new ArrayList<Expression>();
        for (int i = 0; i < argument.length - 1; i++) {
            parser = new Parser(argument[i]);
            added.add(parser.parse());
        }
        String[] argTask = argument[argument.length - 1].split("\\|-");
        Expression alpha = (new Parser(argTask[0])).parse();
        ArrayList<Expression> answer = new ArrayList<Expression>();
        if (in.hasNext())
            s = in.readLine();

        while (in.hasNext()) {
            parser = new Parser(s);
            expressions.add(parser.parse());
            s = in.readLine();
        }
        boolean isgood = true;
        int i = 0;
        for (Expression expr : expressions) {
            i++;
            isgood = false;
            if (Axioms.checker(expr) != -1) {
                answer.add(expr);
                answer.add(new Implication(expr, new Implication(alpha, expr)));
                answer.add(new Implication(alpha, expr));
                isgood = true;
                continue;
            }
            for (Expression g : added) {
                if (g.equals(expr)) {
                    answer.add(expr);
                    answer.add(new Implication(expr, new Implication(alpha, expr)));
                    answer.add(new Implication(alpha, expr));
                    isgood = true;

                }
                if (isgood) break;
            }
            if (alpha.equals(expr)) {
                answer.add(new Implication(alpha, new Implication(alpha, alpha)));
                answer.add(new Implication(new Implication(alpha, new Implication(alpha, alpha)),
                        new Implication(new Implication(alpha, new Implication(
                                new Implication(alpha, alpha), alpha)), new Implication(alpha, alpha))
                ));
                answer.add(new Implication(new Implication(alpha, new Implication(new Implication(alpha, alpha), alpha)),
                        new Implication(alpha, alpha)));
                answer.add(new Implication(alpha, new Implication(new Implication(alpha, alpha), alpha)));
                answer.add(new Implication(alpha, alpha));
                isgood = true;
                continue;
            }

            for (int j = 0; j < i - 1; j++) {
                Expression mp = expressions.get(j);
                if (mp instanceof Implication) {
                    Implication impl = (Implication) mp;
                    if (impl.getRight().equals(expr)) {
                        for (int k = 0; k < i - 1; k++) {
                            Expression mp2 = expressions.get(k);
                            if (mp2.equals(impl.getLeft())) {
                                answer.add(new Implication(new Implication(alpha, mp2), new Implication(new
                                        Implication(alpha, new Implication(mp2, expr)), new Implication(alpha, expr))));
                                answer.add(new Implication(new Implication(alpha, new Implication(mp2, expr)),
                                        new Implication(alpha, expr)));
                                answer.add(new Implication(alpha, expr));
                                isgood = true;
                                break;
                            }
                        }
                    }
                    if (isgood)
                        break;
                }
            }
            if (!isgood) {
                break;
            }
        }
        if (isgood) {
            for (Expression expr : answer) {
                out.println(expr.toString());
            }
        } else {
            for (Expression expr : answer) {
                out.println(expr.toString());
            }
            out.println("Error");
        }
    }

    public void run() {
        try {
            InputStream is = new FileInputStream(fileNameInput + ".txt");
            in = new FastScanner(is);
            out = new PrintWriter(new File(fileNameOutput + ".txt"));
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class FastScanner {

        private StringTokenizer tokenizer;

        public FastScanner(InputStream is) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024);
                byte[] buf = new byte[1024];
                while (true) {
                    int read = is.read(buf);
                    if (read == -1)
                        break;
                    bos.write(buf, 0, read);
                }
                tokenizer = new StringTokenizer(new String(bos.toByteArray()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean hasNext() {
            return tokenizer.hasMoreTokens();
        }

        public int nextInt() {
            return Integer.parseInt(tokenizer.nextToken());
        }

        public long nextLong() {
            return Long.parseLong(tokenizer.nextToken());
        }

        public double nextDouble() {
            return Double.parseDouble(tokenizer.nextToken());
        }

        public String readLine() {
            return tokenizer.nextToken();
        }

    }

    public static void main(String[] arg) {
        new Main().run();
    }


}
