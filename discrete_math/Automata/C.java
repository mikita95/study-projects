import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class C {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "problem3";

    public static void main(String[] args) {
        new C().run();
    }

    private class DFA {
        private ArrayList<ArrayList<Integer>> table;
        private HashSet<Integer> accept;
        private ArrayList<Integer> useful;
        private int n;

        public DFA(int n) {
            this.n = n;
            table = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                table.add(new ArrayList<>());
                for (int k = 0; k < n; k++) {
                    table.get(i).add(0);
                }
            }
            accept = new HashSet<>();
        }

        public void addTransition(int start, int finish, char symbol) {
            table.get(start).set(finish, table.get(start).get(finish) + 1);
        }

        public void addAcceptState(int k) {
            accept.add(k);
        }

        private void dfsForUseful(int k) {
            useful.set(k, 1);
            for (int i = 0; i < n; i++) {
                if (table.get(i).get(k) != 0) {
                    if (useful.get(i) != 1)
                        dfsForUseful(i);
                }
            }
        }

        private ArrayList<Integer> colors;

        private boolean dfsForCycles(int v) {
            colors.set(v, 1);
            for (int i = 0; i < n; i++) {
                if (table.get(v).get(i) != 0) {
                    if (useful.get(i) == 0) continue;
                    if (colors.get(i) == 0) {
                        if (dfsForCycles(i))
                            return true;
                    } else if (colors.get(i) == 1) {
                        return true;
                    }
                }
            }
            colors.set(v, 2);
            return false;
        }

        private int paths(int v) {
            int p = 0;
            if (accept.contains(v))
                p = 1;
            for (int i = 0; i < n; i++) {
                if (table.get(v).get(i) != 0 && useful.get(i) == 1) {
                    p = (p + paths(i) * table.get(v).get(i)) % 1000000007;
                }
            }
            return p;
        }

        public int countWords() {
            useful = new ArrayList<>();
            colors = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                useful.add(0);
                colors.add(0);
            }
            accept.forEach(this::dfsForUseful);
            return dfsForCycles(0) ? -1 : paths(0);
        }
    }

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
        DFA dfa = new DFA(n);
        for (int i = 0; i < k; i++)
            dfa.addAcceptState(in.nextInt() - 1);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            char c = in.next().charAt(0);
            dfa.addTransition(a - 1, b - 1, c);
        }
        out.print(dfa.countWords());
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