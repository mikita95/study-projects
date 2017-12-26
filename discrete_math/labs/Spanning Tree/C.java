import java.io.*;
import java.util.*;

public class C {
    private final static String fileName = "mindiff";
    FastScanner in;


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

    class Edge implements Comparable<Edge> {
        int start, end, weight;
        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
        public int compareTo(Edge edge) {
            if (weight < edge.weight)
                return -1;
            if (weight > edge.weight)
                return 1;
            return 0;
        }
    }

    ArrayList<Edge> edges;
    int[] relative;
    Random rand = new Random();
    private final static int INF = (int) 2e9 + 1;

    int get(int v) {
        if (v == relative[v])
            return v;
        else
            return (relative[v] = get(relative[v]));
    }

    void unite(int f, int t) {
        f = get(f);
        t = get(t);
        if (rand.nextBoolean()) {
            int tmp = f;
            f = t;
            t = tmp;
        }
        if (f != t)
            relative[f] = t;
    }



    public static void main(String[] args) {
        new C().run();
    }

    void initialization(int n, int m) {
        edges = new ArrayList<Edge>(m);
        relative = new int[n];
        for (int i = 0; i < m; i++) {
            edges.add(new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt()));
        }
    }

    void makeSet(int n) {
        for (int i = 0; i < n; i++) {
            relative[i] = i;
        }

    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int minimum = INF;
        if (n == 1) {
            out.println("YES");
            out.println("0");
            return;
        }
        initialization(n, m);
        Collections.sort(edges);
        for (int j = 0; j < edges.size(); j++) {
            int cnt = 0;
            int weight = 0;
            int weight1 = 0;
            makeSet(n);
            int i = j;
            while (i < edges.size()) {
                if (get(edges.get(i).start) != get(edges.get(i).end)) {
                    unite(edges.get(i).start, edges.get(i).end);
                    cnt++;
                    if (cnt == 1) {
                        weight1 = edges.get(i).weight;
                    }
                    if (cnt == n - 1) {
                        weight = edges.get(i).weight;
                    }
                }
                i++;
            }
            if (cnt == n - 1) {
                minimum = Math.min(minimum, weight - weight1);
            }
        }
        if (minimum == INF) {
            out.print("NO");
        } else {
            out.println("YES");
            out.print(minimum);
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