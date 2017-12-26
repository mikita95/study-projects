import java.io.*;
import java.util.StringTokenizer;
 
public class D {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
 
    public static void main(String[] args) {
        new D().run();
    }
 
    void solve() throws IOException {
        InputStream is = new FileInputStream("multimap.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("multimap.out"));
        String s, t, r;
        MyHashMap h = new MyHashMap(100002);
        while (in.hasNext()) {
            s = in.list();
 
            if (s.equals("put")) {
                t = in.list();
                r = in.list();
                h.insert(t, r);
            } else if (s.equals("get")) {
                t = in.list();
                item tmp = h.get(t);
                if (tmp != null) {
                    out.print(tmp.value.getSize());
                    myList b = tmp.value.getAll();
                    while (!b.isEmpty()) {
                        out.print(' ' + b.pop());
                    }
 
                    out.println();
 
                } else
                    out.println("0");
            } else if (s.equals("delete")) {
                t = in.list();
                r = in.list();
                h.delete(t, r);
            } else if (s.equals("deleteall")) {
                t = in.list();
                h.deleteAll(t);
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
 
    public class node {
        String value;
        node next;
    }
 
    public class itemSet {
        String key;
        itemSet next;
        itemSet after;
        int hash;
        itemSet before;
 
        itemSet(String key) {
            this.hash = -1;
            this.key = key;
        }
 
    }
 
    public class myList {
        int size;
        node head;
        node tail;
 
        int size() {
            return size;
        }
 
        boolean isEmpty() {
            return (size == 0);
        }
 
        void push(String value) {
            node tmp = new node();
            tmp.value = value;
            tmp.next = null;
            if (head == null) {
                head = tmp;
                tail = head;
            } else {
                tail.next = tmp;
                tail = tmp;
            }
            size++;
        }
 
        String pop() {
            String value = head.value;
            node tmp = head.next;
            head = null;
            if (tmp == null) {
                head = tail = null;
            } else {
                head = tmp;
            }
            size--;
            return value;
        }
 
        boolean contains(String value) {
            int s = this.size;
            for (int i = 0; i < s; i++) {
                if (head.value.equals(value)) {
                    return true;
                }
                String tmp = pop();
                push(tmp);
            }
            return false;
 
        }
 
        void remove(String value) {
            int s = this.size;
 
            for (int i = 0; i < s; i++) {
                if (head.value.equals(value)) {
                    pop();
                    break;
                }
                String tmp = pop();
                push(tmp);
            }
        }
    }
 
    public class MyHashSet {
        int size = 1;
        itemSet table[];
        int q = 53;
        int full = 0;
        itemSet header = new itemSet(null);
 
 
        MyHashSet(int size) {
            this.size = size;
            this.table = new itemSet[size];
            this.header.after = this.header.before = this.header;
        }
 
        int getSize() {
            return full;
        }
 
        public int mod(int a, int b) {
            if (a >= 0)
                return a % b;
            else
                return (a % b) + b;
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
 
        myList getAll() {
            myList res = new myList();
            itemSet tmp = header.after;
            while (tmp.hash != -1) {
                res.push(tmp.key);
                tmp = tmp.after;
            }
 
            return res;
        }
 
        itemSet getNext(String key) {
            itemSet tmp = search(key);
            if (tmp == null) {
                return null;
            }
 
            if (tmp.hash == -1)
                return null;
 
            if (tmp.after.hash == -1)
                return null;
 
            return tmp.after;
        }
 
        void insert(String key) {
            if (search(key) != null)
                return;
            int bucket = hash(key);
            itemSet p0 = table[bucket];
            itemSet p = new itemSet(key);
            p.hash = bucket;
            table[bucket] = p;
            p.next = p0;
            p.key = key;
            p.before = header.before;
            header.before.after = p;
            header.before = p;
            p.after = header;
            full++;
        }
 
        void delete(String key) {
            itemSet p0, p;
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
 
            p.before.after = p.after;
            p.after.before = p.before;
            p = null;
            full--;
        }
 
        itemSet search(String key) {
            itemSet p;
            p = table[hash(key)];
            while (p != null && (!p.key.equals(key))) {
                p = p.next;
            }
            return (p);
        }
 
    }
 
    public class item {
        int hash;
        String key;
        MyHashSet value;
        item next;
 
        item(String key) {
            this.hash = -1;
            this.key = key;
            //this.value = new MyHashSet(100002);
        }
 
 
    }
 
    public class MyHashMap {
        int size = 1;
        int q = 53;
        item table[];
 
        public int mod(int a, int b) {
            if (a >= 0)
                return a % b;
            else
                return (a % b) + b;
        }
 
        MyHashMap(int size) {
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
            if (search(key, value)) {
                return;
            }
 
            int bucket = hash(key);
            item p0 = table[bucket];
            item tmp = table[bucket];
 
            while (tmp != null && (!tmp.key.equals(key))) {
                tmp = tmp.next;
            }
 
            if (tmp == null) {
                item p = new item(key);
                p.value = new MyHashSet(1001);
                p.value.insert(value);
                p.hash = bucket;
                table[bucket] = p;
                p.next = p0;
            } else {
                tmp.value.insert(value);
            }
            return;
        }
 
 
        void delete(String key, String value) {
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
            if (p.value.getSize() == 0) {
                if (p0 != null) {
                    {
                        p0.next = p.next;
 
                    }
                } else {
                    table[bucket] = p.next;
                }
 
                p = null;
            } else {
                p.value.delete(value);
            }
        }
 
        void deleteAll(String key) {
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
                {
                    p0.next = p.next;
 
                }
            } else {
                table[bucket] = p.next;
            }
 
            p = null;
 
        }
 
        item get(String key) {
            item p;
            p = table[hash(key)];
            while (p != null && (!p.key.equals(key))) {
                p = p.next;
            }
            return (p);
        }
 
 
        boolean search(String key, String value) {
            item p;
            p = table[hash(key)];
            while (p != null && (!p.key.equals(key))) {
                p = p.next;
            }
 
            if (p == null)
                return false;
 
            if (p.value.search(value) != null) {
                return true;
            } else return false;
 
 
        }
 
    }
}