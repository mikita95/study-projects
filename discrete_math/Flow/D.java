import java.io.*;
import java.util.*;

public class D {

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
        new D().run();
    }

    private final static String fileName = "paths";

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

    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> connect;
    ArrayList<Boolean> used;
    private final static int inf = -100;

    boolean dfs(int vertex) {
        if (used.get(vertex))
            return false;
        used.set(vertex ,true);
        ArrayList<Integer> set = graph.get(vertex);
        for (int i = 0, st = set.size(); i < st; i++) {
            if (connect.get(set.get(i)) == inf) {
                connect.set(set.get(i), vertex);
                return true;
            } else if (dfs(connect.get(set.get(i)))) {
                connect.set(set.get(i), vertex);
                return true;
            }
        }
        return false;
    }


    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>(n);
        connect = new ArrayList<Integer>(n);
        used = new ArrayList<Boolean>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            connect.add(inf);
            used.add(false);
        }
    }

    void runDFS(int n) {
        int i = 0;
        while (i < n) {
            Collections.fill(used, false);
            dfs(i);
            i++;
        }
    }

    int minCountPath(int n) {
        int result = 0;
        int i = 0;
        while (i < n) {
            if (connect.get(i) != inf) {
                result++;
            }
            i++;
        }
        return result;

    }
    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int n = in.nextInt();
        int m = in.nextInt();
        init(n);
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            graph.get(x - 1).add(y - 1);
        }
        runDFS(n);
        int answer;
        answer = n - minCountPath(n);
        out.print(answer);
        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
