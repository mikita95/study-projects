import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    private final static String fileName = "cond";

    public static void main(String[] args) {
        new B().run();
    }

    ArrayList<ArrayList<Integer>> graph, graph1;
    ArrayList<Integer> topSort, ans;
    ArrayList<Boolean> used;
    int numberOfComponents;

    void dfs(int v) {
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int way = graph.get(v).get(i);
            if (!used.get(way)) {
                dfs(way);
            }
        }
        topSort.add(v);
    }

    void dfs2(int v) {
        used.set(v, true);
        ans.set(v, numberOfComponents);
        for (int i = 0; i < graph1.get(v).size(); i++) {
            int way = graph1.get(v).get(i);
            if (!used.get(way))
                dfs2(way);
        }
    }


    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        graph1 = new ArrayList<ArrayList<Integer>>(n);
        ans = new ArrayList<Integer>(n);
        used = new ArrayList<Boolean>(n);
        topSort = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            graph1.add(new ArrayList<Integer>());
            ans.add(0);
            used.add(false);
        }
    }

    void topSort(int n) {
        for (int i = 0; i < n; i++)
            if (!used.get(i))
                dfs(i);
    }

    void countComponents(int n) {
        for (int i = 0; i < n; i++) {
            int v = topSort.get(n - i - 1);
            if (!used.get(v)) {
                numberOfComponents++;
                dfs2(v);
            }
        }
    }


    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        init(n);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            graph.get(a - 1).add(b - 1);
            graph1.get(b - 1).add(a - 1);
        }
        topSort(n);
        refreshUsed();
        countComponents(n);
        out.println(numberOfComponents);
        for (int i = 0; i < n; i++)
            out.print(ans.get(i) + " ");

        out.close();
    }

    void refreshUsed() {
        for (int i = 0; i < used.size(); i++)
            used.set(i, false);
    }

    public void run() {
        try {
            solve();
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

        public String list() {
            return tokenizer.nextToken();
        }

    }


}