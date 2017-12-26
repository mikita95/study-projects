import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {
    private final static String fileName = "cycle";

    public static void main(String[] args) {
        new E().run();
    }

    PrintWriter out;

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> color, way;
    int beginCycle, endCycle;
    boolean isReady = false;
    ArrayList<Integer> ans = new ArrayList<Integer>();

    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        color = new ArrayList<Integer>(n);
        way = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            color.add(0);
            way.add(-1);
        }

    }

    void addAnswer(ArrayList<Integer> ans) {
        for (int j = endCycle; j != beginCycle; j = way.get(j)) {
            ans.add(j);
        }
        ans.add(beginCycle);
    }

    void printAnswer(ArrayList<Integer> ans) {
        out.println("YES");
        for (int j = ans.size() - 1; j >= 0; --j) {
            out.print((ans.get(j) + 1) + " ");
        }

    }

    void dfs(int v) {
        if (isReady)
            return;
        color.set(v, 1);

        for (int i = 0; i < graph.get(v).size(); ++i) {
            int dist = graph.get(v).get(i);
            if (color.get(dist) == 0) {
                way.set(dist, v);
                dfs(dist);
                if (isReady)
                    return;
            } else if (color.get(dist) == 1) {
                workWithAns(v, dist);
                return;
            }
        }

        color.set(v, 2);
    }

    void workWithAns(int x, int d) {
        endCycle = x;
        beginCycle = d;
        addAnswer(ans);
        printAnswer(ans);
        isReady = true;

    }

    boolean runDFS(int n) {
        for (int i = 0; i < n; ++i) {
            if (color.get(i) == 0) {
                dfs(i);
            }
        }
        return true;
    }


    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        out = new PrintWriter(new File(fileName + ".out"));
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        init(n);
        for (int i = 0; i < m; ++i) {
            graph.get(in.nextInt() - 1).add(in.nextInt() - 1);
        }

        boolean flag = runDFS(n);

        if (!isReady && flag)
            out.println("NO");


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
