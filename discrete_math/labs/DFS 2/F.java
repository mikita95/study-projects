import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class F {
    private final static String fileName = "biconv";
    PrintWriter out;

    ArrayList<ArrayList<Integer>> graph, id;
    ArrayList<Boolean> used;
    ArrayList<Integer> comeTo, outOf, color;
    int step;
    int cnt;

    void init(int n, int m) {
        used = new ArrayList<Boolean>();
        comeTo = new ArrayList<Integer>();
        outOf = new ArrayList<Integer>();
        color = new ArrayList<Integer>();
        graph = new ArrayList<ArrayList<Integer>>();
        id = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            id.add(new ArrayList<Integer>());
            comeTo.add(0);
            outOf.add(0);
            used.add(false);


        }

        for (int i = 0; i < m; i++)
            color.add(0);

        step = 0;
        cnt = 0;
    }

    void dfs(int v, int cG) {
        used.set(v, true);
        step++;
        comeTo.set(v, step);
        outOf.set(v, step);
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (graph.get(v).get(i) == cG) {
                continue;
            }
            if (used.get(graph.get(v).get(i))) {
                outOf.set(v, Math.min(outOf.get(v), comeTo.get(graph.get(v).get(i))));
            } else {
                dfs(graph.get(v).get(i), v);
                outOf.set(v, Math.min(outOf.get(v), outOf.get(graph.get(v).get(i))));
            }
        }
    }

    void quickSort(ArrayList<Integer> data, int l, int r) {
        if (data.size() <= 1)
            return;
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


    void doComponents(int v, int c, int cG) {
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int u = graph.get(v).get(i);
            if (u == cG) {
                continue;
            }
            if (!used.get(u)) {
                if (comeTo.get(v) <= outOf.get(u)) {
                    cnt++;
                    color.set(id.get(v).get(i), cnt);
                    doComponents(u, cnt, v);
                } else {
                    color.set(id.get(v).get(i), c);
                    doComponents(u, c, v);
                }
            } else if (comeTo.get(v) >= comeTo.get(u)) {
                color.set(id.get(v).get(i), c);
            }
        }
    }

    void runDFS(int n) {
        for (int i = 0; i < n; i++)
            if (!used.get(i))
                dfs(i, -1);

    }

    void makeColor(int n) {
        makeUsedFalse();
        for (int i = 0; i < n; i++)
            if (!used.get(i))
                doComponents(i, -1, -1);

    }

    void makeUsedFalse() {
        for (int i = 0; i < used.size(); i++) {
            used.set(i, false);
        }

    }

    public static void main(String[] args) {
        new F().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n, m);
        int x, y;
        for (int i = 0; i < m; i++) {
            x = in.nextInt() - 1;
            y = in.nextInt() - 1;
            graph.get(x).add(y);
            id.get(x).add(i);
            graph.get(y).add(x);
            id.get(y).add(i);
        }
        runDFS(n);
        makeColor(n);
        out.println(cnt);
        //quickSort(color, 0, color.size() - 1);
        for (int i = 0; i < m; i++)
            out.print(color.get(i) + " ");


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