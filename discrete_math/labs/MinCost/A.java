import java.io.*;
import java.util.*;

public class A {
    private final static String fileName = "assignment";

    ArrayList<Integer> first, second, line, way, minCost;
    ArrayList<ArrayList<Integer>> matrix;
    private final static int INF = (int) 1e9;
    int n;
    ArrayList<Boolean> used;

    void vengerian() {
        int i = 1;
        while (i <= n) {
            line.set(0, i);
            int now = 0;
            Collections.fill(minCost, INF);
            Collections.fill(used, false);
            do {
                used.set(now, true);
                int p = INF;
                int cJ = 0;
                for (int j = 1; j <= n; j++) {
                    if (!used.get(j)) {
                        if ((matrix.get(line.get(now)).get(j) - first.get(line.get(now)) - second.get(j)) < minCost.get(j)) {
                            minCost.set(j, matrix.get(line.get(now)).get(j) - first.get(line.get(now)) - second.get(j));
                            way.set(j, now);
                        }
                        if (minCost.get(j) <= p) {
                            p = minCost.get(j);
                            cJ = j;
                        }
                    }
                }
                for (int j = 0; j <= n; j++) {
                    if (used.get(j)) {
                        first.set(line.get(j), first.get(line.get(j)) + p);
                        second.set(j, second.get(j) - p);
                    } else {
                        minCost.set(j, minCost.get(j) - p);
                    }
                }
                now = cJ;
            } while (line.get(now) > 0);
            while ((now = swap(now)) > 0) {
            }
            i++;
        }
    }

    int swap(int a) {
        int b = way.get(a);
        line.set(a, line.get(b));
        return b;
    }

    void init() {
        first = new ArrayList<Integer>();
        second = new ArrayList<Integer>();
        line = new ArrayList<Integer>();
        way = new ArrayList<Integer>();
        minCost = new ArrayList<Integer>();
        used = new ArrayList<Boolean>();
        for (int i = 0; i <= n + 1; i++) {
            first.add(0);
            second.add(0);
            line.add(0);
            way.add(0);
            minCost.add(INF);
            used.add(false);
        }
    }

    public void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        n = in.nextInt();
        matrix = new ArrayList<ArrayList<Integer>>();
        matrix.add(new ArrayList<Integer>());
        for (int i = 1; i <= n; i++) {
            matrix.add(new ArrayList<Integer>());
            matrix.get(i).add(0);
            for (int j = 1; j <= n; j++) {
                matrix.get(i).add(in.nextInt());
            }
        }
        init();
        vengerian();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i <= n; i++)
            result.add(0);
        for (int i = 1; i <= n; i++) {
            result.set(line.get(i), i);
        }
        out.println(-second.get(0));
        for (int i = 1; i <= n; i++) {
            out.println(String.format("%d %d", i, result.get(i)));
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
        new A().run();
    }


}
