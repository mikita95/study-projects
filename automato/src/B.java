
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "crossover";

    public static void main(String[] args) {
        new B().run();
    }

    void solve() throws IOException {
        int m = in.nextInt();
        int n = in.nextInt();
        ArrayList<StringBuilder> A = new ArrayList<StringBuilder>();
        for (int i = 0; i < m; i++) {
            A.add(new StringBuilder(in.next()));
        }
        StringBuilder s = new StringBuilder(in.next());

        boolean flag1 = false, flag2 = false, flag3 = false;

        for (int i = 0; i < m; i++) {
            for (int k = i; k < m; k++) {
                StringBuilder a = A.get(i);
                StringBuilder b = A.get(k);
                if (!flag1) {
                    for (int j = 0; j <= n; j++) {
                        boolean f = true;
                        for (int t = 0; t < n; t++) {
                            char curr;
                            if (t < j) curr = a.charAt(t);
                            else curr = b.charAt(t);
                            if (s.charAt(t) != curr) {
                                f = false;
                                break;
                            }
                        }
                        if (f) {
                            flag1 = true;
                            break;
                        }
                        if (i == k) break;
                        f = true;
                        for (int t = 0; t < n; t++) {
                            char curr;
                            if (t < j) curr = b.charAt(t);
                            else curr = a.charAt(t);
                            if (s.charAt(t) != curr) {
                                f = false;
                                break;
                            }
                        }
                        if (f) {
                            flag1 = true;
                            break;
                        }
                    }

                }
                if (!flag2) {
                    for (int j = 0; j <= n; j++) {
                        for (int t = j; t <= n; t++) {

                            boolean f = true;
                            for (int c = 0; c < n; c++) {
                                char curr = 'a';
                                if (c >= 0 && c < j) curr = a.charAt(c);
                                if (c >= j && c < n) curr = b.charAt(c);
                                if (c >= t && c < n) curr = a.charAt(c);
                                if (s.charAt(c) != curr) {
                                    f = false;
                                    break;
                                }
                            }
                            if (f) {
                                flag2 = true;
                                break;
                            }
                            if (i == k) break;
                            f = true;
                            for (int c = 0; c < n; c++) {
                                char curr = 'a';
                                if (c >= 0 && c < j) curr = b.charAt(c);
                                if (c >= j && c < n) curr = a.charAt(c);
                                if (c >= t && c < n) curr = b.charAt(c);
                                if (s.charAt(c) != curr) {
                                    f = false;
                                    break;
                                }
                            }
                            if (f) {
                                flag2 = true;
                                break;
                            }
                        }
                        if (flag2) break;
                    }
                }
                if (!flag3) {
                    boolean f = true;
                    for (int j = 0; j < n; j++) {
                        if (!(s.charAt(j) == a.charAt(j) || s.charAt(j) == b.charAt(j))) {
                            f = false;
                            break;
                        }
                    }
                    flag3 = f;
                }
                if (flag1 && flag2 && flag3) break;
            }
            if (flag1 && flag2 && flag3) break;
        }
        out.println(flag1 ? "YES" : "NO");
        out.println(flag2 ? "YES" : "NO");
        out.println(flag3 ? "YES" : "NO");

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