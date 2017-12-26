import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class C {
    private final static String fileName = "points";
    PrintWriter out;

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Boolean> used;
    TreeSet<Integer> ans;
    ArrayList<Integer> comeTo, outOf;
    int step;

    void quickSort(ArrayList<Integer> data, int l, int r) {
        int i = l;
        int j = r;
        int x = data.get(l + (int) (Math.random() * ((r - l) + 1)));
        while (i < j) {
            while (data.get(i) < x)
                i++;
            while (data.get(j) > x)
                j--;
            if (i <= j) {
                int c = data.get(i);
                data.set(i, data.get(j));
                data.set(j, c);
                i++;
                j--;
            }
        }
        if (i < r)
            quickSort(data, i, r);
        if (l < j)
            quickSort(data, l, j);
    }

    void dfs(int v, int cG) {
        used.set(v, true);
        step++;
        comeTo.set(v, step);
        outOf.set(v, step);
        int to = 0;
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (cG != graph.get(v).get(i)) {
                if (used.get(graph.get(v).get(i))) {
                    outOf.set(v, Math.min(comeTo.get(graph.get(v).get(i)), outOf.get(v)));
                } else {
                    dfs(graph.get(v).get(i), v);
                    outOf.set(v, Math.min(outOf.get(v), outOf.get(graph.get(v).get(i))));
                    if (cG != -1)
                        if (outOf.get(graph.get(v).get(i)) < comeTo.get(v)) {
                        } else {
                            ans.add(v + 1);
                        }
                    to += 1;
                }
            }
        }
        if (cG == -1)
            if (1 < to) {
                ans.add(v + 1);
            }
    }

    void init(int n) {
        used = new ArrayList<Boolean>(n);
        comeTo = new ArrayList<Integer>(n);
        outOf = new ArrayList<Integer>(n);
        graph = new ArrayList<ArrayList<Integer>>(n);
        ans = new TreeSet<Integer>();
        int i = 0;
        while (i < n) {
            graph.add(new ArrayList<Integer>());
            comeTo.add(0);
            outOf.add(0);
            used.add(false);
            i++;
        }

    }

    void runDFS(int n) {
        step = 0;
        int i = 0;
        while (i < n) {
            if (!used.get(i)) {
                dfs(i, -1);
            }
            i++;
        }
    }

    void makeAnswer() {
        while (true) {
            if (!(!ans.isEmpty())) break;
            out.println(ans.first());
            ans.remove(ans.first());
        }

    }


    public static void main(String[] args) {
        new C().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);

        int x, y;
        int i = 0;
        while (i < m) {
            x = in.nextInt() - 1;
            y = in.nextInt() - 1;
            graph.get(x).add(y);
            graph.get(y).add(x);
            i++;
        }

       if (ans.size() != 0)
           quickSort(comeTo, 0, comeTo.size() - 1);


        runDFS(n);


        out.println(ans.size());
        makeAnswer();


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