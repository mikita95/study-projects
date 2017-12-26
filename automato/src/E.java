import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "discrete";

    public static void main(String[] args) {
        new E().run();
    }

    class Milli {
        private ArrayList<ArrayList<Integer>> skeleton;
        private ArrayList<ArrayList<ArrayList<Double>>> f;
        private final int POWER = 26;
        private int events;
        private int n;

        public Milli(int n, int events) {
            this.n = n;
            this.events = events;
            skeleton = new ArrayList<>();
            f = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                skeleton.add(new ArrayList<Integer>());
                f.add(new ArrayList<ArrayList<Double>>());
                for (int k = 0; k < events; k++) {
                    skeleton.get(i).add(0);
                    f.get(i).add(new ArrayList<Double>());
                    for (int c = 0; c < POWER; c++)
                        f.get(i).get(k).add(0d);
                }

            }
        }

        public void addEdge(int from, int to, int event) {
            skeleton.get(from).set(event, to);
        }

        public void addTest(int Len, String In, String Out) {
            double k = 1d / Len;
            int to = 0;
            for (int i = 0; i < Len; i++) {
                int ch = Out.charAt(i) - 'a';
                int event;
                event = In.charAt(i) == '0' ? 0 : 1;
                f.get(to).get(event).set(ch, f.get(to).get(event).get(ch) + k);
                to = skeleton.get(to).get(event);
            }
        }

        public ArrayList<ArrayList<Character>> maximize() {
            ArrayList<ArrayList<Character>> answer = new ArrayList<>();
            for (int i = 0; i < skeleton.size(); i++) {
                answer.add(new ArrayList<Character>());
                for (int e = 0; e < events; e++) {
                    double function = 0;
                    int k = 0;
                    for (int c = 0; c < POWER; c++) {
                        double current = f.get(i).get(e).get(c);
                        if (function < current) {
                            function = current;
                            k = c;
                        }
                    }
                    answer.get(i).add((char) ('a' + k));
                }
            }
            return answer;
        }


    }

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        Milli milli = new Milli(n, 2);
        for (int i = 0; i < n; i++) {
            milli.addEdge(i, in.nextInt() - 1, 0);
            milli.addEdge(i, in.nextInt() - 1, 1);
        }
        for (int i = 0; i < m; i++) milli.addTest(in.nextInt(), in.next(), in.next());
        for (ArrayList<Character> line: milli.maximize()) out.println(line.get(0) + " " + line.get(1));
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