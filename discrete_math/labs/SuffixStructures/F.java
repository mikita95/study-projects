import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class F {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "common";

    private static void swapArrays(int[] a, int[] b) {
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            int tmp = a[i];
            a[i] = b[i];
            b[i] = tmp;
        }
    }

    class SuffixArray {
        int[] array;
        public final static int POWER = 28;
        String string;
        SuffixArray(final String string) {
            this.string = string;
            String s = string;
            s += (char) ('a' - 2);
            int n = s.length();
            int position[] = new int[n];
            int position1[] = new int[n];
            int suf[] = new int[n];
            int suf1[] = new int[n];
            int ord[] = new int[Math.max(POWER, n)];
            int ord1[] = new int[Math.max(POWER, n)];
            int count[] = new int[POWER];
            for (int i = 0; i < n; i++) {
                position[i] = s.charAt(i) - ('a' - 1) + 1;
                count[position[i]]++;
            }
            for (int i = 1; i < POWER; i++) {
                ord[i] = ord[i - 1] + count[i - 1];
                ord1[i] = ord[i];
            }
            for (int i = 0; i < n; i++) {
                suf[ord1[position[i]]++] = i;
            }
            int k = 1;
            while (k < n) {
                for (int i = 0; i < n; i++) {
                    suf1[ord[position[((suf[i] - k) % n + n) % n]]++] = ((suf[i] - k) % n + n) % n;
                }
                int place = 0;
                ord[place++] = 0;
                for (int i = 1; i < n; i++) {
                    if (position[(suf1[i] + k) % n] != position[(suf1[i - 1] + k) % n])
                        ord[place++] = i;
                    else if (position[suf1[i]] != position[suf1[i - 1]])
                        ord[place++] = i;
                    position1[suf1[i]] = place - 1;
                }
                k *= 2;
                swapArrays(suf, suf1);
                swapArrays(position, position1);

            }
            array = new int[string.length()];
            System.arraycopy(suf, 1, array, 0, suf.length - 1);

        }

        int[] getArray() {
            return array;
        }

        public int[] getLCP() {
            return lcp(string, array);
        }

        public int[] lcp(String s, int[] suf) {
            int n = s.length();
            int[] lcp = new int[n];
            int[] pos = new int[n];
            for (int i = 0; i < n; i++) {
                pos[suf[i]] = i;
            }
            int k = 0;
            for (int i = 0; i < n; i++) {
                if (k > 0)
                    k--;
                if (pos[i] == n - 1) {
                    lcp[n - 1] = -1;
                    k = 0;
                } else {
                    int j = suf[pos[i] + 1];
                    while (Math.max(i + k, j + k) < n && s.charAt(i + k) == s.charAt(j + k)) {
                        k++;
                    }
                    lcp[pos[i]] = k;
                }
            }
            return lcp;
        }

        public String getString() {
            return string;
        }

        public int get(int i) {
            return array[i];
        }

    }



    public static void main(String[] args) {
        new F().run();
    }

    void solve() throws IOException {
        String s = in.next();
        String t = in.next();
        SuffixArray a = new SuffixArray(s + (char)('a' - 1) + t);
        String lcs = "";
        int[] lcp = a.getLCP();
        for (int i = 0; i < a.getString().length(); i++) {
            if (a.get(i) < s.length() && a.get(i - 1) < s.length()) continue;
            if (a.get(i) > t.length() && a.get(i - 1) > t.length()) continue;
            int length = lcp[i];
            if (length > lcs.length()) {
                lcs = a.getString().substring(a.get(i), a.get(i) + length);
            }
        }
        out.print(lcs);

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