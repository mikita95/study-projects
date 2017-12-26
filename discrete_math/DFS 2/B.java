import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    private final static String fileName = "bridges";

    ArrayList<ArrayList<Integer>> graph, list;
    ArrayList<Integer> answer;
    ArrayList<Boolean> used;
    ArrayList<Integer> comeToBridge;
    ArrayList<Integer> outOfBridge;
    int step;

    void init(int n) {
        used = new ArrayList<Boolean>(n);
        comeToBridge = new ArrayList<Integer>(n);
        outOfBridge = new ArrayList<Integer>(n);
        graph = new ArrayList<ArrayList<Integer>>(n);
        list = new ArrayList<ArrayList<Integer>>(n);
        answer = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            list.add(new ArrayList<Integer>());
            comeToBridge.add(0);
            outOfBridge.add(0);
            used.add(false);
        }
        step = 0;
    }

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
        comeToBridge.set(v, step);
        outOfBridge.set(v, step);
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (cG == graph.get(v).get(i)) continue;
            if (used.get(graph.get(v).get(i))) {
                outOfBridge.set(v, Math.min(comeToBridge.get(graph.get(v).get(i)), outOfBridge.get(v)));
            } else {
                dfs(graph.get(v).get(i), v);
                outOfBridge.set(v, Math.min(outOfBridge.get(v), outOfBridge.get(graph.get(v).get(i))));
                if (comeToBridge.get(v) < outOfBridge.get(graph.get(v).get(i))) {
                    answer.add(1 + list.get(v).get(i));
                }
            }
        }
    }

    void runDFS(int n) {
        for (int i = 0; i < n; i++) {
            if (!used.get(i)) {
                dfs(i, -1);
            }
        }
    }

    public static void main(String[] args) {
        new B().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);
        int x, y;
        for (int i = 0; i < m; i++) {
            x = in.nextInt() - 1;
            y = in.nextInt() - 1;
            graph.get(x).add(y);
            list.get(x).add(i);
            graph.get(y).add(x);
            list.get(y).add(i);
        }
        runDFS(n);
        out.println(answer.size());
        //quickSort(answer, 0, answer.size() - 1);

        for (int i = 0; i < answer.size(); i++) {
            out.print(answer.get(i) + " ");
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