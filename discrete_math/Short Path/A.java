import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class A {
    private final static String fileName = "pathbge1";
    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> dist;
    ArrayList<Boolean> used;

    public static void main(String[] args) {
        new A().run();
    }

    void init(int n) {
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            dist.add(0);
            used.add(false);
        }
    }

    void dfs() {
        Queue<Integer> vertix = new LinkedList<Integer>();
        vertix.add(0);
        used.set(0, true);
        while (true) {
            if (vertix.isEmpty()) break;
            int v = vertix.peek();
            vertix.remove(v);
            ArrayList<Integer> get = graph.get(v);
            for (int i = 0; i < get.size(); i++) {
                int u = get.get(i);
                if (used.get(u)) {
                    continue;
                }
                used.set(u, true);
                dist.set(u, dist.get(v) + 1);
                vertix.add(u);

            }
        }
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt(), m = in.nextInt();
        graph = new ArrayList<ArrayList<Integer>>();
        dist = new ArrayList<Integer>();
        used = new ArrayList<Boolean>();
        init(n);
        int x, y;
        int k = 0;
        while (k < m) {
            x = in.nextInt();
            y = in.nextInt();
            graph.get(x - 1).add(y - 1);
            graph.get(y - 1).add(x - 1);
            k++;
        }

        dfs();
        for (int i = 0; i < n; i++)
            out.print(dist.get(i) + " ");

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