import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class I {
   // private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "";

    public static void main(String[] args) {
        new I().run();
    }

    void solve() throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        //System.out.println(n);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++)
            s.append('0');
        System.out.println(s);
        int k = in.nextInt();
        for (int i = 0; i < n; i++) {
            s.setCharAt(i, '1');
            System.out.println(s);
            int k1 = in.nextInt();
            if (k1 == n)
                break;
            if (k1 > k) {
                k = k1;
            } else s.setCharAt(i, '0');
        }
        System.out.flush();
    }

    public void run() {
        try {
           // InputStream is = new FileInputStream(FILE_NAME + ".in");
            //in = new FastScanner(is);
            //out = new PrintWriter(new File(FILE_NAME + ".out"));
            solve();
            //out.close();
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