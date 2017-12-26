
import java.io.*;
import java.util.StringTokenizer;
 
public class A {
    private static Node root = new Node(0, 0);
 
    private static int compare(int a, int b) {
        if (a == b)
            return 0;
        if (a > b)
            return 1;
        if (a < b)
            return (-1);
        return 0;
    }
 
    public static Node get(Node x, int k) {
        if (x == null || k == x.key)
            return x;
        if (k < x.key)
            return get(x.left, k);
        else
            return get(x.right, k);
    }
 
    private static void put(Node t, Node z) {
        Node x = t;
        if (t.key == Integer.MIN_VALUE && t.right == null) {
            t.right = z;
            z.parent = t;
            return;
        } else {
            if (t == root)
                x = t.right;
        }
        if (z.key > x.key) {
            if (x.right != null)
                put(x.right, z);
            else {
                z.parent = x;
                x.right = z;
            }
        } else {
            if (x.left != null)
                put(x.left, z);
            else {
                z.parent = x;
                x.left = z;
            }
        }
    }
 
    private static void remove(Node rt, Node z) {
        Node x, y;
        Node r = rt.right;
        if (z.left != null && z.right != null) {
 
            y = next(z);
            x = null;
 
            if (y == y.parent.left) {
 
                y.parent.left = null;
            } else {
                y.parent.right = null;
            }
            z.key = y.key;
            z.val = y.val;
        } else if (z.left != null || z.right != null) {
            y = z;
            if (y.left != null)
                x = y.left;
            else
                x = y.right;
        } else {
            y = z;
            x = null;
        }
        if (x != null)
            x.parent = y.parent;
        if (y.parent == null)
            r = x;
        else {
            if (y == y.parent.left)
                y.parent.left = x;
            else
                y.parent.right = x;
        }
    }
 
    private static Node minimum(Node x) {
        if (x.left == null)
            return x;
        return minimum(x.left);
    }
 
    private static Node maximum(Node x) {
        if (x.right == null)
            return x;
        return maximum(x.right);
    }
 
    private static Node next(Node x) {
        if (x.right != null)
            return minimum(x.right);
        Node y = x.parent;
        while (y.key != Integer.MIN_VALUE && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }
 
    private static Node prev(Node x) {
        if (x.left != null)
            return maximum(x.left);
        Node y = x.parent;
        while (y.key != Integer.MIN_VALUE && x == y.left) {
            x = y;
            y = y.parent;
        }
 
        return y;
    }
 
 
    private static class Node {
        int key;
        int val;
 
        int N;
        Node left, right, parent;
 
        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.N = 1;
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
 
 
    public static void main(String[] args) {
        root.key = Integer.MIN_VALUE;
        try {
            InputStream is = new FileInputStream("bstsimple.in");
            FastScanner in = new FastScanner(is);
            PrintWriter out = new PrintWriter(new File("bstsimple.out"));
            try {
                String s;
                int a;
                while (in.hasNext()) {
                    s = in.next();
                    a = in.nextInt();
                    if (s.equals("insert")) {
                        Node tmt = get(root, a);
                        if (tmt == null) {
                            put(root, new Node(a, a));
                        }
                    } else if (s.equals("exists")) {
                        Node tmt = get(root, a);
                        if (tmt == null) {
                            out.println("false");
                        } else {
                            out.println("true");
                        }
                    } else if (s.equals("next")) {
                        boolean f = false;
                        if (get(root, a) == null) {
                            f = true;
                            put(root, new Node(a, a));
                        }
                        Node tmt = next(get(root, a));
                        if (f)
                            remove(root, get(root, a));
                        if (tmt.key == Integer.MIN_VALUE) {
                            out.println("none");
                        } else {
                            out.println(tmt.val);
                        }
                    } else if (s.equals("prev")) {
                        boolean f = false;
                        if (get(root, a) == null) {
                            f = true;
                            put(root, new Node(a, a));
                        }
                        Node tmt = prev(get(root, a));
                        if (f)
                            remove(root, get(root, a));
                        if (tmt.key == Integer.MIN_VALUE) {
                            out.println("none");
                        } else {
                            out.println(tmt.val);
                        }
 
                    } else if (s.equals("delete")) {
                        Node tmt = get(root, a);
                        if (tmt != null) {
                            remove(root, get(root, a));
                        }
                    }
 
                }
 
            } finally {
 
                out.close();
 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
 
}