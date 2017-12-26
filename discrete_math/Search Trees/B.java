import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;
 
public class B implements Runnable {
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
    public static Random random;
 
    static class Treap {
        Treap left, right;
        int key, value, count;
 
        Treap(int key, int value) {
            this.key = key;
            this.value = value;
            count = 1;
        }
    }
 
    public Treap merge(Treap left, Treap right) {
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else if (left.key > right.key) {
            left.right = merge(left.right, right);
 
            return left;
        } else {
            right.left = merge(left, right.left);
 
            return right;
        }
    }
 
    public Treap[] split(Treap t, int key) {
        if (t == null) {
            return new Treap[]{null, null};
        }
 
        if (t.value >= key) {
            Treap[] res = split(t.left, key);
            t.left = res[1];
 
            res[1] = t;
            return res;
        } else {
            Treap[] res = split(t.right, key);
            t.right = res[0];
 
            res[0] = t;
            return res;
        }
    }
 
    public Treap getNode(Treap t, int value) {
        Treap tmp = t;
 
        while (tmp != null && tmp.value != value) {
            if (tmp.value > value) {
                tmp = tmp.left;
            } else if (tmp.value < value) {
                tmp = tmp.right;
            }
        }
 
        return tmp;
    }
 
    public Treap add(Treap t, int element) {
        Treap flag = getNode(t, element);
 
        if (flag != null) {
            ++flag.count;
 
            return t;
        } else {
 
            Treap newNode = new Treap(random.nextInt(), element);
 
            return insert(t, newNode);
        }
    }
 
    public Treap insert(Treap current, Treap add) {
        if (current == null) {
            return add;
        } else if (add.key > current.key) {
            Treap[] mas = split(current, add.value);
            add.left = mas[0];
            add.right = mas[1];
 
            return add;
        } else if (add.value < current.value) {
            current.left = insert(current.left, add);
 
            return current;
        } else {
            current.right = insert(current.right, add);
 
            return current;
        }
    }
 
    public Treap remove(Treap t, int element) {
        //if (getNode(t, element) != null)
        return erase(t, element);
        //return null;
    }
 
    public Treap erase(Treap current, int value) {
        if (current.value == value) {
            --current.count;
 
            if (current.count == 0) {
                return merge(current.left, current.right);
            } else {
                return current;
            }
        } else if (value < current.value) {
            current.left = erase(current.left, value);
 
            return current;
        } else {
            current.right = erase(current.right, value);
 
            return current;
        }
    }
 
    public Treap minimum(Treap t) {
        if (t.left == null)
            return t;
        return minimum(t.left);
    }
 
    public Treap maximum(Treap t) {
        if (t.right == null)
            return t;
        return maximum(t.right);
    }
 
    public Treap parent(Treap t, int value) {
        Treap tmp = t;
        Treap parent = null;
 
        while (tmp != null && tmp.value != value) {
            if (tmp.value > value) {
                parent = tmp;
                tmp = tmp.left;
            } else if (tmp.value < value) {
                parent = tmp;
                tmp = tmp.right;
            }
        }
 
        return parent;
    }
 
    public Treap next(Treap t, Treap x) {
        if (x.right != null)
            return minimum(x.right);
        Treap y = parent(t, x.value);
        while (y != null && x == y.right) {
            x = y;
            y = parent(t, y.value);
        }
        return y;
    }
 
    public Treap prev(Treap t, Treap x) {
        if (x.left != null)
            return maximum(x.left);
        Treap y = parent(t, x.value);
        while (y != null && x == y.left) {
            x = y;
            y = parent(t, y.value);
        }
        return y;
    }
 
    void solve() throws IOException {
        InputStream is = new FileInputStream("bst.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("bst.out"));
        String s;
        Treap t = null;
        int a;
        while (in.hasNext()) {
            s = in.next();
            a = in.nextInt();
            if (s.equals("insert")) {
                if (getNode(t, a) == null)
                    t = add(t, a);
            } else if (s.equals("delete")) {
                if (getNode(t, a) != null)
                    t = remove(t, a);
 
            } else if (s.equals("exists")) {
                if (getNode(t, a) == null)
                    out.println("false");
                else
                    out.println("true");
            } else if (s.equals("next")) {
                boolean f = false;
                Treap tmp = getNode(t, a);
                if (tmp == null) {
                    f = true;
                    t = add(t, a);
                }
                tmp = next(t, getNode(t, a));
                if (f)
                    t = remove(t, a);
                if (tmp == null)
                    out.println("none");
                else
                    out.println(tmp.value);
            } else if (s.equals("prev")) {
                boolean f = false;
                Treap tmp = getNode(t, a);
                if (tmp == null) {
                    f = true;
                    t = add(t, a);
                }
                tmp = prev(t, getNode(t, a));
                if (f)
                    t = remove(t, a);
                if (tmp == null)
                    out.println("none");
                else
                    out.println(tmp.value);
            }
        }
        out.close();
    }
 
    public static void main(String[] args) {
        new B().run();
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