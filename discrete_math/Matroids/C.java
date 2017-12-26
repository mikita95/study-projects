import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class C {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "matching";
    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Pair<Integer, Integer>> nodes;
    int[] visited = new int[1001];
    int[] prior = new int[1001];
    int[] independence = new int[1001];


    boolean dfs(int v) {
        if (visited[v] == 1) return false;
        visited[v] = 1;

        for (int i = 0; i < graph.get(v).size(); i++) {
            if ((prior[graph.get(v).get(i)] == -1) || dfs(prior[graph.get(v).get(i)])) {
                prior[graph.get(v).get(i)] = v;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new C().run();
    }

    void solve() throws IOException {

        for (int i = 0; i < 1001; i++) {
            prior[i] = -1;
            independence[i] = 0;
        }
        int n = in.nextInt();
        nodes = new ArrayList<>();
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            nodes.add(new Pair<>(in.nextInt(), i));
        }
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            for (int j = 0; j < x; j++) {
                graph.get(i).add(in.nextInt() - 1);
            }
        }
        nodes.sort(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return Integer.compare(o2.getKey(), o1.getKey());
            }
        });
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                visited[j] = 0;
            dfs(nodes.get(i).getValue());
        }
        for (int i = 0; i < n; i++) {
            if (prior[i] != -1)
                independence[prior[i] + 1] = i + 1;
        }
        for (int i = 1; i <= n; i++)
            out.print(independence[i] + " ");

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