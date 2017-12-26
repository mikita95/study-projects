import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class J {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "postfixlogic";

    public static void main(String[] args) {
        new J().run();
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
        StringBuilder s = new StringBuilder("2\n");
        out.print(s
                .append("S 0 _ -> S _ > 0 >\n")
                .append("S 1 _ -> S _ > 1 >\n")

                .append("S o _ -> d o ^ _ <\n")
                .append("S o _ -> d o ^ _ <\n")
                .append("S a _ -> c a ^ _ <\n")

                .append("S _ _ -> x _ ^ _ <\n")

                .append("d1 o 0 -> S _ > 1 >\n")
                .append("d1 o 1 -> S _ > 1 >\n")

                .append("c a 0 -> c0 a ^ _ <\n")
                .append("c a 1 -> k1 a ^ _ <\n")
                .append("c0 a 0 -> S _ > 0 >\n")
                .append("c0 a 1 -> S _ > 0 >\n")

                .append("k1 a 0 -> S _ > 0 >\n")
                .append("d o 0 -> d0 o ^ _ <\n")
                .append("d o 1 -> d1 o ^ _ <\n")
                .append("d0 o 0 -> S _ > 0 >\n")
                .append("d0 o 1 -> S _ > 1 >\n")
                .append("k1 a 1 -> S _ > 1 >\n")

                .append("x _ 0 -> AC 0 ^ _ ^\n")
                .append("x _ 1 -> AC 1 ^ _ ^\n")
        );
    }

    public void run() {
        try {
           // InputStream is = new FileInputStream(FILE_NAME + ".in");
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