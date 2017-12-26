import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class F {
    private final static String fileName = "hamiltonian";

    public static void main(String[] args) {
        new F().run();
    }

    PrintWriter out;

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Boolean> used;
    ArrayList<Integer> topSort;
    HashSet<Pair<Integer, Integer>> lines = new HashSet<Pair<Integer, Integer>>();
    int count = 0;

    void dfs(int v) {
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int point = graph.get(v).get(i);
            if (!used.get(point)) {
                dfs(point);
            }
        }
        topSort.add(v);
    }

    public class Pair<A, B> {
        private A first;
        private B second;

        public Pair(A first, B second) {
            super();
            this.first = first;
            this.second = second;
        }

        public int hashCode() {
            int hashFirst = first != null ? first.hashCode() : 0;
            int hashSecond = second != null ? second.hashCode() : 0;

            return (hashFirst + hashSecond) * hashSecond + hashFirst;
        }

        public boolean equals(Object other) {
            if (other instanceof Pair) {
                Pair otherPair = (Pair) other;
                return
                        ((  this.first == otherPair.first ||
                                ( this.first != null && otherPair.first != null &&
                                        this.first.equals(otherPair.first))) &&
                                (	this.second == otherPair.second ||
                                        ( this.second != null && otherPair.second != null &&
                                                this.second.equals(otherPair.second))) );
            }

            return false;
        }

        public String toString()
        {
            return "(" + first + ", " + second + ")";
        }

        public A getFirst() {
            return first;
        }

        public void setFirst(A first) {
            this.first = first;
        }

        public B getSecond() {
            return second;
        }

        public void setSecond(B second) {
            this.second = second;
        }
    }

    ArrayList<Integer> reverseArray(ArrayList<Integer> x) {
        ArrayList<Integer> y = new ArrayList<Integer>();
        for (int i = x.size() - 1; i >= 0; i--)
            y.add(x.get(i));
        return y;
    }

    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        used = new ArrayList<Boolean>(n);
        topSort = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            used.add(false);

        }

    }

    void topSort(int n) {
        for (int i = 0; i < n; i++) {
            if (!used.get(i)) {
                dfs(i);
            }
        }
    }

    int countAnswer (int n) {
        int countPrivate = 0;
        for (int i = 0; i < topSort.size() - 1; i++) {
            int dist = topSort.get(i);
            int point = topSort.get(i + 1);
            if (!lines.contains(new Pair<Integer, Integer>(dist, point)))
                countPrivate++;

        }
        int dist = topSort.get(topSort.size() - 1);
        int point = topSort.get(0);
        if (!lines.contains(new Pair<Integer, Integer>(dist, point)))
            countPrivate++;
        return countPrivate;
    }



    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        out = new PrintWriter(new File(fileName + ".out"));
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        int x, y;
        init(n);
        for (int i = 0; i < m; i++) {
            x = in.nextInt();
            y = in.nextInt();
            graph.get(x - 1).add(y - 1);
            lines.add(new Pair<Integer, Integer>(x - 1, y - 1));
        }
        topSort(n);
        topSort = reverseArray(topSort);
        count = countAnswer(n);
        
        if (count > 1)
            out.println("NO");
        else
            out.println("YES");
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
