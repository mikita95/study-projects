import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {
    private final static String fileName = "path";
    ArrayList<Edge> edges;
    ArrayList<Long> dist;
    private final static long INF = (long) 3e18;
    class Pair<S, T> implements Comparable<Pair<S, T>> {
        final S first;
        final T second;
        final boolean e1Comparable;
        final boolean e2Comparable;

        Pair(final S first, final T second) {
            this.first = first;
            this.second = second;
            this.e1Comparable = (first instanceof Comparable);
            this.e2Comparable = (second instanceof Comparable);
        }

        public int compareTo(Pair<S, T> o) {
            if (e1Comparable) {
                final int k = ((Comparable<S>) first).compareTo(o.first);
                if (k > 0) return 1;
                if (k < 0) return -1;
            }

            if (e2Comparable) {
                final int k = ((Comparable<T>) second).compareTo(o.second);
                if (k > 0) return 1;
                if (k < 0) return -1;
            }

            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                final Pair<S, T> o = (Pair<S, T>) obj;
                return (first.equals(o.first) && second.equals(o.second));
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 19 * hash + (first != null ? first.hashCode() : 0);
            hash = 19 * hash + (second != null ? second.hashCode() : 0);
            return hash;
        }
    }
    class Edge {
        int x;
        int y;
        long weight;

        Edge(int x, int y, long weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }

    void init(int n) {
        edges = new ArrayList<Edge>();
        dist = new ArrayList<Long>();
        for (int i = 0; i < n; i++)
            dist.add(INF);
    }

    void findPath(int m, int n) {
        int i = 0;
        while (true) {
            boolean flag = false;
            int j = 0;
            while (j < m) {
                if (dist.get(edges.get(j).y) > -INF && dist.get(edges.get(j).x) < INF && dist.get(edges.get(j).y) > (dist.get(edges.get(j).x) + edges.get(j).weight)) {
                    flag = true;
                    if (n < i)
                        dist.set(edges.get(j).y, -INF);
                    else {
                        change(j);
                    }
                }
                j++;
            }
            if (!flag)
                break;
            i++;
        }
    }

    public static void main(String[] args) {
        new E().run();
    }

    void change(int i) {
        dist.set(edges.get(i).y, edges.get(i).weight + dist.get(edges.get(i).x));
        if (dist.get(edges.get(i).y) > INF)
            dist.set(edges.get(i).y, INF);
        if (dist.get(edges.get(i).y) < -INF)
            dist.set(edges.get(i).y, -INF);
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt() - 1;
        init(n);
        for (int i = 0; i < m; i++) {
            edges.add(new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextLong()));
        }
        dist.set(s, 0L);
        findPath(m, n);
        for (int i = 0; i < n; i++) {
            if (dist.get(i) == -INF)
                out.println('-');
            else if (dist.get(i) == INF)
                out.println('*');
            else
                out.println(dist.get(i));
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