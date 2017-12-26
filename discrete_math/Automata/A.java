import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "problem1";

    public static void main(String[] args) {
        new A().run();
    }

    private class DFA {
        private ArrayList<ArrayList<Integer>> table;
        private HashSet<Integer> accept;

        public DFA(int n, int power) {
            table = new ArrayList<ArrayList<Integer>>();
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
    }

    void solve() throws IOException {
        String s = in.next();
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
        DFA dfa = new DFA(n, 26);
        for (int i = 0; i < k; i++)
            dfa.addAcceptState(in.nextInt());
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            char c = in.next().charAt(0);
            dfa.addTransition(a, b, c);
        }
        if (dfa.isAccept(s))
            out.print("Accepts");
        else out.print("Rejects");


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