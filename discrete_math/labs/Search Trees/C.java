import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
 
public class C implements Runnable {
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
    public static Random random;
    private SplayTree root = null;
    private final SplayTree alena = new SplayTree(0, 0);
 
    static class SplayTree {
        SplayTree left, right;
        int key, value, count;
 
        SplayTree(int key, int value) {
            this.key = key;
            this.value = value;
            count = 1;
        }
    }
 
    public SplayTree maximum(SplayTree t) {
        if (t.right == null)
            return t;
        return maximum(t.right);
    }
 
    public SplayTree minimum(SplayTree t) {
        if (t.left == null)
            return t;
        return minimum(t.left);
    }
 
    public SplayTree merge(SplayTree t1, SplayTree t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        t1 = splay(t1, maximum(t1).key);
        t1.right = t2;
        return t1;
    }
 
    public SplayTree[] split(SplayTree t, int key) {
        if (t == null) {
            return new SplayTree[]{null, null};
        }
        t = splay(t, key);
        if (t.key < key)
        {
            SplayTree tmp = t.right;
            t.right = null;
            return new SplayTree[]{t, tmp};
        }
        else
        {
            SplayTree tmp = t.left;
            t.left = null;
            return new SplayTree[]{tmp, t};
        }
 
    }
 
    public SplayTree splay(SplayTree tree, int element) {
        SplayTree l, r, t, y;
        l = r = alena;
        t = tree;
        alena.left = alena.right = null;
        while(true) {
            if (element < t.key) {
                if (t.left == null) break;
                if (element < t.left.key) {
                    y = t.left;
                    t.left = y.right;
                    y.right = t;
                    t = y;
                    if (t.left == null) break;
                }
                r.left = t;
                r = t;
                t = t.left;
            } else if (element > t.key) {
                if (t.right == null) break;
                if (element > t.right.key) {
                    y = t.right;
                    t.right = y.left;
                    y.left = t;
                    t = y;
                    if (t.right == null) break;
                }
                l.right = t;
                l = t;
                t = t.right;
            } else {
                break;
            }
        }
        l.right = t.left;
        r.left = t.right;
        t.left = alena.right;
        t.right = alena.left;
        return  t;
    }
 
    public SplayTree getNode(SplayTree t, int key) {
        SplayTree tmp = t;
 
        while (tmp != null && tmp.key != key) {
            if (tmp.key > key) {
                tmp = tmp.left;
            } else if (tmp.key < key) {
                tmp = tmp.right;
            }
        }
 
        return tmp;
    }
 
    public SplayTree add(SplayTree t, int key, int value) {
        SplayTree[] tmp = split(t, key);
        SplayTree res = new SplayTree(key, value);
        res.right = tmp[1];
        res.left = tmp[0];
        return res;
    }
 
    public SplayTree remove(SplayTree t, int key) {
        t = splay(t, key);
        return merge(t.left, t.right);
    }
 
    public void move(int a, int b) {
        int pos = 1;
        ArrayList<Integer> mas = new ArrayList<Integer>();
 
 
        for (int i = 1; i < a; i++) {
            SplayTree t = getNode(root, i);
            mas.add(t.value);
            root = remove(root, i);
        }
 
        for (int i = a; i <= b; i++) {
            SplayTree t = getNode(root, i);
            root = remove(root, i);
            t.key = i - a + 1;
            root = add(root, i - a + 1, t.value);
        }
 
        for  (int i = 1; i < a; i++ ) {
            root = add(root, i + b - a + 1, mas.get(i - 1));
        }
    }
 
 
 
    void solve() throws IOException {
        InputStream is = new FileInputStream("movetofront.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("movetofront.out"));
        String s;
        int n = in.nextInt();
        int m = in.nextInt();
        int a ,b;
 
        for (int i = n; i >=1; i--) {
            root = add(root, i, i);
        }
 
        for (int i = 0; i < m; i++) {
            a = in.nextInt();
            b = in.nextInt();
            move(a, b);
        }
 
        for (int i = 1; i <= n; i++) {
            SplayTree tmp = getNode(root, i);
            out.print(tmp.value);
            if (i != n)
                out.print(" ");
            root = remove(root, i);
        }
        out.close();
    }
 
    public static void main(String[] args) {
        new C().run();
    }
 
    public void run() {
        try {
            random = new Random();
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