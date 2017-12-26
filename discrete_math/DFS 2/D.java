import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
    private final static String fileName = "bipartite";

    void quickSort(ArrayList<Integer> data, int l, int r) {
        int i = l;
        int j = r;
        int x = data.get(l + (int) (Math.random() * ((r - l) + 1)));
        while (i < j) {
            while (data.get(i) < x)
                i++;
            while (data.get(j) > x)
                j--;
            if (i <= j) {
                int c = data.get(i);
                data.set(i, data.get(j));
                data.set(j, c);
                i++;
                j--;
            }
        }
        if (i < r)
            quickSort(data, i, r);
        if (l < j)
            quickSort(data, l, j);
    }

    PrintWriter out;
    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> color;
    ArrayList<Boolean> used;
    static private final int c = 3;

    boolean dfs(int v, int cG) {
        used.set(v, true);
        color.set(v, cG);
        boolean isBipartite = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used.get(graph.get(v).get(i))) {
                if (isBipartite & dfs(graph.get(v).get(i), c - cG))
                    isBipartite = true;
                else
                    isBipartite = false;
            } else {
                if (isBipartite & c == (color.get(graph.get(v).get(i)) + cG)) {
                    isBipartite = true;
                } else
                    isBipartite = false;
            }
        }
        return isBipartite;
    }

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

    void makeAnswer(int n) {
        for (int i = 0; i < n; i++) {
            if (used.get(i)) {
                continue;
            }
            if (!dfs(i, 1)) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }

    public static void main(String[] args) {
        new D().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);
        int x, y;
        int i = 0;
        while (i < m) {
            x = in.nextInt() - 1;
            y = in.nextInt() - 1;
            graph.get(x).add(y);
            graph.get(y).add(x);
            i++;
        }
        makeAnswer(n);
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