import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "cf";

    public static void main(String[] args) {
        new E().run();
    }


    class CFGrammar {

        HashSet<String> nonterminals;
        HashSet<String> terminals;
        ArrayList<ArrayList<ArrayList<String>>> rools;
        String start;
        HashMap<String, Integer> map;
        private ArrayList<ArrayList<String>> to;
        private ArrayList<ArrayList<String>> r;
        private int l;
        final int MAX_POWER = 100;

        public CFGrammar(String start) {
            nonterminals = new HashSet<>();
            terminals = new HashSet<>();
            rools = new ArrayList<>();
            this.start = start;
            map = new HashMap<>();
            map.put(start, start.charAt(0) - 'A');
            nonterminals.add(start);
            rools.add(new ArrayList<ArrayList<String>>());
            to = new ArrayList<>();
            for (int i = 0; i < MAX_POWER; i++) {
                to.add(new ArrayList<String>());
            }
            r = new ArrayList<>();
            l = 0;
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

        public void addRool(String from, ArrayList<String> rool) {
            ArrayList<String> r = new ArrayList<String>();
            for (String c : rool) {
                if (c.length() == 1 && Character.isLowerCase(c.charAt(0)))
                    terminals.add(String.valueOf(c));
                else {
                    nonterminals.add(c);
                    if (!map.containsKey(c)) {
                        map.put(c, map.size());
                        rools.add(new ArrayList<ArrayList<String>>());
                    }
                }
                r.add(c);
            }

            nonterminals.add(from);
            if (!map.containsKey(from)) {
                map.put(from, map.size());
                rools.add(new ArrayList<ArrayList<String>>());
            }
            if (!rools.get(map.get(from)).contains(r))
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

        public boolean isGeneratesNotInCNF(String w) {
            for (int i = 0; i < MAX_POWER * 2 + l * 2; i++) {
                HashSet<String> t = new HashSet<>();
                for (String c : terminals) {
                    int k = map.get(c);
                    if (k < to.size())
                        t.addAll(to.get(k));
                }
                terminals.addAll(t);
                for (ArrayList<String> rool : r) {
                    if (terminals.contains(rool.get(1))) {
                        if (terminals.contains(rool.get(2))) {
                            terminals.add(rool.get(0));
                        }
                    }
                }
            }
            boolean[][][] d = new boolean[w.length()][w.length()][l + MAX_POWER];
            for (int i = 0; i < w.length(); i++) {
                String h = String.valueOf(w.charAt(i));
                if (map.containsKey(h)) {
                    int k = map.get(h);
                    for (String s : to.get(k)) d[i][i][map.get(s)] = true;
                    d[i][i][k] = true;
                }
            }
            for (int m = 0; m < w.length(); m++)
                for (int i = 0; i < w.length() - m; i++) {
                    for (int j = i; j < i + m; j++) {
                        for (ArrayList<String> rool : r) {
                            if (d[i][j][map.get(rool.get(1))] && d[j + 1][i + m][map.get(rool.get(2))]) {
                                d[i][i + m][map.get(rool.get(0))] = true;
                            }
                        }

                    }
                    for (int t = 0; t < terminals.size() + 10; t++) {
                        for (ArrayList<String> rool : r) {
                            if (d[i][i + m][map.get(rool.get(1))] && (terminals.contains(rool.get(2)))) {
                                d[i][i + m][map.get(rool.get(0))] = true;
                            } else if (d[i][i + m][map.get(rool.get(2))] && terminals.contains(rool.get(1))) {
                                d[i][i + m][map.get(rool.get(0))] = true;
                            }
                        }
                        for (int k = 0; k < to.size(); k++) {
                            if (d[i][i + m][k]) for (String s : to.get(k)) d[i][i + m][map.get(s)] = true;
                        }
                    }

                }
            return d[0][w.length() - 1][map.get(start)];
        }

        public boolean isGenerates(String w) {
            boolean[][][] d = new boolean[map.size()][w.length()][w.length()];
            for (int i = 0; i < w.length(); i++) {
                for (String c : nonterminals) {
                    for (ArrayList<String> rool : rools.get(map.get(c))) {
                        d[map.get(c)][i][i] |= rool.size() == 1 && rool.get(0).equals(String.valueOf(w.charAt(i)));

                    }
                }
            }
//            out.print(nonterminals.size());
            for (int m = 0; m < w.length(); m++) {
                for (int i = 0; i < w.length() - m; i++) {
                    int j = i + m;
                    for (int k = i; k < j; k++) {
                        for (String c : nonterminals) {
                            for (ArrayList<String> rool : rools.get(map.get(c))) {
                                if (rool.size() == 2) {
                                    d[map.get(c)][i][j] = (d[map.get(c)][i][j] || (d[map.get(rool.get(0))][i][k] && d[map.get(rool.get(1))][k + 1][j]));
                                }
                            }

                        }
                    }
                }
            }
            return d[map.get(start)][0][w.length() - 1];

        }

        public void removeLongRools() {
            ArrayList<String> v = new ArrayList<>(map.keySet());
            int l = 0;
            for (String i : v) {
                int t = map.get(i);
                for (int i1 = rools.get(t).size() - 1; i1 >= 0; i1--) {
                    ArrayList<String> r = rools.get(t).get(i1);
                    if (r.size() > 2) {
                        String newNonTerminal = i;
                        for (int k = 0; k < r.size() - 2; k++) {
                            ArrayList<String> newTo = new ArrayList<>();
                            newTo.add(r.get(k));
                            newTo.add("L" + l);
                            this.addRool(newNonTerminal, newTo);
                            newNonTerminal = "L" + l;
                            l++;
                        }
                        ArrayList<String> newTo = new ArrayList<>();
                        newTo.add(r.get(r.size() - 2));
                        newTo.add(r.get(r.size() - 1));
                        this.addRool(newNonTerminal, newTo);
                        rools.get(t).remove(i1);
                    }
                }
                if (rools.get(t).size() == 0)
                    nonterminals.remove(i);

            }

        }

        public void removeNonGeneratingRools() {
            HashSet<String> nonGenerating = findNonGenerating();
            for (String t : map.keySet()) {
                int k = map.get(t);
                if (nonGenerating.contains(t)) {
                    rools.get(k).clear();
                    continue;
                }
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    for (String f : r) {
                        if (nonGenerating.contains(f)) {
                            rools.get(k).remove(i);
                            break;
                        }
                    }
                }
                if (rools.get(k).size() == 0)
                    nonterminals.remove(t);

            }
        }

        public void addRoolInCNF(String from, String rool) {
            if (!map.containsKey(from)) {
                map.put(from, from.charAt(0) - 'A');
            }
            if (rool.isEmpty()) {
                terminals.add(from);
            } else if (rool.length() == 1) {
                to.get(rool.charAt(0) - 'A').add(from);
                map.put(rool, rool.charAt(0) - 'A');
            } else {
                String s = from;
                while (rool.length() > 2) {
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(s);
                    temp.add("C" + l);
                    map.put("C" + l, MAX_POWER + l);
                    String f = String.valueOf(rool.charAt(rool.length() - 1));
                    if (!map.containsKey(String.valueOf(rool.charAt(rool.length() - 1)))) {
                        map.put(String.valueOf(rool.charAt(rool.length() - 1)), rool.charAt(rool.length() - 1) - 'A');
                    }
                    temp.add(f);
                    r.add(temp);
                    rool = rool.substring(0, rool.length() - 1);
                    s = "C" + l;
                    l++;
                }
                ArrayList<String> temp = new ArrayList<>();
                temp.add(s);
                if (!map.containsKey(s)) {
                    map.put(s, s.charAt(0) - 'A');
                }
                temp.add(String.valueOf(rool.charAt(0)));
                temp.add(String.valueOf(rool.charAt(1)));
                if (!map.containsKey(String.valueOf(rool.charAt(0)))) {
                    map.put(String.valueOf(rool.charAt(0)), rool.charAt(0) - 'A');
                }
                if (!map.containsKey(String.valueOf(rool.charAt(1)))) {
                    map.put(String.valueOf(rool.charAt(1)), rool.charAt(1) - 'A');
                }
                r.add(temp);
            }
        }

        public void removeUnreachableRools() {
            HashSet<String> unreachable = findUnreachable();
            for (String t : map.keySet()) {
                int k = map.get(t);
                if (unreachable.contains(t)) {
                    rools.get(k).clear();
                    continue;
                }
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    for (String f : r) {
                        if (unreachable.contains(f)) {
                            rools.get(k).remove(i);
                            break;
                        }
                    }
                }
                if (rools.get(k).size() == 0)
                    nonterminals.remove(t);

            }
        }

        public void removeUselessRools() {
            removeNonGeneratingRools();
            removeUnreachableRools();
        }

        private void generateRoolsWithOutEpsilon(String from, HashSet<String> epsilonMaking, ArrayList<String> prefix, ArrayList<String> rest) {
            if (rest.size() == 0) {
                if (prefix.size() > 0)
                    addRool(from, prefix);
                return;
            }
            ArrayList<String> newPrefix = new ArrayList<>(prefix);
            newPrefix.add(rest.get(0));
            ArrayList<String> newRest = new ArrayList<>();
            for (int i = 1; i < rest.size(); i++)
                newRest.add(rest.get(i));
            if (epsilonMaking.contains(rest.get(0))) {
                generateRoolsWithOutEpsilon(from, epsilonMaking, prefix, newRest);
            }
            generateRoolsWithOutEpsilon(from, epsilonMaking, newPrefix, newRest);
        }

        public void removeEpsilonMakingRools() {
            HashSet<String> epsilonMaking = findEpsilonMaking();
            boolean isEps = epsilonMaking.contains(start);
            for (String t : map.keySet()) {
                int k = map.get(t);
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    generateRoolsWithOutEpsilon(t, epsilonMaking, new ArrayList<String>(), r);
                }

            }
            for (String t : map.keySet()) {
                int k = map.get(t);
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    if (r.size() == 0)
                        rools.get(k).remove(i);
                }
                if (rools.get(k).size() == 0)
                    nonterminals.remove(t);
            }
            if (isEps) {
                addRool("S\'", start);
                addRool("S\'", "");
                start = "S\'";
            }

        }

        private HashSet<Pair<String, String>> unitPairs;

        private void addUnitPair(Pair<String, String> pair) {
            int k = map.get(pair.getValue());
            for (int i = 0; i < rools.get(k).size(); i++) {
                if (rools.get(k).get(i).size() == 1 && nonterminals.contains(rools.get(k).get(i).get(0))) {
                    Pair<String, String> newP = new Pair<>(pair.getKey(), rools.get(k).get(i).get(0));
                    if (!unitPairs.contains(newP)) {
                        unitPairs.add(newP);
                        addUnitPair(newP);
                    }
                }
            }
        }

        public void removeUnitRools() {
            unitPairs = new HashSet<>();
            for (String s : nonterminals) {
                unitPairs.add(new Pair<>(s, s));
            }
            HashSet<Pair<String, String>> temp = new HashSet<>(unitPairs);
            for (Pair<String, String> pair : temp) {
                addUnitPair(pair);
            }

            for (Pair<String, String> pair : unitPairs) {
                int k = map.get(pair.getValue());
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    if (!(r.size() == 1 && nonterminals.contains(r.get(0)))) {
                        addRool(pair.getKey(), r);
                    }
                }
            }

            for (String t : map.keySet()) {
                int k = map.get(t);
                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    if (r.size() == 1 && nonterminals.contains(r.get(0))) {
                        rools.get(k).remove(i);
                    }
                }
                if (rools.get(k).size() == 0)
                    nonterminals.remove(t);


            }

        }

        public void removeRoolsWithMoreTwoTerminals() {
            int l = 0;
            ArrayList<String> keys = new ArrayList<>(map.keySet());
            for (String t : keys) {
                int k = map.get(t);

                for (int i = rools.get(k).size() - 1; i >= 0; i--) {
                    ArrayList<String> r = rools.get(k).get(i);
                    if (r.size() >= 2) {
                        for (int f = 0; f < r.size(); f++) {
                            String u = r.get(f);
                            if (!nonterminals.contains(u)) {
                                rools.get(k).get(i).set(f, "U" + l);
                                addRool("U" + l, u);
                                l++;
                            }
                        }

                    }
                }

            }
        }

        public void convertToCNF() {
            removeEpsilonMakingRools();
            removeUnitRools();
            removeUselessRools();
            removeRoolsWithMoreTwoTerminals();
            removeLongRools();
        }

        @Override
        public String toString() {
            String s = "";
            int n = 0;
            for (ArrayList<ArrayList<String>> rool : rools) n += rool.size();
            s += n;
            s += " " + start + "\n";
            for (String t : map.keySet()) {
                for (ArrayList<String> r : rools.get(map.get(t))) {
                    s += t + " -> ";
                    for (String f : r) {
                        s += f;
                    }
                    s += "\n";
                }
            }

            return s;

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
            cfGrammar.addRoolInCNF(String.valueOf(s.charAt(0)), to);
        }
        // cfGrammar.convertToCNF();
        // out.print(cfGrammar);
        out.println(cfGrammar.isGeneratesNotInCNF(in.next()) ? "yes" : "no");
       /* HashSet<String> result = cfGrammar.findNonGenerating();
      //  result.addAll(cfGrammar.findUnreachable());
        ArrayList<String> answer = new ArrayList<>(result);
        answer.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String c : answer)
            out.print(c + " ");
*/
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