import java.io.*;
import java.util.StringTokenizer;

public class C {
    private final static String fileName = "shortpath";

    public static void main(String[] args) {
        new C().run();
    }

    int[] start, destination, weight, degree, topSort, dArray;
    boolean[] used;
    int[][][] graph;
    int x = 0;
    int inf = (int) 1e9 + 30;

    void initialization(int n, int m) {
        start = new int[m];
        destination = new int[m];
        weight = new int[m];
        degree = new int[n];
        used = new boolean[n];
        graph = new int[n][2][];
        topSort = new int[n];
        dArray = new int[n];
    }

    void topSort(int v) {
        used[v] = true;
        if (graph[v][0] != null) {
            for (int i : graph[v][0]) {
                if (used[i] == false)
                    topSort(i);
            }
        }
        topSort[x++] = v;
    }

    void findPath(int n, int s) {
        dArray[s] = 0;
        for (int i = 0; i < n; i++) {
            if (graph[i][0] != null) {
                for (int j = 0; j < graph[topSort[i]][0].length; j++)
                    dArray[graph[topSort[i]][0][j]] = Math.min(dArray[graph[topSort[i]][0][j]], graph[topSort[i]][1][j] + dArray[topSort[i]]);
            }
        }
    }


    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();
        s--;
        t--;
        initialization(n, m);
        for (int i = 0; i < n; i++) {
            degree[i] = 0;
            used[i] = false;
            dArray[i] = inf;
        }
        for (int i = 0; i < m; i++) {
            start[i] = in.nextInt() - 1;
            destination[i] = in.nextInt() - 1;
            weight[i] = in.nextInt();
            degree[start[i]]++;
        }
        for (int i = 0; i < n; i++) {
            graph[i][0] = new int[degree[i]];
            graph[i][1] = new int[degree[i]];
        }
        for (int i = 0; i < m; i++) {
            graph[start[i]][0][--degree[start[i]]] = destination[i];
            graph[start[i]][1][degree[start[i]]] = weight[i];
        }
        if (s == t) {
            out.println(0);
        } else {
            topSort(s);
            int tmp;
            for (int i = 0; i < n / 2; i++) {
                tmp = topSort[i];
                topSort[i] = topSort[n - i - 1];
                topSort[n - i - 1] = tmp;
            }
            findPath(n, s);

            if (dArray[t] == inf)
                out.println("Unreachable");
            else
                out.println(dArray[t]);
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
