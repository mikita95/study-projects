import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {

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
        new E().run();
    }

    private final static String fileName = "decomposition";

    private final static long inf = (long) 1e9;

    class Edge {
        int from, to, id;
        long c, f;
        Edge rev;

        Edge(int from, int to, long c, long f, int id) {
            this.from = from;
            this.to = to;
            this.c = c;
            this.f = f;
            this.rev = null;
        }

        long remain() {
            return c - f;
        }
    }

    ArrayList<ArrayList<Edge>> g;
    ArrayList<Integer> h, start;
    long min_allowed_w = 1;
    int n;

    boolean bfs() {
        ArrayList<Boolean> used = new ArrayList<Boolean>();
        for (int i = 0; i < n; i++)
            used.add(false);
        used.set(0, true);
        h.set(0, 0);
        h.set(n - 1, -1);
        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        q.push(0);
        while (!q.isEmpty()) {
            int now = q.getFirst();
            q.pop();
            for (int i = 0; i < g.get(now).size(); i++) {
                Edge it = g.get(now).get(i);
                if (!used.get(it.to) && it.remain() >= min_allowed_w) {
                    used.set(it.to, true);
                    q.push(it.to);
                    h.set(it.to, h.get(now) + 1);
                }
            }
        }
        return h.get(n - 1) != -1;
    }

    ArrayList<Edge> path;
    int revision = 1;
    ArrayList<Integer> used;

    boolean dfs(int v) {
        used.set(v, revision);
        if (v == n - 1) {
            return true;
        }
        for (; start.get(v) < g.get(v).size(); start.set(v, start.get(v) + 1)) {
            Edge to = g.get(v).get(start.get(v));
            if (to.remain() >= min_allowed_w && used.get(to.to) != revision) {
                path.add(to);
                if (dfs(to.to))
                    return true;
                path.remove(path.size() - 1);
            }
        }
        return false;
    }

    long ans = 0;

    void push() {
        long mn = Long.MAX_VALUE;
        for (int i = 0; i < path.size(); i++) {
            mn = Math.min(mn, path.get(i).remain());
        }
        for (int i = 0; i < path.size(); i++) {
            path.get(i).f += mn;
            path.get(i).rev.f -= mn;
        }
        ans += mn;
    }

    boolean dfs2(int v) {
        used.set(v, revision);
        if (v == n - 1) return true;
        for (int i = 0; i < g.get(v).size(); i++) {
            Edge to = g.get(v).get(i);
            if (to.f > 0 && used.get(to.to) != revision && to.id > 0) {
                path.add(to);
                if (dfs2(to.to)) {
                    return true;
                }
                path.remove(path.size() - 1);
            }
        }
        return false;
    }


    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int m;
        n = in.nextInt();
        m = in.nextInt();
        g = new ArrayList<ArrayList<Edge>>();
        h = new ArrayList<Integer>();
        path = new ArrayList<Edge>();
        used = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            used.add(0);
            g.add(new ArrayList<Edge>());
            h.add(0);
        }
        for (int i = 0; i < m; i++) {
            int a, b, w;
            a = in.nextInt() - 1;
            b = in.nextInt() - 1;
            w = in.nextInt();
            Edge e1 = new Edge(a, b, w, 0, i + 1);
            Edge e2 = new Edge(b, a, w, w, -1);
            e1.rev = e2;
            e2.rev = e1;
            g.get(a).add(e1);
            g.get(b).add(e2);
        }
        while (bfs()) {
            start = new ArrayList<Integer>(n);
            for (int i = 0; i < n; i++)
                start.add(0);
            while (dfs(0)) {
                revision++;
                push();
                path.clear();
            }
            revision++;
        }
        ArrayList<ArrayList<Long>> ans2 = new ArrayList<ArrayList<Long>>();
        while (dfs2(0)) {
            ans2.add(new ArrayList<Long>());
            ArrayList<Long> ans3 = ans2.get(ans2.size() - 1);
            revision++;
            long mn = inf;
            for (int i = 0; i < path.size(); i++) {
                mn = Math.min(mn, path.get(i).f);
            }

            ans3.add(mn);
            ans3.add((long) path.size());
            for (int i = 0; i < path.size(); i++) {
                ans3.add((long) path.get(i).id);
                path.get(i).f -= mn;
            }
            path.clear();
        }
        out.println(ans2.size());
        for (int i = 0; i < ans2.size(); i++) {
            for (int k = 0; k < ans2.get(i).size(); k++) {
                out.print(ans2.get(i).get(k) + " ");
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
}
