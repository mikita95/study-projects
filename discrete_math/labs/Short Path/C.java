import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class C {
    private final static String fileName = "pathsg";
    private ArrayList<ArrayList<Integer>> graph, distance;
    private ArrayList<ArrayList<Boolean>> used;

    private final static int INF = (int) 2e8 + 1;

    void init(int n) {
        graph = new ArrayList<ArrayList<Integer>>();
        distance = new ArrayList<ArrayList<Integer>>();
        used = new ArrayList<ArrayList<Boolean>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
            distance.add(new ArrayList<Integer>());
            used.add(new ArrayList<Boolean>());
            for (int k = 0; k < n; k++) {
                graph.get(i).add(0);
                distance.get(i).add(0);
                used.get(i).add(false);

            }
        }
    }

    void floyd(int n) {
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    distance.get(i).set(j, Math.min(distance.get(i).get(j), distance.get(i).get(k) + distance.get(k).get(j)));
                }
    }

    void setDistance(int n) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (j == i) distance.get(i).set(j, Integer.valueOf(0));
                else distance.get(i).set(j, used.get(i).get(j) ? graph.get(i).get(j) : Integer.valueOf(INF));

    }


    public static void main(String[] args) {
        new C().run();
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
            x = in.nextInt();
            y = in.nextInt();
            graph.get(x - 1).set(y - 1, in.nextInt());
            used.get(x - 1).set(y - 1, true);
        }
        setDistance(n);
        floyd(n);
        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < n) {
                out.print(String.format("%d ", distance.get(i).get(j)));
                j++;
            }
            out.println();
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