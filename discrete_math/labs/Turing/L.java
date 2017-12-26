import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class L {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "factorial";

    public static void main(String[] args) {
        new L().run();
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

        Rool getRool(String current, String ch) {
            int k = 0;
            Rool r = null;
            for (Rool rool : rools) {
                if (rool.from.equals(current) && rool.symbol.equals(ch)) {
                    k++;
                    r = rool;

                }
            }
            if (k > 1) System.err.print(k);

            return r;

        }

        String run(String line, int s) {
            char[] tmp = line.toCharArray();
            String[] word = new String[tmp.length];
            for (int i = 0; i < tmp.length; i++)
                word[i] = "" + tmp[i];
            String current = start;
            while (!current.equals(accept) && !current.equals(reject)) {
                String ch = "" + word[s];
                Rool rool = getRool(current, ch);

                word[s] = rool.write;

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


            }
            return Arrays.toString(word) + s;
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

    String factorial(int k) {
        BigInteger res = new BigInteger("1");
        for (int i = 1; i <= k; i++)
            res = res.multiply(new BigInteger(String.valueOf(i)));
        return res.toString(2);
    }

    Turing t = new Turing("s", "ac", "rj", "_");


    void generate(String current) {

        if (Integer.parseInt(current, 2) > 30) {
            return;
        }
        int k = Integer.parseInt(current, 2);
        String write = factorial(k);
        t.addRool("f" + current, "_", "w" + k, write, Turing.Arrow.Non);

        String fact = write;
        t.addRool("push" + k + "0", "1", "push" + k + "0", "1", Turing.Arrow.Right);
        t.addRool("push" + k + "0", "0", "push" + k + "0", "0", Turing.Arrow.Right);
        t.addRool("push" + k + "0", "_", "r" + k, "" + "0", Turing.Arrow.Left);
        t.addRool("push" + k + "1", "1", "push" + k + "1", "1", Turing.Arrow.Right);
        t.addRool("push" + k + "1", "0", "push" + k + "1", "0", Turing.Arrow.Right);
        t.addRool("push" + k + "1", "_", "r" + k, "" + "1", Turing.Arrow.Left);
        t.addRool("r" + k, "0", "r" + k,  "0", Turing.Arrow.Left);
        t.addRool("r" + k, "1", "r" + k, "1", Turing.Arrow.Left);
        t.addRool("r" + k, "_", "w" + k, "_", Turing.Arrow.Right);
        t.addRool("r" + k, "&", "ac", "_", Turing.Arrow.Right);
        for (int i = 0; i < write.length() - 1; i++) {
            String to = fact.substring(1, fact.length());
            if (to.length() == 0) to = "&";
            if (to.length() == 1) {
                to += "&";
                t.addRool("w" + k, to, "push" + k + to.charAt(0), "&", Turing.Arrow.Right);
            }
            t.addRool("w" + k, fact, "push" + k + fact.charAt(0), to, Turing.Arrow.Right);
            if (fact.substring(1, fact.length()).length() > 1)
                t.addRool("r" + k, to, "w" + k, to, Turing.Arrow.Non);
            if (fact.substring(1, fact.length()).length() == 1)
                t.addRool("r" + k, to, "w" + k, to, Turing.Arrow.Non);

            fact = fact.substring(1, fact.length());
        }

        if (Integer.valueOf(current + "1", 2) <= Integer.valueOf(Integer.toBinaryString(30), 2)) {
            t.addRool("f" + current, "1", "f" + current + "1", "_", Turing.Arrow.Right);
        }

        generate(current + "1");

        if (Integer.valueOf(current + "0", 2) <= Integer.valueOf(Integer.toBinaryString(30), 2)) {
            t.addRool("f" + current, "0", "f" + current + "0", "_", Turing.Arrow.Right);
        }
        generate(current + "0");
    }

    void solve() throws IOException {
        generate("1");
        t.addRool("s", "1", "f1", "_", Turing.Arrow.Right);
        t.addRool("w1", "1", "ac" ,"1", Turing.Arrow.Non);
        out.print(t);
        // out.print(t.run("____________________1_____________________________", 20));
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