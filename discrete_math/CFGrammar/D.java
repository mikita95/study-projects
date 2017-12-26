import java.io.*;
import java.util.*;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "nfc";

    public static void main(String[] args) {
        new D().run();
    }

    class CFGrammar {

        HashSet<String> nonterminals;
        HashSet<String> terminals;
        ArrayList<ArrayList<ArrayList<String>>> rools;
        String start;
        HashMap<String, Integer> map;


        public CFGrammar(String start) {
            nonterminals = new HashSet<>();
            terminals = new HashSet<>();
            rools = new ArrayList<>();
            this.start = start;
            map = new HashMap<>();
        }

        public void addRool(String from, String rool) {
            ArrayList<String> r = new ArrayList<String>();
            for (int i = 0; i < rool.length(); i++) {
                char c = rool.charAt(i);
                if (Character.isLowerCase(c))
                    terminals.add(String.valueOf(c));
                else {
                    String t = String.valueOf(c);
                    nonterminals.add(t);
                    if (!map.containsKey(t)) {
                        map.put(t, map.size());
                        rools.add(new ArrayList<ArrayList<String>>());
                    }
                }
                r.add(String.valueOf(c));
            }

            nonterminals.add(from);
            if (!map.containsKey(from)) {
                map.put(from, map.size());
                rools.add(new ArrayList<ArrayList<String>>());
            }

            rools.get(map.get(from)).add(r);
        }


        public HashSet<String> findEpsilonMaking() {
            HashSet<String> set = new HashSet<>();
            for (String c : nonterminals)
                for (ArrayList<String> rool : rools.get(map.get(c))) {
                    if (rool.isEmpty()) {
                        set.add(c);
                        break;
                    }
                }

            boolean isModified = true;
            while (isModified) {
                isModified = false;
                for (String c : nonterminals) {
                    for (ArrayList<String> rool : rools.get(map.get(c))) {
                        if (rool.isEmpty()) continue;
                        boolean isEpsilonMaking = true;
                        for (String ch : rool) {
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

        public HashSet<String> findNonGenerating() {
            HashSet<String> set = new HashSet<>();
            for (String c : nonterminals) {
                for (ArrayList<String> rool : rools.get(map.get(c))) {
                    boolean flag = true;
                    for (String ch : rool) {
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
                for (String ch : nonterminals) {
                    for (ArrayList<String> rool : rools.get(map.get(ch))) {
                        if (rool.isEmpty()) continue;
                        boolean isGenerating = true;
                        for (String c : rool) {
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
            HashSet<String> result = new HashSet<>(nonterminals);
            result.removeAll(set);
            return result;
        }

        public HashSet<String> findUnreachable() {
            HashSet<String> set = new HashSet<>();
            set.add(start);
            boolean isModified = true;
            while (isModified) {
                isModified = false;
                for (String ch : nonterminals) {
                    for (ArrayList<String> rool : rools.get(map.get(ch))) {
                        int size = set.size();
                        if (set.contains(ch)) {
                            for (String c : rool)
                                if (nonterminals.contains(c))
                                    set.add(c);
                        }
                        if (size < set.size())
                            isModified = true;
                    }
                }
            }
            HashSet<String> result = new HashSet<>(nonterminals);
            result.removeAll(set);
            return result;
        }

        public long numberOfWays(String w) {
            final long MOD = 1000000007;
            long[][][] d = new long[nonterminals.size()][w.length()][w.length()];
            for (int i = 0; i < w.length(); i++) {
                for (String c : nonterminals) {
                    for (ArrayList<String> rool : rools.get(map.get(c))) {
                        if (rool.size() == 1 && Objects.equals(rool.get(0), String.valueOf(w.charAt(i)))) {
                            d[map.get(c)][i][i] += 1;
                        }
                    }
                }
            }

            for (int m = 0; m < w.length(); m++) {
                for (int i = 0; i < w.length() - m; i++) {
                    int j = i + m;
                    for (int k = i; k < j; k++) {
                        for (String c : nonterminals) {
                            for (ArrayList<String> rool : rools.get(map.get(c))) {
                                if (rool.size() == 2) {
                                    d[map.get(c)][i][j] = (d[map.get(c)][i][j] + (d[map.get(rool.get(0))][i][k] * d[map.get(rool.get(1))][k + 1][j])) % MOD;
                                }
                            }

                        }
                    }
                }
            }
            return d[map.get(start)][0][w.length() - 1];
        }
    }


    void solve() throws IOException {
        int n = in.nextInt();
        String start = in.next();
        CFGrammar cfGrammar = new CFGrammar(start);
        for (int i = 0; i < n; i++) {
            String s = in.nextLine();
            String to = "";
            if (s.length() >= 5)
                to = s.substring(5, s.length());
            cfGrammar.addRool(String.valueOf(s.charAt(0)), to);
        }

        out.println(cfGrammar.numberOfWays(in.next()));
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