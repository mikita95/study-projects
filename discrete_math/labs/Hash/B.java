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
        InputStream is = new FileInputStream("map.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("map.out"));
        String s, t, r;
        MyHashSet h = new MyHashSet(100002);
        while (in.hasNext()) {
            s = in.list();
 
            if (s.equals("put")) {
                t = in.list();
                r = in.list();
                h.insert(t, r);
            } else if (s.equals("get")) {
                t = in.list();
                item tmp = h.search(t);
                if (tmp != null)
                    out.println(tmp.value);
                else
                    out.println("none");
            } else if (s.equals("delete")) {
                t = in.list();
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
        String key;
        String value;
 
        item next;
 
        item(String key, String value) {
            this.key = key;
            this.value = value;
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
        int q = 53;
        item table[];
 
        MyHashSet(int size) {
            this.size = size;
            this.table = new item[size + 1];
        }
 
        int hash(String key) {
            int k = 1;
            int res = 1;
            for (int i = 0; i < key.length(); i++) {
 
                res = mod(res + (int) (key.charAt(i) - 'a' + 1) * k, size);
                k *= q;
            }
            return mod(res, size);
        }
 
        void insert(String key, String value) {
            delete(key);
            int bucket = hash(key);
            item p0 = table[bucket];
            item p = new item(key, value);
            table[bucket] = p;
            p.next = p0;
            p.key = key;
            p.value = value;
        }
 
        void delete(String key) {
            item p0, p;
            p0 = null;
            int bucket = hash(key);
            p = table[bucket];
            while (p != null && (!p.key.equals(key))) {
                p0 = p;
                p = p.next;
            }
 
            if (p == null) {
                return;
            }
 
            if (p0 != null) {
                p0.next = p.next;
            } else
                table[bucket] = p.next;
            p = null;
        }
 
        item search(String key) {
            item p;
            p = table[hash(key)];
            while (p != null && (!p.key.equals(key))) {
                p = p.next;
            }
            return (p);
        }
 
    }
 
 
}