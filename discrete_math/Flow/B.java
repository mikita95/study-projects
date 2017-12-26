import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class B {
    private final static String fileName = "maxflow";
    int INF = (int) 1e9;
    ArrayList<Integer> edges1, edges2;
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<ArrayList<Integer>> graph;
    int n, m, s;
    class Edge {
        int from, to, cap, flowSize;
        public Edge(int from, int to, int cap, int flowSize) {
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.flowSize = flowSize;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getCap() {
            return cap;
        }

        public void setCap(int cap) {
            this.cap = cap;
        }

        public int getFlowSize() {
            return flowSize;
        }

        public void setFlowSize(int flowSize) {
            this.flowSize = flowSize;
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

    boolean bfs() {
        ArrayDeque<Integer> deq = new ArrayDeque<Integer>();
        deq.clear();
        deq.offer(s);
        Collections.fill(edges2, -1);
        edges2.set(s, 0);
        while (true) {
            if (!(!deq.isEmpty() && edges2.get(n - 1) == -1))
                break;
            int v;
            v = deq.poll();
            for (int i = 0; i < graph.get(v).size(); i++) {
                if (edges2.get(edges.get(graph.get(v).get(i)).getTo()) == -1) {
                    if (edges.get(graph.get(v).get(i)).getFlowSize() < edges.get(graph.get(v).get(i)).getCap()) {
                        deq.offer(edges.get(graph.get(v).get(i)).getTo());
                        edges2.set(edges.get(graph.get(v).get(i)).getTo(), edges2.get(v) + 1);
                    }
                }
            }

        }
        return edges2.get(n - 1) != -1 ? true : false;
    }

    int dfs(int v, int flowS) {
        if (flowS == 0)
            return 0;
        if (n == v + 1) {
            return flowS;
        }
        for (; edges1.get(v) < graph.get(v).size(); edges1.set(v, edges1.get(v) + 1)) {
            if (edges2.get(edges.get(graph.get(v).get(edges1.get(v))).to) != edges2.get(v) + 1)
                continue;
            int tmp = dfs(edges.get(graph.get(v).get(edges1.get(v))).getTo(), Math.min(flowS, edges.get(graph.get(v).get(edges1.get(v))).getCap() - edges.get(graph.get(v).get(edges1.get(v))).getFlowSize()));
            if (tmp != 0) {
                edges.get(graph.get(v).get(edges1.get(v))).setFlowSize(edges.get(graph.get(v).get(edges1.get(v))).getFlowSize() + tmp);
                edges.get(graph.get(v).get(edges1.get(v)) ^ 1).setFlowSize(edges.get(graph.get(v).get(edges1.get(v)) ^ 1).flowSize - tmp);
                return tmp;
            }
        }
        return 0;
    }

    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        edges1 = new ArrayList<Integer>(n);
        edges2 = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            edges1.add(0);
            edges2.add(0);
        }
    }

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        n = in.nextInt();
        m = in.nextInt();
        s = 0;
        init(n);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            graph.get(a - 1).add(edges.size());
            edges.add(new Edge(a - 1, b - 1, c, 0));
            graph.get(b - 1).add(edges.size());
            edges.add(new Edge(b - 1, a - 1, 0, 0));
        }
        int fl = 0;
        int tmp;
        while (true) {
            if (!bfs())
                break;
            Collections.fill(edges1, 0);
            tmp = dfs(s, INF);
            while (true) {
                if (tmp == 0)
                    break;
                fl = fl + tmp;
                tmp = dfs(s, INF);
            }
        }
        out.print(fl);
        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] arg) {
        new B().run();
    }
}
