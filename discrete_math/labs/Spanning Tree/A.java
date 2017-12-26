import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    private final static String fileName = "spantree";
    FastScanner in;
    private final static long INF = 1000000001;
    ArrayList<Pair<Integer, Integer>> graph;
    ArrayList<Long> minDist;
    ArrayList<Boolean> used;

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

    public static void main(String[] args) {
        new A().run();
    }

    long distance(long x, long y) {
        long result = x * x + y * y;
        if (result < 0.5)
            result = INF;
        return result;
    }

    void initialization(int n) {
        graph = new ArrayList<Pair<Integer, Integer>>();
        minDist = new ArrayList<Long>();
        used = new ArrayList<Boolean>();
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            graph.add(new Pair<Integer, Integer>(x, y));
            used.add(false);
            minDist.add(INF);
        }
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        initialization(n);
        minDist.set(0, 0l);
        double weight = 0;
        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!used.get(j) && (v == -1 || minDist.get(j) < minDist.get(v))) {
                    v = j;
                }
            }
            used.set(v, true);
            int j = 0;
            while (j < n) {
                long dist = distance(graph.get(v).first - graph.get(j).first, graph.get(v).second - graph.get(j).second);
                if (dist < minDist.get(j) && !used.get(j)) {
                    minDist.set(j, dist);
                }
                j++;
            }
        }
        int i = 0;
        while (i < n) {
            weight += Math.sqrt(minDist.get(i));
            i++;
        }
        out.print(weight);
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