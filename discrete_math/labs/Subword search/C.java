import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class C {
    private final static String FILE_NAME = "prefix";

    public static void main(String[] args) {
        new C().run();
    }

    ArrayList<Integer> prefixFunction(String s) {
        ArrayList<Integer> p = new ArrayList<Integer>(s.length());
        for (int i = 0; i < s.length(); i++)
            p.add(0);
        p.set(0, 0);
        for (int i = 1; i < s.length(); i++) {
            int k = p.get(i - 1);
            while (s.charAt(i) != s.charAt(k) && k > 0)
                k = p.get(k - 1);
            if (s.charAt(k) == s.charAt(i))
                k++;
            p.set(i, k);
        }
        return p;
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(FILE_NAME + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(FILE_NAME + ".out"));
        String s = in.next();
        for (int ans: prefixFunction(s))
            out.print(ans + " ");
        out.close();
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

        public String next() {
            return tokenizer.nextToken();
        }

    }


}