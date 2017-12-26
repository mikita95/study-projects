import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
    private final static String fileName = "game";

    public static void main(String[] args) {
        new D().run();
    }

    ArrayList<ArrayList<Integer>> graph, graphTop;
    ArrayList<Boolean> used;

    ArrayList<Integer> player, deg;

    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        graphTop = new ArrayList<ArrayList<Integer>>(n);
        used = new ArrayList<Boolean>(n);
        player = new ArrayList<Integer>(n);
        deg = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            graphTop.add(new ArrayList<Integer>());
            used.add(false);
            player.add(0);
            deg.add(0);
        }

    }

    void dfs(int v) {
        used.set(v, true);
        for (int i = 0; i < graphTop.get(v).size(); i++) {
            int dist = graphTop.get(v).get(i);
            if (!used.get(dist)) {
                if (player.get(v) == 2) {
                    player.set(dist, 1);
                } else if (deg.get(dist) + 1 == graph.get(dist).size()) {
                    player.set(dist, 2);
                    deg.set(dist, deg.get(dist) + 1);
                } else
                    continue;

                dfs(dist);
            }


        }
    }

    void win(int n) {
        for (int i = 0; i < n; i++) {
            if (player.get(i) != 0 && !used.get(i)) {
                dfs(i);
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
        s--;
        int x, y;

        init(n);

        for (int i = 0; i < m; i++) {
            x = in.nextInt();
            y = in.nextInt();
            graph.get(x - 1).add(y - 1);
            graphTop.get(y - 1).add(x - 1);
        }
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 0) {
                player.set(i, 2);
            }
        }

        win(n);

        if (player.get(s) == 1)
            out.println("First player wins");
        else
            out.println("Second player wins");


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
