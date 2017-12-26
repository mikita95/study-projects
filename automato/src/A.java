import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "mutation";

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(11);
        for (int i = 0; i < m; i++) {
            String s = in.next();
            String t = in.next();
            double result = 1;
            for (int k = 0; k < n; k++) {
                if (s.charAt(k) != t.charAt(k))
                    result *= 1 / (double) n;
                else result *= (1 - 1 / (double) n);
            }
            out.println(decimalFormat.format(result));

        }

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