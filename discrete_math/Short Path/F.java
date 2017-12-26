import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class F {
    private final static String fileName = "negcycle";

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

    ArrayList<Edge> edges;
    ArrayList<Integer> path;
    final int max = 10001, INF = (int) 2e8;
    int distance[], parents[];

    class Edge {
        final int x;
        final int y;
        final int weight;
        public Edge(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        new F().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        edges = new ArrayList<Edge>();
        int x = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x = in.nextInt();
                if (Math.abs(x) < max) edges.add(new Edge(i, j, x));
            }
        }
        distance = new int[n];
        parents = new int[n];
        Arrays.fill(parents, -1);
        int size = edges.size();

        for (int i = 0; i < n; i++) {
            x = -1;
            for (int j = 0; j < size; j++) {
                if (distance[edges.get(j).y] > distance[edges.get(j).x] + edges.get(j).weight) {
                    distance[edges.get(j).y] = Math.max(-INF, distance[edges.get(j).x] + edges.get(j).weight);
                    parents[edges.get(j).y] = edges.get(j).x;
                    x = edges.get(j).y;
                }
            }
        }
        if (x == -1) {
            out.println("NO");
            return;
        }
        int y = x;
        for (int i = 0; i < n; i++) y = parents[y];
        path = new ArrayList<Integer>();
        for (int current = y; ; current = parents[current]) {
            path.add(current);
            if (current == y && path.size() > 1) break;
        }
        Collections.reverse(path);
        out.println("YES");
        out.println(path.size());
        for (int u : path) out.print(u + 1 + " ");
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