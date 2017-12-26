import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class D {
    private final static String fileName = "minimax";

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
        public int to;
        public long cst, cap, flow;
        public int backId;

        public Edge(int to, long cst, long cap, long flow, int backId) {
            this.to = to;
            this.cst = cst;
            this.cap = cap;
            this.flow = flow;
            this.backId = backId;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public long getCst() {
            return cst;
        }

        public void setCst(long cst) {
            this.cst = cst;
        }

        public long getCap() {
            return cap;
        }

        public void setCap(long cap) {
            this.cap = cap;
        }

        public long getFlow() {
            return flow;
        }

        public void setFlow(long flow) {
            this.flow = flow;
        }

        public int getBackId() {
            return backId;
        }

        public void setBackId(int backId) {
            this.backId = backId;
        }
    }

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> cnt, weights;
    ArrayList<Boolean> used;
    boolean bfs(int p) {
        for (int i = 0; i < n; i++)
            bfs(graph.get(p).get(i));
        return true;
    }
    public final static int inf = (int) 1e9;
    int n;

    boolean dfs(int v, int m) {
        if (used.get(v))
            return false;
        used.set(v, true);
        for (int i = 0; i < n; i++) {
            if (m > graph.get(v).get(i))
                continue;
            if (cnt.get(i) == -1) {
                cnt.set(i, v);
                return true;
            } else if (dfs(cnt.get(i), m)) {
                cnt.set(i, v);
                return true;
            }
        }
        return false;
    }

    int binSearch() {
        int a = 0;
        int b = weights.size() - 1;
        int k;
        int result = inf;
        while (true) {
            if (!(a < (b - 1)))
                break;
            int count = 0;
            k = (a + b) >> 1;
            Collections.fill(cnt, -1);
            for (int i = 0; i < n; i++) {
                Collections.fill(used, false);
                if (dfs(i, weights.get(k)))
                    count++;
            }
            if (count == n) {
                a = k;
                result = inf;
                for (int i = 0; i < n; i++)
                    if (cnt.get(i) != -1)
                        if (result > graph.get(cnt.get(i)).get(i))
                            result = graph.get(cnt.get(i)).get(i);

            } else {
                b = k;
            }
        }
        return result;
    }

    void init() {
        graph = new ArrayList<ArrayList<Integer>>();
        weights = new ArrayList<Integer>();
        cnt = new ArrayList<Integer>();
        used = new ArrayList<Boolean>();
        for (int i = 0; i < n * n; i++)
            weights.add(0);

    }

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        n = in.nextInt();
        init();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            cnt.add(0);
            used.add(false);
            for (int j = 0; j < n; j++) {
                graph.get(i).add(in.nextInt());
                weights.set(j + (n * i), graph.get(i).get(j));
            }
        }
        Collections.sort(weights);
        out.print(binSearch());
        out.close();
    }


    public void run() {
        try {
            solve();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void main(String[] arg) {
        new D().run();
    }


}
