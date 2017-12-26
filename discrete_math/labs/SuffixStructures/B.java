import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "array";

    public static void main(String[] args) {
        new B().run();
    }

    private static void swapArrays(int[] a, int[] b) {
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            int tmp = a[i];
            a[i] = b[i];
            b[i] = tmp;
        }
    }

    class SuffixArray {
        ArrayList<Integer> array;
        public final static int POWER = 27;
        SuffixArray(final String string) {
            String s = string;
            s += (char) ('a' - 1);
            int n = s.length();
            int position[] = new int[n];
            int position1[] = new int[n];
            int suf[] = new int[n];
            int suf1[] = new int[n];
            int ord[] = new int[Math.max(POWER, n)];
            int ord1[] = new int[Math.max(POWER, n)];
            int count[] = new int[POWER];
            for (int i = 0; i < n; i++) {
                position[i] = s.charAt(i) - 'a' + 1;
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
            array = new ArrayList<Integer>();
            for (int i = 1; i < suf.length; i++)
                array.add(suf[i]);

        }

        ArrayList<Integer> getArray() {
            return array;
        }


    }

    void solve() throws IOException {
        SuffixArray suffixArray = new SuffixArray(in.next());
        for (Integer e : suffixArray.getArray()) {
            out.print(e + 1 + " ");
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