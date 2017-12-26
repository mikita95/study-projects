import java.io.*;
import java.util.*;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "destroy";
    Random rand = new Random();

    ArrayList<Edge> edges;
    int[] relative;

    class Edge implements Comparable<Edge> {
        int start, end;
        long weight;
        int id;
        public Edge(int start, int end, long weight, int id) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.id = id;
        }
        public int compareTo(Edge edge) {
            return Long.compare(edge.weight, weight);
        }
    }

    int dsuGet(int v) {
        if (v == relative[v])
            return v;
        else
            return (relative[v] =  dsuGet(relative[v]));
    }

    void dsuUnite(int f, int t) {
        f = dsuGet(f);
        t = dsuGet(t);
        if (rand.nextBoolean()) {
            int tmp = f;
            f = t;
            t = tmp;
        }
        if (f != t)
            relative[f] =  t;
    }

    void initialization(int n, int m) {
        edges = new ArrayList<>(m);
        relative = new int[n];
    }

    void makeSet(int n) {
        for (int i = 0; i < n; i++) {
            relative[i] = i;
        }
    }

    long kruskal(int m) {
        long weight = 0;
        int i = 0;
        while (i < m) {
            int a = edges.get(i).start;
            int b = edges.get(i).end;
            long c = edges.get(i).weight;
            if (dsuGet(a) != dsuGet(b)) {
                weight += c;
                dsuUnite(a, b);
                good[edges.get(i).id] = true;
            }
            i++;
        }
        return weight;
    }

    boolean good[];

    public static void main(String[] args) {
        new B().run();
    }

    void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        long s = in.nextLong();
        initialization(n, m);
        for (int i = 0; i < m; i++) {
            edges.add(new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextLong(), i));
        }
        Collections.sort(edges);
        makeSet(n);
        good = new boolean[m];
        kruskal(m);
        long answer = 0;
        ArrayList<Integer> minCost = new ArrayList<>();
        for (int i = m - 1; i >= 0; i--) {
            if (!good[edges.get(i).id]) {
                if (answer + edges.get(i).weight <= s) {
                    answer += edges.get(i).weight;
                    minCost.add(edges.get(i).id);
                }
            }
        }
        out.println(minCost.size());
        minCost.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        for (Integer e: minCost) {
            out.print(e + 1 + " ");
        }
    }

    public void run() {
        try {
            InputStream is = new FileInputStream(FILE_NAME + ".in");
            in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".out"));
            solve();
            out.close();
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

        public String next() {
            return tokenizer.nextToken();
        }

    }
}