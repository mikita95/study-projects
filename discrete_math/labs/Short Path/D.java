import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class D {
    private final static String fileName = "pathbgep";
    ArrayList<ArrayList<Pair<Integer, Integer>>> graph;
    TreeSet<Pair<Integer, Integer>> set;
    ArrayList<Integer> dist;
    int INF = Integer.MAX_VALUE;

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

    void init(int n) {
        graph = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
        dist = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Pair<Integer, Integer>>());
            dist.add(INF);
        }
    }

    int count = 0;

    void dijkstra() {
        set = new TreeSet<Pair<Integer, Integer>>();
        dist.set(0, 0);
        set.add(new Pair<Integer, Integer>(0, 0));
        while (true) {
            if (set.isEmpty()) break;
            int v = set.first().second;
            set.remove(set.first());
            for (int i = 0; i < graph.get(v).size(); i++) {
                if (dist.get(graph.get(v).get(i).first) > (dist.get(v) + graph.get(v).get(i).second)) {
                    count++;
                    set.remove(new Pair<Integer, Integer>(dist.get(graph.get(v).get(i).first), graph.get(v).get(i).first));
                    dist.set(graph.get(v).get(i).first, dist.get(v) + graph.get(v).get(i).second);
                    set.add(new Pair<Integer, Integer>(dist.get(graph.get(v).get(i).first), graph.get(v).get(i).first));
                }
            }
        }
    }

    public static void main(String[] args) {
        new D().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int weight = in.nextInt();
            graph.get(x - 1).add(new Pair<Integer, Integer>(y - 1, weight));
            // graph.get(y - 1).add(new Pair<Integer, Integer>(x - 1, weight));
        }
        dijkstra();
        //  for (int i = 0; i < n; i++)
        //    out.print(String.format("%d ", dist.get(i)));
        out.print(count);
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