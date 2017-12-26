import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class C {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "trees";

    public static void main(String[] args) {
        new C().run();
    }

    class Tree {
        private int k;
        private ArrayList<Integer> p, f, t;
        private ArrayList<Boolean> leaf;
        public Tree(int k) {
            p = new ArrayList<Integer>();
            f = new ArrayList<Integer>();
            t = new ArrayList<Integer>();
            leaf = new ArrayList<Boolean>();
            for (int i = 0; i < k; i++) {
                p.add(0);
                f.add(-1);
                t.add(-1);
                leaf.add(false);
            }
            this.k = k;
        }

        public void addChoice(int i, int pi, int fi, int ti) {
            p.set(i, pi);
            f.set(i, fi);
            t.set(i, ti);
        }

        public void addLeaf(int i, int pi) {
            p.set(i, pi);
            leaf.set(i, true);
        }

        private ArrayList<Boolean> newLeaf;
        private ArrayList<Integer> newF, newT, newP;
        private HashMap<Integer, Integer> map;
        public void removeUnreachable() {
            newLeaf = new ArrayList<Boolean>();
            newF = new ArrayList<Integer>();
            newT = new ArrayList<Integer>();
            newP = new ArrayList<Integer>();
            map = new HashMap<Integer, Integer>();
            dfs(0);
            this.k = newLeaf.size();
            this.p = newP;
            this.f = newF;
            this.t = newT;
            this.leaf = newLeaf;
        }

        private void addNewState(boolean isLeaf, int predicateValue) {
            newLeaf.add(isLeaf);
            newF.add(-1);
            newT.add(-1);
            newP.add(predicateValue);
        }

        private int dfs(int v) {
            int u = p.get(v);
            if (f.get(v) == -1) {
                addNewState(true, u);
                return newF.size() - 1;
            }
            if (map.containsKey(u)) {
                if (map.get(u) == 0) return dfs(f.get(v));
                else return dfs(t.get(v));
            }
            int index = newF.size();
            addNewState(false, u);

            map.put(u, 0);
            newF.set(index, dfs(f.get(v)));

            map.put(u, 1);
            newT.set(index, dfs(t.get(v)));

            map.remove(u);
            return index;
        }


        public StringBuilder toStringBuilder() {
            StringBuilder s = new StringBuilder();
            s.append(k).append('\n');
            for (int i = 0; i < k; i++) {
                if (leaf.get(i)) s.append("leaf ").append(p.get(i)).append('\n');
                else s.append("choice ").append(p.get(i)).append(" ").append(f.get(i) + 1).append(" ").append(t.get(i) + 1).append('\n');

            }
            return s;
        }
    }

    void solve() throws IOException {
        int k = in.nextInt();
        Tree tree = new Tree(k);
        for (int i = 0; i < k; i++) {
            if (in.next().equals("leaf"))
                tree.addLeaf(i, in.nextInt());
            else {
                tree.addChoice(i, in.nextInt(), in.nextInt() - 1, in.nextInt() - 1);
            }
        }
        tree.removeUnreachable();
        out.print(tree.toStringBuilder());

    }

    public void run() {
        try {
            //long millis = System.currentTimeMillis();
            InputStream is = new FileInputStream(FILE_NAME + ".in");
            in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".out"));
            solve();
            out.close();
            //millis = System.currentTimeMillis() - millis;
            //System.out.println(millis);
        } catch (IOException ignored) {
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