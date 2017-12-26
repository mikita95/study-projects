import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "problem2";

    public static void main(String[] args) {
        new B().run();
    }

    private class NFA {

        private ArrayList<ArrayList<ArrayList<Integer>>> table;
        private HashSet<Integer> accept;

        public NFA(int n, int power) {
            table = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                table.add(new ArrayList<>());
                for (int k = 0; k < power; k++) {
                    table.get(i).add(new ArrayList<>());
                }
            }
            accept = new HashSet<>();
        }

        public void addTransition(int start, int finish, char symbol) {
            int pos = symbol - 'a';
            table.get(start).get(pos).add(finish);
        }

        public void addAcceptState(int k) {
            accept.add(k);
        }

        public Boolean isAccept(String word) {
            ArrayList<Integer> R = new ArrayList<>();
            for (int i = 0; i < table.size(); i++)
                R.add(0);
            R.set(0, 1);
            ArrayList<Integer> temp;
            for (int i = 0; i < word.length(); i++) {
                temp = new ArrayList<>();
                for (int t = 0; t < table.size(); t++)
                    temp.add(0);
                for (int k = 0; k < R.size(); k++) {
                    if (R.get(k) == 0) continue;
                    for (int q = 0; q < table.get(k).get(word.charAt(i) - 'a').size(); q++) {
                        temp.set(table.get(k).get(word.charAt(i) - 'a').get(q), 1);
                    }
                }
                R = temp;
            }
            for (Integer q : accept) {
                if (R.get(q) == 1)
                    return true;
            }
            return false;
        }
    }

    void solve() throws IOException {
        String s = in.next();
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
        NFA nfa = new NFA(n, 26);
        for (int i = 0; i < k; i++)
            nfa.addAcceptState(in.nextInt() - 1);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            char c = in.next().charAt(0);
            nfa.addTransition(a - 1, b - 1, c);
        }
        if (nfa.isAccept(s))
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