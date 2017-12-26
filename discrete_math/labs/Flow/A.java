import java.io.*;
import java.util.*;

public class A {
    private final static String fileName = "matching";
    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Boolean> used;
    ArrayList<Integer> set;
    private final static int inf = -100;

    void initialization(int n, int m) {
        set = new ArrayList<Integer>(n + m);
        graph = new ArrayList<ArrayList<Integer>>(n + m);
        used = new ArrayList<Boolean>(n);
        for (int i = 0; i < n + m; i++) {
            graph.add(new ArrayList<Integer>());
            set.add(inf);
        }
        for (int i = 0; i < n; i++)
            used.add(false);
    }

    boolean dfs(int v) {
        if (used.get(v))
            return false;
        used.set(v, true);
        for (int i = 0, s = graph.get(v).size(); i < s; i++) {
            if (set.get(graph.get(v).get(i)) == inf) {
                set.set(graph.get(v).get(i), v);
                return true;
            } else if (dfs(set.get(graph.get(v).get(i)))) {
                set.set(graph.get(v).get(i), v);
                return true;
            }
        }
        return false;
    }

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        initialization(n, m);
        for (int i = 0; i < k; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            graph.get(x - 1).add(y - 1 + n);
            graph.get(y - 1 + n).add(x - 1);
        }
        for (int i = 0; i < n; i++) {
            for (int t = 0; t < used.size(); t++)
                used.set(t, false);
            dfs(i);
        }
        int answer = 0;
        for (int i = 0; i < n + m; i++) {
            if (set.get(i) != inf)
                answer++;
        }
        out.print(answer);
        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void main(String[] arg) {
        new A().run();
    }
}
