import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class D {
    private final static String fileName = "euler";
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

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> degrees;

    public static void main(String[] args) {
        new D().run();
    }

    void initialization(int n) {
        graph = new ArrayList<ArrayList<Integer>>();
        degrees = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            for (int j = 0; j < n; j++)
                graph.get(i).add(0);
        }
        for (int i = 0; i < n; i++) {
            degrees.add(in.nextInt());
            for (int j = 0; j < degrees.get(i); j++) {
                int t = in.nextInt();
                graph.get(i).set(t - 1, graph.get(i).get(t - 1) + 1);
            }
        }
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        initialization(n);
        int start = 0;
        ArrayList<Pair<Edge, Integer>> pathTmp = new ArrayList<Pair<Edge, Integer>>();
        while (true) {
            if (!(degrees.get(start) == 0)) break;
            start++;
        }
        int vertix1 = -1;
        int vertix2 = -1;
        boolean check = false;
        for (int i = 0; i < n; i++) {
            if ((degrees.get(i) % 2) != 0) {
                if (vertix1 == -1)
                    vertix1 = i;
                else if (vertix2 == -1)
                    vertix2 = i;
                else
                    check = true;
            }
        }
        if (vertix1 != -1) {
            graph.get(vertix1).set(vertix2, graph.get(vertix1).get(vertix2) + 1);
            graph.get(vertix2).set(vertix1, graph.get(vertix2).get(vertix1) + 1);
        }
        Stack<Integer> st = new Stack<Integer>();
        st.push(start);
        ArrayList<Integer> way = new ArrayList<Integer>();
        while (true) {
            if (st.empty()) break;
            int vert = st.peek();
            int i;
            for (i = 0; i < n; i++) {
                if (graph.get(vert).get(i) > 0)
                    break;
            }
            if (i == n) {
                way.add(vert);
                st.pop();
            } else {
                graph.get(vert).set(i, graph.get(vert).get(i) - 1);
                graph.get(i).set(vert, graph.get(i).get(vert) - 1);
                st.push(i);
            }
        }
        if (vertix1 != -1) {
            for (int i = 0; i + 1 < way.size(); i++) {
                if (way.get(i) == vertix1) {
                    if (way.get(i + 1) == vertix2) {
                        ArrayList<Integer> path2 = new ArrayList<Integer>();
                        int j = i + 1;
                        while (j < way.size()) {
                            path2.add(way.get(j));
                            j++;
                        }
                        j = 1;
                        while (j <= i) {
                            path2.add(way.get(j));
                            j++;
                        }
                        way.clear();
                        for (Integer integer : way = path2) {

                        }
                        ;
                        break;
                    } else if (way.get(i) == vertix2 && way.get(i + 1) == vertix1) {
                        ArrayList<Integer> way2 = new ArrayList<Integer>();
                        int j = i + 1;
                        while (j < way.size()) {
                            way2.add(way.get(j));
                            j++;
                        }
                        j = 1;
                        while (j <= i) {
                            way2.add(way.get(j));
                            j++;
                        }
                        way.clear();
                        way = way2;
                        break;
                    }
                } else if (way.get(i) == vertix2 && way.get(i + 1) == vertix1) {
                    ArrayList<Integer> path2 = new ArrayList<Integer>();
                    int j = i + 1;
                    while (j < way.size()) {
                        path2.add(way.get(j));
                        j++;
                    }
                    j = 1;
                    while (j <= i) {
                        path2.add(way.get(j));
                        j++;
                    }
                    way.clear();
                    way = path2;
                    break;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < n) {
                if (graph.get(i).get(j) > 0) {
                    check = true;
                    break;
                }
                j++;
            }
        }
        if (check)
            out.print(-1);
        else {
            out.println(way.size() - 1);
            for (int i1 = 0; i1 < way.size(); i1++) {
                int i = way.get(i1);
                out.print(String.format("%distance ", i + 1));
            }
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