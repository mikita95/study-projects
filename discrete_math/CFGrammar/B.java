import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "epsilon";

    public static void main(String[] args) {
        new B().run();
    }

    class CFGrammar {
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

        HashSet<Character> nonterminals;
        HashSet<Character> terminals;
        //ArrayList<Rool> rools;
        ArrayList<ArrayList<ArrayList<Character>>> rools;
        Character start;

        public CFGrammar(Character start) {
            nonterminals = new HashSet<Character>();
            terminals = new HashSet<Character>();
            rools = new ArrayList<>();
            for (int i = 0; i < 26; i++)
                rools.add(new ArrayList<ArrayList<Character>>());
            this.start = start;
        }

        public void addRool(Character from, String rool) {
            ArrayList<Character> r = new ArrayList<Character>();
            for (int i = 0; i < rool.length(); i++) {
                char c = rool.charAt(i);
                if (Character.isLowerCase(c))
                    terminals.add(c);
                else {
                    nonterminals.add(c);
                }
                r.add(c);
            }
            nonterminals.add(from);
            rools.get(from - 'A').add(r);
        }


        public HashSet<Character> findEpsilonMaking() {
            HashSet<Character> set = new HashSet<>();
            for (Character c : nonterminals)
                for (ArrayList<Character> rool : rools.get(c - 'A')) {
                    if (rool.isEmpty()) {
                        set.add(c);
                        break;
                    }
                }

            boolean isModified = true;
            while (isModified) {
                isModified = false;
                for (Character c: nonterminals) {
                    for (ArrayList<Character> rool : rools.get(c - 'A')) {
                        if (rool.isEmpty()) continue;
                        boolean isEpsilonMaking = true;
                        for (Character ch : rool) {
                            if (terminals.contains(ch)) {
                                isEpsilonMaking = false;
                                break;
                            }
                            if (!set.contains(ch)) {
                                isEpsilonMaking = false;
                                break;
                            }
                        }
                        int size = set.size();
                        if (isEpsilonMaking) set.add(c);
                        if (size < set.size())
                            isModified = true;
                    }
                }
            }
            return set;
        }

        public HashSet<Character> findNonGenerating() {
            HashSet<Character> set = new HashSet<>();
            for (Character c: nonterminals) {
                for (ArrayList<Character> rool : rools.get(c - 'A')) {
                    boolean flag = true;
                    for (Character ch : rool) {
                        if (nonterminals.contains(ch)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        set.add(c);
                    }
                }
            }
            boolean isModified = true;
            while (isModified) {
                isModified = false;
                for (Character ch: nonterminals) {
                    for (ArrayList<Character> rool : rools.get(ch - 'A')) {
                        if (rool.isEmpty()) continue;
                        boolean isGenerating = true;
                        for (Character c : rool) {
                            if (nonterminals.contains(c) && !set.contains(c)) {
                                isGenerating = false;
                                break;
                            }
                        }
                        int size = set.size();
                        if (isGenerating) set.add(ch);
                        if (size < set.size())
                            isModified = true;
                    }
                }
            }
            HashSet<Character> result = new HashSet<>(nonterminals);
            result.removeAll(set);
            return result;
        }

        public HashSet<Character> findUnreachable() {
            HashSet<Character> set = new HashSet<>();
            set.add(start);
            boolean isModified = true;
            while (isModified) {
                isModified = false;
                for (Character ch: nonterminals) {
                    for (ArrayList<Character> rool : rools.get(ch - 'A')) {
                        int size = set.size();
                        if (set.contains(ch)) {
                            for (Character c : rool)
                                if (nonterminals.contains(c))
                                    set.add(c);
                        }
                        if (size < set.size())
                            isModified = true;
                    }
                }
            }
            HashSet<Character> result = new HashSet<>(nonterminals);
            result.removeAll(set);
            return result;
        }

        public long numberOfWays(String w) {
            final long MOD = 1000000007;
            long[][][] d = new long[26][w.length()][w.length()];
            for (int i = 0; i < w.length(); i++) {
                for (Character c : nonterminals) {
                    for (ArrayList<Character> rool : rools.get(c - 'A')) {
                        if (rool.size() == 1 && rool.get(0) == w.charAt(i)) {
                            d[c - 'A'][i][i] += 1;
                        }
                    }
                }
            }

            for (int m = 0; m < w.length(); m++) {
                for (int i = 0; i < w.length() - m; i++) {
                    int j = i + m;
                    for (int k = i; k < j; k++) {
                        for (Character c : nonterminals) {
                            for (ArrayList<Character> rool : rools.get(c - 'A')) {
                                if (rool.size() == 2) {
                                    d[c - 'A'][i][j] = (d[c - 'A'][i][j] + (d[rool.get(0) - 'A'][i][k] * d[rool.get(1) - 'A'][k + 1][j])) % MOD;
                                }
                            }

                        }
                    }
                }
            }
            return d[start - 'A'][0][w.length() - 1];
        }
    }

    void solve() throws IOException {
        int n = in.nextInt();
        char start = in.next().charAt(0);
        CFGrammar g = new CFGrammar(start);
        for (int i = 0; i < n; i++) {
            String s = in.nextLine();
            char from = s.charAt(0);
            String to = "";
            if (s.length() > 4)
                to = s.substring(5, s.length());
            g.addRool(from, to);
        }
        ArrayList<Character> answer = new ArrayList<>(g.findEpsilonMaking());
        answer.sort(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return o1.compareTo(o2);
            }
        });
        for (Character c : answer)
            out.print(c + " ");
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

        public String nextLine() {
            return tokenizer.nextToken(System.lineSeparator());
        }

    }
}