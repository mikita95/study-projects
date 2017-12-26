import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "zero";

    public static void main(String[] args) {
        new A().run();
    }

    private static class Turing {
        String start, accept, reject, blank;
        ArrayList<String> rools;
        public Turing(String start, String accept, String reject, String blank) {
            this.start = start;
            this.accept = accept;
            this.reject = reject;
            this.blank = blank;
            rools = new ArrayList<String>();
        }

        private enum Arrow {
            Right, Left, Non;
        }

        public void addRool(String from, String symbol, String to, String write, Arrow arrow) {
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
            rools.add(s);
        }

        @Override
        public String toString() {
            String s = "";
            s += "start: " + start + "\n";
            s += "accept: " + accept + "\n";
            s += "reject: " + reject + "\n";
            s += "blank: " + blank + "\n";
            for (String r: rools)
                s += r + "\n";
            return s;
        }
    }
    
    void solve() throws IOException {
        Turing t = new Turing("s", "ac", "rj", "_");
        t.addRool(t.start, t.blank, t.accept, t.blank, Turing.Arrow.Non);
        t.addRool(t.start, "0", "s0", t.blank, Turing.Arrow.Right);
        t.addRool("s0", "0", t.start, t.blank, Turing.Arrow.Right);
        t.addRool("s0", t.blank, t.reject, t.blank, Turing.Arrow.Right);
        out.print(t);
    }

    public void run() {
        try {
        //    InputStream is = new FileInputStream(FILE_NAME + ".in");
          //  in = new FastScanner(is);
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