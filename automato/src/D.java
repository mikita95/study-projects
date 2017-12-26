import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "start";


    class AbstractMachine {
        private ArrayList<ArrayList<Integer>> table;
        private ArrayList<Character> accept;
        private int start;

        public AbstractMachine(int n, int power, int start) {
            table = new ArrayList<>();
            accept = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                table.add(new ArrayList<Integer>());
                accept.add('c');
            }
            this.start = start;

        }

        public void addTransition(int start, int finish, char symbol) {
            int pos = symbol - 'a';
            table.get(start).add(finish);
        }

        public void addAcceptState(int i, char k) {
            accept.set(i, k);
        }

        public ArrayList<Integer> isAccept(String word, int m) {
            ArrayList<Integer> R = new ArrayList<>();
            for (int i = 0; i < table.size(); i++)
                R.add(1);
            // R.set(this.start, 1);
            ArrayList<Integer> temp;
            final ArrayList<Integer> empty = new ArrayList<>();
            for (int t = 0; t < table.size(); t++)
                empty.add(0);
            for (int i = m - 1; i >= 0; i--) {
                temp = empty;
                for (int k = 0; k < R.size(); k++) {
                    if (R.get(k) == 0) continue;
                    for (int q = 0; q < table.get(k).size(); q++) {
                        if (accept.get(k) == word.charAt(i))
                            temp.set(table.get(k).get(q), 1);
                    }
                }
                R = temp;
            }
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < table.size(); i++) {
                if (R.get(i) == 1)
                    result.add(i + 1);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        new D().run();
    }

    void solve() throws IOException {
        int m = in.nextInt(), n = in.nextInt();
        AbstractMachine abstractMachine = new AbstractMachine(n, 2, 0);
        for (int i = 0; i < n; i++) {
            abstractMachine.addTransition(in.nextInt() - 1, i, 'a');
            abstractMachine.addTransition(in.nextInt() - 1, i, 'b');
            abstractMachine.addAcceptState(i, in.next().charAt(0));
        }
        String z = in.next();

        ArrayList<Integer> result = abstractMachine.isAccept(z, m);
        out.print(result.size());
        for (Integer i : result) out.print(" " + i);
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