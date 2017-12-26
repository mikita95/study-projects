import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class F {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "reverse";

    public static void main(String[] args) {
        new F().run();
    }

    private static class Turing {
        private class Rool {
            String from, symbol, to, write;
            Arrow arrow;

            public Rool(String from, String symbol, String to, String write, Arrow arrow) {
                this.from = from;
                this.symbol = symbol;
                this.to = to;
                this.write = write;
                this.arrow = arrow;
            }

            @Override
            public String toString() {
                String s = from + " " + symbol + " -> " + to + " " + write;
                switch (arrow) {
                    case Right: {
                        s += " >";
                        break;
                    }
                    case Left: {
                        s += " <";
                        break;
                    }
                    case Non: {
                        s += " ^";
                    }
                }
                return s;
            }
        }

        String start, accept, reject, blank;
        ArrayList<Rool> rools;

        public Turing(String start, String accept, String reject, String blank) {
            this.start = start;
            this.accept = accept;
            this.reject = reject;
            this.blank = blank;
            rools = new ArrayList<Rool>();
        }

        private enum Arrow {
            Right, Left, Non;
        }

        public void addRool(String from, String symbol, String to, String write, Arrow arrow) {
            rools.add(new Rool(from, symbol, to, write, arrow));
        }

        String run(String line, int s) {
            StringBuilder word = new StringBuilder(line);
            String current = start;
            while (!current.equals(accept) && !current.equals(reject)) {
                String ch = "" + word.charAt(s);
                for (Rool rool : rools) {
                    if (rool.from.equals(current) && rool.symbol.equals(ch)) {
                        word.setCharAt(s, rool.write.charAt(0));
                        current = rool.to;
                        switch (rool.arrow) {
                            case Right: {
                                s += 1;
                                break;
                            }
                            case Left: {
                                s -= 1;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            return word.toString() + s;
        }

        @Override
        public String toString() {
            String s = "";
            s += "start: " + start + "\n";
            s += "accept: " + accept + "\n";
            s += "reject: " + reject + "\n";
            s += "blank: " + blank + "\n";
            for (Rool r : rools)
                s += r + "\n";
            return s;
        }
    }

    void solve() throws IOException {
        Turing t = new Turing("s", "ac", "rj", "_");

        t.addRool(t.start, "0", "d", "0", Turing.Arrow.Right);
        t.addRool(t.start, "1", "d", "1", Turing.Arrow.Right);
        t.addRool(t.start, t.blank, "r", t.blank, Turing.Arrow.Left);

        t.addRool("d", "0", "m", "0", Turing.Arrow.Right);
        t.addRool("d", "1", "m", "1", Turing.Arrow.Right);
        t.addRool("d", t.blank, t.accept, t.blank, Turing.Arrow.Left);

        t.addRool("m", "0", "m", "0", Turing.Arrow.Right);
        t.addRool("m", "1", "m", "1", Turing.Arrow.Right);
        t.addRool("m", t.blank, "r", t.blank, Turing.Arrow.Left);

        t.addRool("r", "0", "b0", t.blank, Turing.Arrow.Right);
        t.addRool("r", "1", "b1", t.blank, Turing.Arrow.Right);
        t.addRool("r", t.blank, t.accept, t.blank, Turing.Arrow.Non);

        t.addRool("b0", t.blank, "t", "0", Turing.Arrow.Left);
        t.addRool("b1", t.blank, "t", "1", Turing.Arrow.Left);

        t.addRool("t", "0", "c", "0", Turing.Arrow.Left);
        t.addRool("t", "1", "c", "1", Turing.Arrow.Left);
        t.addRool("t", t.blank, "t", t.blank, Turing.Arrow.Left);

        t.addRool("c", "0", "f", "0", Turing.Arrow.Right);
        t.addRool("c", "1", "f", "1", Turing.Arrow.Right);
        t.addRool("c", t.blank, "x", t.blank, Turing.Arrow.Right);

        t.addRool("x", "0", "e0", t.blank, Turing.Arrow.Right);
        t.addRool("x", "1", "e1", t.blank, Turing.Arrow.Right);
        t.addRool("x", t.blank, "x", t.blank, Turing.Arrow.Right);

        t.addRool("e0", "0", "k0", "0", Turing.Arrow.Right);
        t.addRool("e0", "1", "k0", "1", Turing.Arrow.Right);
        t.addRool("e0", t.blank, "e0", t.blank, Turing.Arrow.Right);

        t.addRool("e1", "0", "k1", "0", Turing.Arrow.Right);
        t.addRool("e1", "1", "k1", "1", Turing.Arrow.Right);
        t.addRool("e1", t.blank, "e1", t.blank, Turing.Arrow.Right);

        t.addRool("k0", "0", "k0", "0", Turing.Arrow.Right);
        t.addRool("k0", "1", "k0", "1", Turing.Arrow.Right);
        t.addRool("k0", t.blank, "n", "0", Turing.Arrow.Left);

        t.addRool("k1", "0", "k1", "0", Turing.Arrow.Right);
        t.addRool("k1", "1", "k1", "1", Turing.Arrow.Right);
        t.addRool("k1", t.blank, "n", "1", Turing.Arrow.Left);

        t.addRool("n", "0", "n", "0", Turing.Arrow.Left);
        t.addRool("n", "1", "n", "1", Turing.Arrow.Left);
        t.addRool("n", t.blank, t.accept, t.blank, Turing.Arrow.Right);

        t.addRool("f", "0", "v0", t.blank, Turing.Arrow.Right);
        t.addRool("f", "1", "v1", t.blank, Turing.Arrow.Right);

        t.addRool("v0", "0", "p0", "0", Turing.Arrow.Right);
        t.addRool("v0", "1", "p0", "1", Turing.Arrow.Right);
        t.addRool("v0", t.blank, "v0", t.blank, Turing.Arrow.Right);

        t.addRool("p0", "0", "p0", "0", Turing.Arrow.Right);
        t.addRool("p0", "1", "p0", "1", Turing.Arrow.Right);
        t.addRool("p0", t.blank, "l", "0", Turing.Arrow.Left);

        t.addRool("v1", "0", "p1", "0", Turing.Arrow.Right);
        t.addRool("v1", "1", "p1", "1", Turing.Arrow.Right);
        t.addRool("v1", t.blank, "v1", t.blank, Turing.Arrow.Right);

        t.addRool("p1", "0", "p1", "0", Turing.Arrow.Right);
        t.addRool("p1", "1", "p1", "1", Turing.Arrow.Right);
        t.addRool("p1", t.blank, "l", "1", Turing.Arrow.Left);

        t.addRool("l", "0", "l", "0", Turing.Arrow.Left);
        t.addRool("l", "1", "l", "1", Turing.Arrow.Left);
        t.addRool("l", t.blank, "t", t.blank, Turing.Arrow.Left);


        out.print(t);
       // out.print(t.run("__010000111__________________________________________________________", 2));

    }

    public void run() {
        try {
            //InputStream is = new FileInputStream(FILE_NAME + ".in");
            //in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".out"));
            solve();
            out.close();
        } catch (IOException ignored) {
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

        public String next() {
            return tokenizer.nextToken();
        }

    }
}