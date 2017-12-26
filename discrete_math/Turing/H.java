import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class H {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "convertto2";

    public static void main(String[] args) {
        new H().run();
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

        t.addRool(t.start, "0", "f", "0", Turing.Arrow.Left);
        t.addRool(t.start, "1", "f", "1", Turing.Arrow.Left);
        t.addRool(t.start, "2", "f", "2", Turing.Arrow.Left);
        t.addRool("f", t.blank, "t", "b", Turing.Arrow.Right);

        t.addRool("g0", "0", "g0", "0", Turing.Arrow.Left);
        t.addRool("g0", "1", "g0", "1", Turing.Arrow.Left);
        t.addRool("g0", "2", "g0", "2", Turing.Arrow.Left);
        t.addRool("g0", "b", "g0", "b", Turing.Arrow.Left);
        t.addRool("g0", t.blank, "r", "0", Turing.Arrow.Right);

        t.addRool("t", "0", "v", "0", Turing.Arrow.Right);
        t.addRool("t", "1", "w", "1", Turing.Arrow.Right);
        t.addRool("t", "2", "v", "2", Turing.Arrow.Right);

        t.addRool("r", "0", "r", "0", Turing.Arrow.Right);
        t.addRool("r", "1", "r", "1", Turing.Arrow.Right);
        t.addRool("r", "2", "r", "2", Turing.Arrow.Right);
        t.addRool("r", "b", "q", "b", Turing.Arrow.Right);

        t.addRool("q", "0", "q", "0", Turing.Arrow.Right);
        t.addRool("q", "1", "d", "0", Turing.Arrow.Right);
        t.addRool("q", "2", "q", "1", Turing.Arrow.Right);
        t.addRool("q", t.blank, "c", t.blank, Turing.Arrow.Left);

        t.addRool("x", "b", "x", t.blank, Turing.Arrow.Right);
        t.addRool("x", "0", "x", t.blank, Turing.Arrow.Right);
        t.addRool("x", t.blank, "n", t.blank, Turing.Arrow.Non);

        t.addRool("n", "0", "m", "0", Turing.Arrow.Left);
        t.addRool("n", "1", "m", "1", Turing.Arrow.Left);
        t.addRool("n", "2", "m", "2", Turing.Arrow.Left);
        t.addRool("n", t.blank, "n", t.blank, Turing.Arrow.Left);

        t.addRool("d", "0", "d", "1", Turing.Arrow.Right);
        t.addRool("d", "1", "q", "2", Turing.Arrow.Right);
        t.addRool("d", "2", "d", "2", Turing.Arrow.Right);
        t.addRool("d", t.blank, "c", t.blank, Turing.Arrow.Left);

        t.addRool("v", "0", "v", "0", Turing.Arrow.Right);
        t.addRool("v", "1", "w", "1", Turing.Arrow.Right);
        t.addRool("v", "2", "v", "2", Turing.Arrow.Right);
        t.addRool("v", t.blank, "g0", t.blank, Turing.Arrow.Left);

        t.addRool("w", "0", "w", "0", Turing.Arrow.Right);
        t.addRool("w", "1", "v", "1", Turing.Arrow.Right);
        t.addRool("w", "2", "w", "2", Turing.Arrow.Right);
        t.addRool("w", t.blank, "g1", t.blank, Turing.Arrow.Left);

        t.addRool("g1", "0", "g1", "0", Turing.Arrow.Left);
        t.addRool("g1", "1", "g1", "1", Turing.Arrow.Left);
        t.addRool("g1", "2", "g1", "2", Turing.Arrow.Left);
        t.addRool("g1", "b", "g1", "b", Turing.Arrow.Left);
        t.addRool("g1", t.blank, "r", "1", Turing.Arrow.Right);

        t.addRool("c", "0", "c", "0", Turing.Arrow.Left);
        t.addRool("c", "1", "l", "1", Turing.Arrow.Left);
        t.addRool("c", "2", "l", "2", Turing.Arrow.Left);
        t.addRool("c", "b", "x", "b", Turing.Arrow.Non);

        t.addRool("l", "0", "l", "0", Turing.Arrow.Left);
        t.addRool("l", "1", "l", "1", Turing.Arrow.Left);
        t.addRool("l", "2", "l", "2", Turing.Arrow.Left);
        t.addRool("l", "b", "t", "b", Turing.Arrow.Right);
        
        t.addRool("m", "0", "m", "0", Turing.Arrow.Left);
        t.addRool("m", "1", "m", "1", Turing.Arrow.Left);
        t.addRool("m", "2", "m", "2", Turing.Arrow.Left);
        t.addRool("m", t.blank, t.accept, t.blank, Turing.Arrow.Right);
        out.print(t);
        //out.print(t.run("__________________________12002120021____________________________", 26));
    }

    public void run() {
        try {
            //  InputStream is = new FileInputStream(FILE_NAME + ".in");
            // in = new FastScanner(is);
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