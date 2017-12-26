
import java.io.*;
import java.util.StringTokenizer;
 
public class A {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
 
    public static void main(String[] args) {
        new A().run();
    }
 
    void solve() throws IOException {
        InputStream is = new FileInputStream("set.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("set.out"));
        String s = "";
        int t;
        MyHashSet h = new MyHashSet(1000002);
        while (in.hasNext()) {
            s = in.list();
 
            t = in.nextInt();
            if (s.equals("insert")) {
                h.insert(t);
            } else if (s.equals("exists")) {
                if (h.search(t))
                    out.println("true");
                else
                    out.println("false");
            } else if (s.equals("delete")) {
                h.delete(t);
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
 
    public class item {
        int key;
        item next;
 
        item(int key) {
            this.key = key;
        }
    }
 
    public int mod(int a, int b) {
        if (a >= 0)
            return a % b;
        else
            return (a % b) + b;
    }
 
    class MyHashSet {
        int size = 1;
        item table[];
 
        MyHashSet(int size) {
            this.size = size;
            this.table = new item[size];
        }
 
        int hash(int key) {
            return (mod(key, size));
        }
 
        void insert(int key) {
            if (search(key))
                return;
            int bucket = hash(key);
            item p0 = table[bucket];
            item p = new item(key);
            table[bucket] = p;
            p.next = p0;
            p.key = key;
        }
 
        void delete(int key) {
            item p0, p;
            p0 = null;
            int bucket = hash(key);
            p = table[bucket];
            while (p != null && (p.key != key)) {
                p0 = p;
                p = p.next;
            }
 
            if (p == null){
                return;
            }
 
            if (p0 != null) {
                p0.next = p.next;
            } else
                table[bucket] = p.next;
            p = null;
        }
 
        boolean search(int key) {
            item p;
            p = table[hash(key)];
            while (p != null && (p.key != key)) {
                p = p.next;
            }
            return (p != null);
        }
 
    }
 
 
}