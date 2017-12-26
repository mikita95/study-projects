import java.io.*;
import java.util.StringTokenizer;
 
public class C {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
 
    public class item {
        int a, b, c, d;
 
        item(int a, int b, int c, int d) {
            this.a = a % r;
            this.b = b % r;
            this.c = c % r;
            this.d = d % r;
 
        }
 
        boolean equal(item x) {
            if (x.a == this.a && x.b == this.b && x.c == this.c && x.d == this.d)
                return true;
            else
                return false;
        }
    }
 
    public int r = 10;
 
    public class Tree {
 
 
        item tree[];
        private int n;
 
        public item inf = new item(1, 0, 0, 1);
 
        public item operation(item x, item y) {
            int a = (x.a * y.a + x.b * y.c) % r;
            int b = (x.a * y.b + x.b * y.d) % r;
            int c = (x.c * y.a + x.d * y.c) % r;
            int d = (x.c * y.b + x.d * y.d) % r;
            return new item(a, b, c, d);
        }
 
        Tree(int n) {
 
            tree = new item[4 * n];
            this.n = n;
        }
 
        void build(item data[]) {
            build(data, 1, 0, this.n - 1);
        }
 
        void build(item a[], int v, int tl, int tr) {
            if (tl == tr)
                tree[v] = a[tl];
            else {
                int tm = (tl + tr) / 2;
                build(a, v * 2, tl, tm);
                build(a, v * 2 + 1, tm + 1, tr);
                tree[v] = operation(tree[v * 2], tree[v * 2 + 1]);
            }
        }
 
              item getResult(int a, int b) {
            return getResult(1, 0, this.n - 1, a - 1, b - 1);
        }
 
        item getResult(int v, int tl, int tr, int l, int r) {
            if (l > r)
                return inf;
            if (l == tl && r == tr)
                return tree[v];
            int tm = (tl + tr) / 2;
            return operation(getResult(v * 2, tl, tm, l, Math.min(r, tm))
                    , getResult(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r));
        }
 
        void modify(int i, item v) {
            i = i + n - 1;
            tree[i] = v;
            i /= 2;
            while (i > 0) {
                tree[i] = operation(tree[2 * i], tree[2 * i + 1]);
                i /= 2;
            }
        }
 
 
    }
 
    public static void main(String[] args) {
        new C().run();
    }
 
    void solve() throws IOException {
        InputStream is = new FileInputStream("crypto.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("crypto.out"));
        int n, m, a, b, c, d;
        r = in.nextInt();
        n = in.nextInt();
        m = in.nextInt();
 
        item data[] = new item[200001];
        int count = 0;
        for (int i = 0; i < n; i++) {
            a = in.nextInt();
            b = in.nextInt();
            c = in.nextInt();
            d = in.nextInt();
            data[count] = new item(a, b, c, d);
            count++;
        }
 
        Tree tree = new Tree(n);
        tree.build(data);
        item tmp;
 
        for (int i = 0; i < m; i++) {
            a = in.nextInt();
            b = in.nextInt();
            tmp = tree.getResult(a, b);
 
            out.println(tmp.a + " " + tmp.b);
            out.println(tmp.c + " " + tmp.d);
            out.println();
 
 
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
 
        public String next() {
            return tokenizer.nextToken();
        }
 
    }
}