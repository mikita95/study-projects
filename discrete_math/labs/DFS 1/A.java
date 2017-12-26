import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    private final static String fileName = "topsort";

    public static void main(String[] args) {
        new A().run();
    }

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> color, topSort;
    boolean cycle;

    void dfs(int v) {
        color.set(v, 1);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int way = graph.get(v).get(i);
            if (color.get(way) == 0)
                dfs(way);
            else if (color.get(way) == 1)
                cycle = true;
        }
        color.set(v, 2);
        topSort.add(v);
    }

    boolean isCycle() {
        return cycle;
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        graph = new ArrayList<ArrayList<Integer>>(n);
        color = new ArrayList<Integer>(n);
        topSort = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            color.add(0);
        }
        for (int i = 0; i < m; i++) {
            graph.get(in.nextInt() - 1).add(in.nextInt() - 1);
        }

        cycle = false;
        for (int i = 0; i < n; i++)
            if (color.get(i) == 0)
                dfs(i);
        if (!isCycle())
            for (int i = topSort.size() - 1; i >= 0; i--)
                out.print((topSort.get(i) + 1) + " ");
        else
            out.print("-1");


        out.close();
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