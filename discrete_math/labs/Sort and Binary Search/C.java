import java.io.*;
import java.util.StringTokenizer;
 
public class C {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
 
 
    public static void main(String[] args) {
        new C().run();
    }
 
    void solve() throws IOException {
        is = new FileInputStream("kth.in");
        in = new FastScanner(is);
        out = new PrintWriter(new File("kth.out"));
 
        int n = in.nextInt();
        int k = in.nextInt();
        int data[] = new int[n + 1];
        int A = in.nextInt();
        int B = in.nextInt();
        int C = in.nextInt();
        data[1] = in.nextInt();
        data[2] = in.nextInt();
        for (int i = 3; i <= n; i++) {
            data[i] = A * data[i - 2] + B * data[i - 1] + C;
        }
        out.print(findOrderStatistic(data, n, k));
 
 
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
 
    void swap(int a[], int x, int y) {
        int c = a[x];
        a[x] = a[y];
        a[y] = c;
    }
 
    void doSwaps(int a[], int l, int r) {
        if (a[l] > a[r])
            swap(a, l, r);
        if (a[l + 1] > a[r])
            swap(a, l + 1, r);
        if (a[l] > a[l + 1])
            swap(a, l, l + 1);
 
    }
 
    int makeBalance(int a[], int cur, int i, int j) {
        return 0;
 
    }
 
    class Pair {
        int first, second;
 
        Pair(int x, int y) {
            first = x;
            second = y;
        }
    }
 
    Pair changeValues(int i, int j, int k, int a, int b) {
        int r = a, l = b;
        if (j >= k)
            r = j - 1;
        if (j <= k)
            l = i;
        return new Pair(r, l);
 
 
    }
 
    int findOrderStatistic(int a[], int n, int k) {
        for (int l = 1, r = n; ; ) {
            if (r <= l + 1) {
                if (r == l + 1 && a[r] < a[l])
                    swap(a, l, r);
                return a[k];
            }
 
            swap(a, l + (r - l) / 2, l + 1);
            doSwaps(a, l, r);
 
            int i = l + 1;
            int j = r;
            int cur = a[l + 1];
            int c = makeBalance(a, cur, i, j);
            while (true) {
                if (a[++i] < cur) {
                    do {
                        ;
                    } while (a[++i] < cur);
                }
                if (a[--j] > cur) {
                    do ;
                    while (a[--j] > cur);
                }
                if (i > j)
                    break;
                swap(a, i, j);
            }
            a[l + 1] = a[j];
            a[j] = cur;
            Pair tmp = changeValues(i, j, k, r, l);
            r = tmp.first;
            l = tmp.second;
        }
    }
}