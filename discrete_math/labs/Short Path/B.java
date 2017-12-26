import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    private final static String fileName = "pathmgep";

    ArrayList<ArrayList<Pair>> graph;
    ArrayList<Integer> dist;
    ArrayList<Boolean> used;
    final private static int inf = (int) Integer.MAX_VALUE;

    void init(int n) {
        graph = new ArrayList<ArrayList<Pair>>();
        used = new ArrayList<Boolean>();
        dist = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Pair>());
            used.add(false);
            dist.add(inf);
        }
    }

    void findPath(int start, int n) {
        dist.set(start, 0);
        for (int i = 0; i < n; i++) {
            int vertix = -1;
            int k = vertix + 1;
            while (k < n) {
                if ((vertix == -1 || dist.get(k) < dist.get(vertix)))
                    if (!used.get(k)) {
                        vertix = k;
                    }
                k++;
            }
            if (vertix == -1 || dist.get(vertix) == inf) {
                break;
            }
            used.set(vertix, true);
            for (int i1 = 0, getSize = graph.get(vertix).size(); i1 < getSize; i1++) {
                if (dist.get(graph.get(vertix).get(i1).first) > dist.get(vertix) + graph.get(vertix).get(i1).second) {
                    dist.set(graph.get(vertix).get(i1).first, dist.get(vertix) + graph.get(vertix).get(i1).second);
                }
            }
        }

    }

    public static void main(String[] args) {
        new B().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int s = in.nextInt() - 1;
        int t = in.nextInt() - 1;

        init(n);
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                int x = in.nextInt();
                if (x > 0) {
                    graph.get(i).add(new Pair(k, x));
                }
            }
        }
        if (t == s) {
            out.print("0");
            out.close();
            return;
        }

        findPath(s, n);

        if (dist.get(t) == inf)
            out.print(-1);
        else
            out.print(dist.get(t));
        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException ignored) {
        }
    }

    class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
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