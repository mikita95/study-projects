import java.io.*;
import java.util.*;

public class C {
    private final static String fileName = "multiassignment";

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
        public int from, to;
        public long weight, cost, flow;
        public Edge reverse;
        public boolean returned;

        Edge(int from, int to, long weight, long cost, long flow, boolean returned) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.cost = cost;
            this.flow = flow;
            this.returned = returned;
        }

        long getSub() {
            return cost - flow;
        }
    }

    class Graph {
        ArrayList<ArrayList<Edge>> graph;
        int n;

        Graph(int n) {
            this.n = n;
            graph = new ArrayList<ArrayList<Edge>>(n);
            for (int i = 0; i < n; i++)
                graph.add(new ArrayList<Edge>());
        }

        void addEdge(int from, int to, int cost, int weight) {
            Edge edge1 = new Edge(from, to, weight, cost, 0, false);
            Edge edge2 = new Edge(to, from, -weight, cost, cost, true);
            edge1.reverse = edge2;
            edge2.reverse = edge1;
            graph.get(from).add(edge1);
            graph.get(to).add(edge2);
        }

        long getMinCst(int start, int tVert) {
            ArrayList<Edge> way;
            long result = 0;
            while (!(way = getPath(start, tVert)).isEmpty()) {
                long minimum = Long.MAX_VALUE;
                long cost = 0;
                for (Edge aPath : way) {
                    minimum = Math.min(minimum, aPath.getSub());
                    cost += aPath.weight;
                }
                result += minimum * cost;
                for (Edge aPath : way) {
                    aPath.flow += minimum;
                    aPath.reverse.flow -= minimum;
                }
            }
            return result;
        }

        Pair<Long, ArrayList<Pair<Integer, Integer>>> connective(int start, int tVert) {
            long cost = getMinCst(start, tVert);
            ArrayList<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
            for (int i = 0; i < n; i++) {
                if (i == start) {
                    continue;
                } else if (i == tVert) {
                    continue;
                }
                for (int k = 0; k < graph.get(i).size(); k++) {
                    Edge edge = graph.get(i).get(k);
                    if (edge.to == start) continue;
                    else if (!(edge.to != tVert && edge.getSub() <= 0 && !edge.returned))
                        continue;
                    result.add(new Pair<Integer, Integer>(edge.from, edge.to));
                }
            }
            return new Pair<Long, ArrayList<Pair<Integer, Integer>>>(cost, result);
        }

        ArrayList<Edge> getPath(int start, int tVert) {
            ArrayList<Long> dist = new ArrayList<Long>(n);
            ArrayList<Boolean> used = new ArrayList<Boolean>(n);

            ArrayList<Edge> way = new ArrayList<Edge>(n);
            for (int i = 0; i < n; i++) {
                dist.add(inf);
                used.add(false);
                way.add(null);
            }
            dist.set(start, 0L);


            Deque<Pair<Long, Integer>> queue = new LinkedList<Pair<Long, Integer>>();
            queue.add(new Pair<Long, Integer>(0L, start));
            while (!queue.isEmpty()) {
                Pair<Long, Integer> current = queue.getFirst();
                int currentSec = current.second;
                if (currentSec == tVert)
                    break;
                queue.pop();
                if (dist.get(currentSec) < current.first)
                    continue;
                used.set(currentSec, true);
                for (int i = 0; i < graph.get(currentSec).size(); i++) {
                    Edge edge = graph.get(currentSec).get(i);
                    if (dist.get(currentSec) + edge.weight < dist.get(edge.to) && edge.getSub() > 0) {
                        dist.set(edge.to, dist.get(currentSec) + edge.weight);
                        way.set(edge.to, edge);
                        queue.add(new Pair<Long, Integer>(-dist.get(edge.to), edge.to));
                    }
                }

            }
            if (dist.get(tVert) == inf)
                return new ArrayList<Edge>();
            ArrayList<Edge> result = new ArrayList<Edge>();
            Edge currEdge = way.get(tVert);
            while (currEdge != null) {
                result.add(currEdge);
                currEdge = way.get(currEdge.from);
            }
            return result;
        }


    }

    class GraphHelp {
        int n, m;
        ArrayList<Set<Integer>> graph;
        ArrayList<Boolean> used;
        ArrayList<Integer> way;

        GraphHelp(int n, int m) {
            this.n = n;
            this.m = m;
            graph = new ArrayList<Set<Integer>>(n);
            for (int i = 0; i < n; i++)
                graph.add(new TreeSet<Integer>());
        }

        void addEdge(int a, int b) {
            graph.get(a).add(b);
        }

        ArrayList<Integer> connecting() {
            way = new ArrayList<Integer>(m);
            for (int i = 0; i < m; i++)
                way.add(-1);
            used = new ArrayList<Boolean>();
            for (int i = 0; i < n; i++)
                used.add(false);
            for (int i = 0; i < n; i++) {
                Collections.fill(used, false);
                dfs(i);
            }
            ArrayList<Integer> result = new ArrayList<Integer>(n);
            for (int i = 0; i < n; i++)
                result.add(-1);
            for (int i = 0; i < m; i++) {
                if (way.get(i) != -1)
                    result.set(way.get(i), i);
            }
            return result;
        }

        void clear(ArrayList<Integer> a) {
            for (int i = 0; i < n; i++)
                graph.get(i).remove(a.get(i));
        }

        boolean dfs(int v) {
            used.set(v, true);
            for (Integer o : graph.get(v)) {
                int destination = o;
                if (way.get(destination) == -1) {
                    way.set(destination, v);
                    return true;
                } else if (!used.get(way.get(destination)) && dfs(way.get(destination))) {
                    way.set(destination, v);
                    return true;
                }

            }
            return false;
        }
    }

    public final static long inf = 10000000L;

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int k = in.nextInt();
        Graph gr = new Graph(2 * n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gr.addEdge(i, j + n, 1, in.nextInt());
            }
            gr.addEdge(2 * n, i, k, 0);
            gr.addEdge(i + n, 2 * n + 1, k, 0);
        }
        Pair<Long, ArrayList<Pair<Integer, Integer>>> result = gr.connective(2 * n, 2 * n + 1);
        int count = result.second.size() / n;
        out.println(result.first);
        GraphHelp grHelp = new GraphHelp(n, n);
        for (int i = 0; i < result.second.size(); i++) {
            grHelp.addEdge(result.second.get(i).first, result.second.get(i).second - n);
        }

        for (int i = 0; i < count; i++) {
            ArrayList<Integer> connecting = grHelp.connecting();
            grHelp.clear(connecting);
            for (Integer aMatching : connecting) {
                out.print(String.format("%d ", aMatching + 1));
            }
            out.println();
        }

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
        new C().run();
    }


}
