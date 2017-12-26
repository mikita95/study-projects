import java.io.*;
import java.util.*;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "problem5";

    public static void main(String[] args) {
        new E().run();
    }

    private class DFA {
        public ArrayList<ArrayList<Integer>> table;
        public HashSet<Integer> accept;
        private int POWER;
        public int transitionsNumber = 0;


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

        public void addTransition(int start, int finish, int symbol) {

            table.get(start).set(symbol, finish);
            transitionsNumber++;
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

    private class NFA {

        private ArrayList<ArrayList<ArrayList<Integer>>> table;
        private HashSet<Integer> accept;
        private int n, POWER;

        public int transitionsNumber = 0;

        public NFA(int n, int power) {
            this.n = n;
            this.POWER = power;
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
            transitionsNumber++;
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

        private class BitSetComparator implements Comparator<BitSet> {

            @Override
            public int compare(BitSet lhs, BitSet rhs) {
                if (lhs.equals(rhs)) return 0;
                BitSet xor = (BitSet) lhs.clone();
                xor.xor(rhs);
                int firstDifferent = xor.length() - 1;
                return rhs.get(firstDifferent) ? -1 : 1;
            }

        }

        private class delta {
            BitSet from, to;
            int c;

            public delta(BitSet from, BitSet to, int c) {
                this.from = from;
                this.to = to;
                this.c = c;
            }
        }

        public DFA toDFA() {
            PriorityQueue<BitSet> queue = new PriorityQueue<>(new BitSetComparator());
            BitSet s = new BitSet(n);
            s.set(0);
            queue.add(s);
            HashSet<delta> deltaD = new HashSet<>();
            HashSet<BitSet> Qd = new HashSet<>();
            HashSet<BitSet> used = new HashSet<>();
            used.add(s);
            while (!queue.isEmpty()) {
                BitSet pd = queue.poll();
                for (int i = 0; i < POWER; i++) {
                    BitSet qd = new BitSet(n);
                    for (int p = pd.nextSetBit(0); p >= 0; p = pd.nextSetBit(p + 1)) {
                        for (Integer delta : table.get(p).get(i)) {
                            qd.set(delta, true);
                        }
                    }
                    if (!qd.isEmpty()) {
                        if (!used.contains(qd)) {
                            queue.add(qd);
                            Qd.add(qd);
                            used.add(qd);
                        }
                        Qd.add(pd);
                        deltaD.add(new delta(pd, qd, i));
                    }
                }
            }
            ArrayList<BitSet> AQd = new ArrayList<>(Qd);
            AQd.sort(new BitSetComparator());
            DFA dfa = new DFA(AQd.size(), POWER);
            int f = 0;
            for (BitSet qd : AQd) {
                for (Integer p : accept) {
                    if (qd.get(p)) {
                        dfa.addAcceptState(f + 1);
                    }
                }
                f++;
            }
            for (delta q : deltaD) {
                int from = Collections.binarySearch(AQd, q.from, new BitSetComparator());
                int to = Collections.binarySearch(AQd, q.to, new BitSetComparator());
                dfa.addTransition(from + 1, to + 1, q.c);
            }
            return dfa;
        }
    }


    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();//, l = in.nextInt();
        NFA nfa = new NFA(n, 26);
        for (int i = 0; i < k; i++)
            nfa.addAcceptState(in.nextInt() - 1);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            char c = in.next().charAt(0);
            nfa.addTransition(a - 1, b - 1, c);
        }
       // out.print(nfa.toDFA().countWordsOfLength(l));
        DFA dfa = nfa.toDFA();
        out.println(dfa.table.size() + " " + dfa.transitionsNumber + " " + dfa.accept.size());
        for (Integer i: dfa.accept)
            out.print(i + " ");
        out.println();

        for (int i = 0; i < dfa.table.size(); i++) {
            for (k = 0; k < dfa.table.get(i).size(); k++) {
                if (dfa.table.get(i).get(k) != 0) {
                    out.println(i + " " + dfa.table.get(i).get(k) + " " + (char)(k + 'a'));
                }
            }
        }
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