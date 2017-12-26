import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "problem4";

    public static void main(String[] args) {
        new D().run();
    }

    private class DFA {
        private ArrayList<ArrayList<Integer>> table;
        private HashSet<Integer> accept;
        private int POWER;

        public DFA(int n, int power) {
            table = new ArrayList<ArrayList<Integer>>();
            this.POWER = power;
            for (int i = 0; i <= n; i++) {
                table.add(new ArrayList<Integer>());
                for (int k = 0; k < power; k++) {
                    table.get(i).add(0);
                }
            }
            accept = new HashSet<Integer>();
        }

        public void addTransition(int start, int finish, char symbol) {
            int pos = symbol - 'a';
            table.get(start).set(pos, finish);
        }

        public void addAcceptState(int k) {
            accept.add(k);
        }

        public Boolean isAccept(String word) {
            int pos = 1;
            for (int i = 0; i < word.length(); i++) {
                pos = table.get(pos).get(word.charAt(i) - 'a');
            }
            return accept.contains(pos);
        }

        private int f(int q, int i) {
            if (i == 0) {
                if (accept.contains(q))
                    return 1;
                else return 0;
            } else {
                int sum = 0;
                for (int p = 1; p < table.size(); p++) {
                    for (int a = 0; a < POWER; a++) {
                        if (table.get(p).get(a) == q) {
                            sum += f(p, i - 1) % 1000000007;
                        }
                    }
                }
                return sum;
            }
        }

        int countWordsOfLength(int length) {
            ArrayList<ArrayList<Integer>> d = new ArrayList<>();
            d.add(new ArrayList<>());
            for (int i = 1; i < table.size(); i++) {
                d.get(0).add(f(i, 0));
            }
            for (int i = 0; i < length; i++) {
                d.add(new ArrayList<>());
                for (int k = 1; k < table.size(); k++)
                    d.get(i + 1).add(0);
                for (int k = 1; k < table.size(); k++) {
                    for (int a = 0; a < POWER; a++)
                        if (table.get(k).get(a) != 0) {
                            d.get(i + 1).set(k - 1, (d.get(i).get(table.get(k).get(a) - 1) + d.get(i + 1).get(k - 1)) % 1000000007);
                        }
                }
            }
            return d.get(length).get(0);
        }
    }

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt(), l = in.nextInt();
        DFA dfa = new DFA(n, 26);
        for (int i = 0; i < k; i++)
            dfa.addAcceptState(in.nextInt());
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            char c = in.next().charAt(0);
            dfa.addTransition(a, b, c);
        }
        out.print(dfa.countWordsOfLength(l));


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