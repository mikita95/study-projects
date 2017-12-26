import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "automaton";

    public static void main(String[] args) {
        new A().run();
    }

    private class Rool {
        private Character from;
        private ArrayList<Character> to;

        public Rool(Character nonterminal, Character terminal) {
            this.from = nonterminal;
            this.to = new ArrayList<Character>();
            this.to.add(terminal);
        }

        public Rool(Character nonterminal, Character terminal, Character nonterminal1) {
            this.from = nonterminal;
            this.to = new ArrayList<Character>();
            this.to.add(terminal);
            this.to.add(nonterminal1);
        }

        public Rool(Character nonterminal, ArrayList<Character> body) {
            this.from = nonterminal;
            this.to = body;
        }

        public Character getFrom() {
            return from;
        }

        public void setFrom(Character from) {
            this.from = from;
        }

        public ArrayList<Character> getTo() {
            return to;
        }

        public void setTo(ArrayList<Character> to) {
            this.to = to;
        }
    }

    class CFGrammar {
        HashSet<Character> nonterminals;
        HashSet<Character> terminals;
        ArrayList<Rool> rools;
        public CFGrammar() {
            nonterminals = new HashSet<Character>();
            terminals = new HashSet<Character>();
        }
        public void addRool(Character from, Character... rool) {
            ArrayList<Character> r = new ArrayList<Character>();
            for (Character c: rool) {
                if (Character.isLowerCase(c))
                    terminals.add(c);
                else nonterminals.add(c);
                r.add(c);
            }
            nonterminals.add(from);
            rools.add(new Rool(from, r));
        }

    }

    private class NFA {

        private ArrayList<ArrayList<ArrayList<Integer>>> table;
        private HashSet<Integer> accept;
        private int start;

        public NFA(int n, int power, int start) {
            table = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                table.add(new ArrayList<ArrayList<Integer>>());
                for (int k = 0; k < power; k++) {
                    table.get(i).add(new ArrayList<Integer>());
                }
            }
            this.start = start;
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
            R.set(this.start, 1);
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
        int n = in.nextInt();
        char start = in.next().charAt(0);
        NFA nfa = new NFA(27, 26, start - 'A');
        for (int i = 0; i < n; i++) {
            char from = in.next().charAt(0);
            in.next();
            String to = in.next();
            if (to.length() == 1) {
                nfa.addTransition(from - 'A', 26, to.charAt(0));
            } else
                nfa.addTransition(from - 'A', to.charAt(1) - 'A', to.charAt(0));
        }
        nfa.addAcceptState(26);
        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            out.println(nfa.isAccept(in.next()) ? "yes" : "no");
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