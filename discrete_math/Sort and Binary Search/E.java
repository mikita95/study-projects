import java.io.*;
import java.util.StringTokenizer;

public class E {

    public InputStream is;
    public FastScanner in;
    public PrintWriter out;


    public static void main(String[] args) {
        new E().run();
    }

    void solve() throws IOException {
        is = new FileInputStream("radixsort.in");
        in = new FastScanner(is);
        out = new PrintWriter(new File("radixsort.out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        String a[] = new String[n];
        for (int i = 0; i < n; i++)
            a[i] = in.next();
        radixSort(a, k, m);
        for (int i = 0; i < n; i++)
            out.println(a[i]);


        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException e) {
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

    private static final int R = 256;

    public static void radixSort(String[] a, int k, int m) {
        int N = a.length;
        String[] aux = new String[N];
        sort(a, 0, N - 1, m - k, aux);
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void f(String[] a, int count[], int lo, int hi, int d, String[] aux) {
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + 15) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && compare(a[j], a[j - 1], d); j--)
                    swap(a, j, j - 1);
            return;
        }
        int[] count = new int[R + 2];

        f(a, count, lo, hi, d, aux);

        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }


    private static void swap(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    private static boolean compare(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }


}