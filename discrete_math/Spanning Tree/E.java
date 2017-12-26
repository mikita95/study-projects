import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class E {
    private final static String fileName = "chinese";
    FastScanner in;

    class Pair<S, T> implements Comparable<Pair<S, T>> {
        S first;
        T second;
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
        int start, end;
        long weight;

        public Edge(int start, int end, long weight) {
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

    public static void main(String[] args) {
        new E().run();
    }

    private final static long INF = (long) 1e18 + 1;

    ArrayList<Integer> order;
    ArrayList<ArrayList<Pair<Integer, Integer>>> g_transpose = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    ArrayList<Boolean> used;
    Edge[] edge;
    Map<Integer, Integer> edge_minimum = new TreeMap<Integer, Integer>();
    int cnt_order, nVertix, nEdge;
    long[] distance, p, merge, n, vertix;


    void dfs(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int v) {
        used.set(v, true);
        int to;
        for (int i = 0; i < graph.get(v).size(); i++) {
            to = graph.get(v).get(i).first;
            if (!used.get(to))
                dfs(graph, to);
        }
    }

    void dfsZeroEdged(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int v, ArrayList<Long> forMin) {
        used.set(v, true);
        int to, w;
        for (int i = 0; i < graph.get(v).size(); i++) {
            to = graph.get(v).get(i).first;
            w = graph.get(v).get(i).second;
            if (!used.get(to) && w == forMin.get(to)) {
                dfsZeroEdged(graph, to, forMin);
            }
        }
        order.set(cnt_order++, v);
    }

    void dfsOrder(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int v, ArrayList<Long> forMin) {
        used.set(v, true);
        int to, w;
        for (int i = 0; i < graph.get(v).size(); i++) {
            to = graph.get(v).get(i).first;
            w = graph.get(v).get(i).second;
            if (!used.get(to) && w == forMin.get(to)) {
                dfsOrder(graph, to, forMin);
            }
        }
        order.set(cnt_order++, v);
    }

    void dfsComponent(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int v, ArrayList<Integer> colors, int color, ArrayList<Long> forMin) {
        used.set(v, true);
        colors.set(v, color);
        int to, w;
        for (int i = 0; i < graph.get(v).size(); i++) {
            to = graph.get(v).get(i).first;
            w = graph.get(v).get(i).second;
            if (!used.get(to) && w == forMin.get(to)) {
                dfsComponent(graph, to, colors, color, forMin);
            }
        }
    }

    void edgesToZero(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int startV, ArrayList<Long> forMin) {
        int n = graph.size();
        int from, to, w;
        for (int i = 0; i < n; i++) {
            g_transpose.add(new ArrayList<Pair<Integer, Integer>>());
            forMin.add(INF);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                from = i;
                to = graph.get(i).get(j).first;
                w = graph.get(i).get(j).second;
                g_transpose.get(to).add(new Pair<Integer, Integer>(from, w));
                forMin.set(to, Math.min(forMin.get(to), w));
            }
        }
    }

    long ans2;

    boolean findMst(ArrayList<ArrayList<Pair<Integer, Integer>>> graph, int startV) {
        int n = graph.size();
        ArrayList<Long> minV = new ArrayList<Long>(n);
        for (int i = 0; i < n; i++) {
            minV.add(0l);
        }
        edgesToZero(graph, startV, minV);
        cnt_order = 0;
        used.clear();
        for (ArrayList<Pair<Integer, Integer>> aGraph : graph) used.add(false);
        dfsZeroEdged(graph, startV, minV);
        boolean ok = true;
        for (int i = 0; i < n; i++) {
            if (i != startV) {
                ans2 += minV.get(i);
            }
            if (!used.get(i))
                ok = false;
        }
        if (ok)
            return true;

        for (int i = 0; i < n; i++) {
            if (!used.get(i))
                dfsOrder(graph, i, minV);
        }

        used.clear();
        ArrayList<Integer> colors = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            used.add(false);
            colors.add(0);
        }

        int countOfComponent = 0;
        for (int i = 0; i < n; i++) {
            int v = order.get(n - 1 - i);
            if (!used.get(v)) {
                dfsComponent(g_transpose, v, colors, countOfComponent, minV);
                ++countOfComponent;
            }
        }

        ArrayList<ArrayList<Pair<Integer, Integer>>> zeroComponentGraph = new ArrayList<ArrayList<Pair<Integer, Integer>>>(countOfComponent);
        for (int i = 0; i < countOfComponent; i++) {
            zeroComponentGraph.add(new ArrayList<Pair<Integer, Integer>>());
        }

        int compWithSrartV = colors.get(startV);
        int from, to, w, x, y;
        Pair<Integer, Integer> p;
        edge_minimum.clear();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                p = graph.get(i).get(j);
               // p.second -=  minV.get(p.first);
                from = i;
                to = p.first;
                w = p.second;
                x = colors.get(from);
                y = colors.get(to);
                if (x != y) {
                    int edgeHash = x * countOfComponent + y;
                    if (!edge_minimum.containsKey(edgeHash)) {
                        edge_minimum.put(edgeHash, w);
                    } else {
                        if (w < edge_minimum.get(edgeHash)) {
                            edge_minimum.remove(edgeHash);
                            edge_minimum.put(edgeHash, w);
                        }
                    }
                }
            }
        }

        int hash;
        for (Map.Entry entry : edge_minimum.entrySet()) {
            Integer key = (Integer) entry.getKey();
            Integer value = (Integer) entry.getValue();
            hash = key;
            x = hash / countOfComponent;
            y = hash - x * countOfComponent;
            zeroComponentGraph.get(x).add(new Pair<Integer, Integer>(y, value));
        }

        minV.clear();
        for (int i = 0; i < n; i++) {
            graph.get(i).clear();
        }
        findMst(zeroComponentGraph, compWithSrartV);
        return true;
    }

    long mst(int result) {
        int i = 0;
        while (i <= nVertix) {
            merge[i] = 0;
            ++i;
        }
        long weightForFirst, w2 = 0;
        while (true) {
            for (i = 0; i <= nVertix; ++i) {
                distance[i] = INF;
                p[i] = -1;
            }

            i = 0;
            while (i < nEdge) {
                int from = edge[i].start;
                int to = edge[i].end;
                long weight = edge[i].weight;
                if (from != to && to != result && weight < distance[to]) {
                    distance[to] = weight;
                    p[to] = from;
                }
                ++i;
            }
            i = 0;
            while (i <= nVertix) {
                vertix[i] = -1;
                n[i] = -1;
                ++i;
            }
            weightForFirst = 0;
            boolean flag = false;
            for (i = 0; i < nVertix; ++i) {
                if (merge[i] > 0) continue;
                if (p[i] == -1 && i != result) return INF;
                if (p[i] >= 0) weightForFirst += distance[i];

                int s;
                s = i;
                while (s != -1 && vertix[s] == -1) {
                    vertix[s] = i;
                    s = (int) p[s];
                }

                if (s != -1) {
                    if (vertix[s] == i) {
                        flag = true;
                        int j = s;
                        do {
                            n[j] = s;
                            merge[j] = 1;
                            w2 += distance[j];
                            j = (int) p[j];
                        } while (j != s);
                        merge[s] = 0;
                    }
                }
            }
            if (!flag) break;
            i = 0;
            while (i < nEdge) {
                if (n[edge[i].end] >= 0)
                    edge[i].weight -= distance[edge[i].end];
                if (n[edge[i].start] >= 0)
                    edge[i].start = (int) n[edge[i].start];
                if (n[edge[i].end] >= 0)
                    edge[i].end = (int) n[edge[i].end];
                if (edge[i].end == edge[i].start)
                    edge[i--] = edge[--nEdge];
                ++i;
            }
        }
        return weightForFirst + w2;
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        nVertix = in.nextInt();
        nEdge = in.nextInt();
        edge = new Edge[nEdge + 1];
        distance = new long[nVertix + 1];
        p = new long[nVertix + 1];
        vertix = new long[nVertix + 1];
        merge = new long[nVertix + 1];
        n = new long[nVertix + 1];
        for (int i = 0; i < nEdge; i++) {
            edge[i] = new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextLong());
        }
        long ans = mst(0);
        if (ans >= INF)
            out.print("NO");
        else {
            out.println("YES");
            out.print(ans);
        }
        if (nVertix < 0) {
            int n = 0;
            int m = 0;
            ans2 = 0;
            ArrayList<ArrayList<Pair<Integer, Integer>>> g = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
            order = new ArrayList<Integer>();
            used = new ArrayList<Boolean>();
            for (int i = 0; i < n; i++) {
                order.add(0);
                g.add(new ArrayList<Pair<Integer, Integer>>());
                used.add(false);
            }
            int from, to, w;
            for (int i = 0; i < m; i++) {
                from = in.nextInt() - 1;
                to = in.nextInt() - 1;
                w = in.nextInt();
                g.get(from).add(new Pair<Integer, Integer>(to, w));
            }

            dfs(g, 0);
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (!used.get(i)) {
                    ok = false;
                    out.print("NO");
                    out.close();
                    return;
                }
            }
            findMst(g, 0);
            out.println("YES");
            out.print(ans2);
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