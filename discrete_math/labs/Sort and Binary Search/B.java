import java.io.*;
import java.util.StringTokenizer;

public class B {

    public InputStream is;
    public FastScanner in;
    public PrintWriter out;

    public static void main(String[] args) {
        new B().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream("binsearch.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("binsearch.out"));
        int n = in.nextInt();
        int data[] = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int res = leftBinarySearch(data, 0, data.length, x);
            if (res == -1)
                out.println("-1 -1");
            else {
                out.println(res + 1 + " " + (rightBinarySearch(data, 0, data.length, x) + 1));
            }
        }

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

        public String list() {
            return tokenizer.nextToken();
        }


    }


    public static int leftBinarySearch(int[] a, int l, int r, int x) {
        if (a.length == 0)
            return -1;
        if (a[l] > x)
            return -1;
        if (a[r - 1] < x)
            return -1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (x <= a[mid])
                r = mid;
            else
                l = mid + 1;
        }

        if (a[r] == x)
            return r;
        else
            return (-1);


    }

    public static int rightBinarySearch(int[] a, int l, int r, int x) {
        if (a.length == 0)
            return -1;
        if (a[l] > x)
            return -1;
        if (a[r - 1] < x)
            return -1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (x < a[mid])
                r = mid;
            else
                l = mid + 1;
        }
        if (a[r - 1] == x)
            return r - 1;
        else
            return -1;

    }

}