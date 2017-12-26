import java.io.*;
import java.util.*;

public class B {
    private final static String fileName = "mincost";

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

    ArrayList<ArrayList<Edge>> graph;
    int n;
    int s;
    private final static long INF = Long.MAX_VALUE;
    private final static int dCalc = 0;
    private final static int inside = 1;
    private final static int ndCalc = 2;
    long maxFlow = INF;

    void init() {
        graph = new ArrayList<ArrayList<Edge>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Edge>());
        }
    }

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        n = in.nextInt();
        int m = in.nextInt();
        init();
        s = 0;
        ArrayList<Pair<Edge, Integer>> graphTemp = new ArrayList<Pair<Edge, Integer>>();
        int from, to;
        long u, c;
        for (int i = 0; i < m; i++) {
            from = in.nextInt();
            to = in.nextInt();
            u = in.nextLong();
            c = in.nextLong();
            Edge edge1 = new Edge(to - 1, u, c, 0, graph.get(to - 1).size());
            Edge edge2 = new Edge(from - 1, 0, -c, 0, graph.get(from - 1).size());
            graph.get(from - 1).add(edge1);
            graph.get(to - 1).add(edge2);
        }
        out.println(levAlg());
        out.close();
    }

    boolean checkLevit() {
        if (graph.size() > 0)
            return true;
        else {
            return graph != null && graph.size() > 0 && graph.get(0) != null && graph.get(0).get(0) != null && graph.get(0).get(0).backId == s;
        }
    }

    long levAlg() {
        long flow = 0, cost = 0;
        Deque<Integer> deq = new LinkedList<Integer>();
        int[] iLev = new int[n];
        long[] dist = new long[n];
        int[] ptV = new int[n];
        int[] ptE = new int[n];
        while (flow <= maxFlow) {
            Arrays.fill(iLev, 0);
            boolean flag = checkLevit();
            Arrays.fill(dist, INF);
            Arrays.fill(ptV, 0);
            Arrays.fill(ptE, 0);
            deq.clear();
            deq.addLast(s);
            dist[s] = 0;
            while (!deq.isEmpty()) {
                int vertex = deq.removeFirst();
                iLev[vertex] = ndCalc;
                for (int i = 0; i < graph.get(vertex).size(); i++) {
                    Edge edge = graph.get(vertex).get(i);
                    if (edge.flow < edge.getCst() && dist[vertex] + edge.getCap() < dist[edge.to]) {
                        dist[edge.to] = dist[vertex] + edge.getCap();
                        if (iLev[edge.to] == dCalc) {
                            deq.addLast(edge.to);
                        } else if (iLev[edge.to] == ndCalc) {
                            deq.addFirst(edge.to);
                        }
                        iLev[edge.to] = inside;
                        ptV[edge.to] = vertex;
                        ptE[edge.to] = i;
                    }
                }
            }
            if (dist[n - 1] == INF)
                break;
            long addFlow = maxFlow - flow;
            {
                int pV, pE, en;
                for (int v = n - 1; v != s; v = ptV[v]) {
                    pV = ptV[v];
                    pE = ptE[v];
                    addFlow = Math.min(addFlow, graph.get(pV).get(pE).cst - graph.get(pV).get(pE).flow);
                }
                for (int v = n - 1; v != s; v = ptV[v]) {
                    pV = ptV[v];
                    pE = ptE[v];
                    en = graph.get(pV).get(pE).backId;
                    graph.get(pV).get(pE).flow += addFlow;
                    graph.get(v).get(en).flow -= addFlow;
                    cost += graph.get(pV).get(pE).cap * addFlow;
                }
            }
            flow += addFlow;
        }
        return cost;
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
        new B().run();
    }


}
