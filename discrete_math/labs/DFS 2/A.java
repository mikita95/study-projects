import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    private final static String fileName = "components";

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> color;
    ArrayList<Boolean> used;

    void init(int n) {
        color = new ArrayList<Integer>(n);
        used = new ArrayList<Boolean>(n);
        graph = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            color.add(0);
            used.add(false);
        }
    }

    void dfs(int v, int clr) {
        used.set(v, true);
        color.set(v, clr);
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used.get(graph.get(v).get(i))) {
                dfs(graph.get(v).get(i), clr);
            }
        }
    }

    int countComponents(int n) {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (!used.get(i)) {
                cnt++;
                dfs(i, cnt);
            }
        }
        return cnt;

    }

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);
        int x, y;
        for (int i = 0; i < m; i++) {
            x = in.nextInt() - 1;
            y = in.nextInt() - 1;
            graph.get(x).add(y);
            graph.get(y).add(x);
        }
        out.println(countComponents(n));
        for (int i = 0; i < n; i++) {
            out.print(color.get(i) + " ");
        }

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