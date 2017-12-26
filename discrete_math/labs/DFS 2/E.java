import java.io.*;
import java.util.*;

public class E {
    FastScanner in;
    PrintWriter out;
    ArrayList<Integer> graph[];
    boolean[] used;
    int[] enter, ret, color;
    int time = 0, count = 0;

    void dfs(int v, int p) {
        used[v] = true;
        enter[v] = ret[v] = time++;
        for (int i = 0; i < graph[v].size(); i++) {
            int t = graph[v].get(i);
            if (t == p) continue;
            if (used[t]) {
                ret[v] = Math.min(ret[v], enter[t]);
            } else {
                dfs(t, v);
                ret[v] = Math.min(ret[v], ret[t]);
            }
        }
    }

    void paint(int v, int col) {
        color[v] = col;
        for (int i = 0; i < graph[v].size(); i++) {
            int u = graph[v].get(i);
            if (color[u] == 0) {
                if (ret[u] == enter[u]) {
                    count++;
                    paint(u, count);
                } else {
                    paint(u, col);
                }
            }
        }
    };

    public void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        used = new boolean[n];
        enter = new int[n];
        ret = new int[n];
        color = new int[n];
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
            enter[i] = 0;
            ret[i] = 0;
            used[i] = false;
            color[i] = 0;
        }
        int x, y;
        for (int i = 0; i < m; i++) {
            x = in.nextInt();
            y = in.nextInt();
            graph[x - 1].add(y - 1);
            graph[y - 1].add(x - 1);
        }
        time = 0;
        for (int i = 0; i < n; i++)
            if (!used[i])
                dfs(i, -1);
        count = 0;
        for (int i = 0; i < n; i++)
            if (color[i] == 0) {
                count++;
                paint(i, count);
            }
        out.println(count);
        for (int i = 0; i < n; i++)
            out.print(color[i] + " ");
    }

    public void run() {
        try {
            in = new FastScanner(new File("bicone.in"));
            out = new PrintWriter(new File("bicone.out"));

            solve();

            out.close();
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
        new E().run();
    }
}
