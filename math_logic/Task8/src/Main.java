import java.io.*;
import java.util.StringTokenizer;

//I used this article: http://www.ccs.neu.edu/home/pete/pub/cade-algorithms-ordinal-arithmetic.pdf

public class Main {
    private final static String fileName = "input";

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        String s = in.readLine();
        while (in.hasNext())
            s += in.readLine();
        String first = s.substring(0, s.indexOf("="));
        String second = s.substring(s.indexOf("=") + 1, s.length());
        try {
            Ordinal arg1 = OrdinalParser.parse(first);
            Ordinal arg2 = OrdinalParser.parse(second);
            //out.println(arg1.toString());
            //out.println(arg2.toString());
            String result1 = arg1.toCNF().toString();
            String result2 = arg2.toCNF().toString();
            //out.println(result1);
            //out.println(result2);
            if (result1.equals(result2))
                out.println("Равны");
            else out.println("Не равны");
        } catch (Exception e) {
            out.println("Check your input file");
        }

        out.close();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        try {
            solve();
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

        public String readLine() {
            return tokenizer.nextToken();
        }

    }
}