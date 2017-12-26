import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
    private final static String FILE_NAME = "z";

    public static void main(String[] args) {
        new D().run();
    }

    ArrayList<Integer> zFunction(String s) {
        ArrayList<Integer> z = new ArrayList<Integer>(s.length());
        for (int i = 0; i < s.length(); i++)
            z.add(0);
        z.set(0, s.length());
        int l = 0, r = 0;
        for (int i = 1; i < z.size(); i++) {
            z.set(i, Math.max(0, Math.min(z.get(i - l), r - i + 1)));
            while (i + z.get(i) < s.length() && s.charAt(z.get(i)) == s.charAt(i + z.get(i)))
                z.set(i, z.get(i) + 1);
            if (i + z.get(i) - 1 > r) {
                l = i;
                r = i + z.get(i) - 1;
            }
        }
        return z;
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(FILE_NAME + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(FILE_NAME + ".out"));
        ArrayList<Integer> ans = zFunction(in.next());
        for (int i = 1; i < ans.size(); i++)
            out.print(ans.get(i) + " ");
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