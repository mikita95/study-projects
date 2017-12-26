import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "isomorphism";

    public static void main(String[] args) {
        new A().run();
    }

    private class DFA {
        private ArrayList<ArrayList<Integer>> table;
        private HashSet<Integer> accept;
        private int power;
        private int statesNumber;
        private int start;
        private int transitionsNumber;
        private DFA other;
        private ArrayList<Boolean> visited;
        private ArrayList<Integer> colors;
        private ArrayList<Integer> useful;
        private ArrayList<Integer> valueNumeric, keyNumeric;
        int number = -1;

        public boolean isIsomorphic(DFA other) {
            this.other = other;
            this.remap(this.start);
            this.other.remap(this.other.start);
            if (statesNumber != other.statesNumber || this.accept.size() != this.other.accept.size() || this.transitionsNumber != this.other.transitionsNumber)
                return false;
            for (int i = 0; i < statesNumber; i++) {
                if (this.accept.contains(i) != this.other.accept.contains(this.other.keyNumeric.get(valueNumeric.get(i))))
                    return false;
            }
            for (int i = 0; i < statesNumber; i++) {
                int mapped = this.other.keyNumeric.get(valueNumeric.get(i));
                for (int c = 0; c < this.power; c++) {
                    int r1 = this.table.get(i).get(c);
                    int r2 = this.other.table.get(mapped).get(c);
                    if (r1 != -1 && r2 != -1) {
                        r1 = valueNumeric.get(r1);
                        r2 = this.other.valueNumeric.get(r2);
                    }
                    if (r1 != r2) return false;
                }
            }
            return true;
        }

        void remap(int v) {
            keyNumeric.set(++number, v);
            valueNumeric.set(v, number);
            visited.set(v, true);
            for (int i = 0; i < this.power; i++) {
                int r = table.get(v).get(i);
                if (r != -1 && !visited.get(r))
                    remap(r);
            }
        }

        public DFA(int statesNumber, int power) {
            table = new ArrayList<>();
            this.statesNumber = statesNumber;
            this.power = power;
            this.start = 0;
            for (int i = 0; i < statesNumber; i++) {
                table.add(new ArrayList<>());
                for (int k = 0; k < power; k++) {
                    table.get(i).add(-1);
                }
            }
            accept = new HashSet<>();
            valueNumeric = new ArrayList<>();
            keyNumeric = new ArrayList<>();
            visited = new ArrayList<>();
            for (int i = 0; i < this.statesNumber; i++) {
                visited.add(false);
                valueNumeric.add(0);
                keyNumeric.add(0);
            }
        }

        public DFA(int statesNumber, int power, int start) {
            table = new ArrayList<>();
            this.statesNumber = statesNumber;
            this.power = power;
            this.start = start;
            for (int i = 0; i < statesNumber; i++) {
                table.add(new ArrayList<>());
                for (int k = 0; k < power; k++) {
                    table.get(i).add(-1);
                }
            }
            accept = new HashSet<>();
        }

        public void addTransition(int start, int finish, char symbol) {
            int pos = symbol - 'a';
            if (table.get(start).get(pos) == -1) {
                table.get(start).set(pos, finish);
                transitionsNumber++;
            }
        }

        public void addAcceptState(int k) {
            accept.add(k);
        }

        public boolean isAcceptable(int k) {
            return accept.contains(k);
        }

        public Boolean isAccept(String word) {
            int pos = 1;
            for (int i = 0; i < word.length(); i++) {
                pos = table.get(pos).get(word.charAt(i) - 'a');
            }
            return accept.contains(pos);
        }

        public int countWordsOfLength(int length) {
            ArrayList<ArrayList<Integer>> d = new ArrayList<>();
            d.add(new ArrayList<>());
            for (int i = 1; i < table.size(); i++) {
                d.get(0).add(accept.contains(i) ? 1 : 0);
            }
            for (int i = 0; i < length; i++) {
                d.add(new ArrayList<>());
                for (int k = 1; k < table.size(); k++)
                    d.get(i + 1).add(0);
                for (int k = 1; k < table.size(); k++) {
                    for (int a = 0; a < power; a++)
                        if (table.get(k).get(a) != 0) {
                            d.get(i + 1).set(k - 1, (d.get(i).get(table.get(k).get(a) - 1) + d.get(i + 1).get(k - 1)) % 1000000007);
                        }
                }
            }
            return d.get(length).get(0);
        }

        private boolean dfs(int u, int v) {
            this.visited.set(u, true);
            if (this.accept.contains(u) != this.other.accept.contains(v))
                return false;
            boolean result = true;
            for (int i = 0; i < this.power; i++) {
                int t1 = this.table.get(u).get(i);
                int t2 = this.other.table.get(v).get(i);
                if (this.accept.contains(t1) ^ this.other.accept.contains(t2))
                    return false;
                if (t1 != -1 && !this.visited.get(t1))
                    result = result & dfs(t1, t2);
            }
            return result;
        }

        public DFA minimize() {
            ArrayList<ArrayList<ArrayList<Integer>>> Inv = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                Inv.add(new ArrayList<>());
                for (int c = 0; c < power; c++)
                    Inv.get(i).add(new ArrayList<>());
            }
            int[][][] invTemp = new int[statesNumber][power][];
            int[][] invSize = new int[statesNumber][power];
            for (int i = 0; i < statesNumber; ++i) {
                for (int j = 0; j < power; ++j) {
                    if (table.get(i).get(j) != -1)
                        invSize[table.get(i).get(j)][j]++;
                }
            }
            for (int i = 0; i < statesNumber; ++i) {
                for (int j = 0; j < power; ++j) {
                    invTemp[i][j] = new int[invSize[i][j]];
                }
            }
            for (int i = 0; i < statesNumber; ++i) {
                for (int j = 0; j < power; ++j) {
                    int r = table.get(i).get(j);
                    if (r != -1)
                        invTemp[r][j][--invSize[r][j]] = i;
                }
            }
            for (int i = 0; i < statesNumber; i++) {
                for (int j = 0; j < power; j++) {
                    for (int k = 0; k < invTemp[i][j].length; k++)
                        Inv.get(i).get(j).add(invTemp[i][j][k]);
                }
            }
            ArrayList<Integer> Class = new ArrayList<>();
            ArrayList<ArrayList<Integer>> P = new ArrayList<>();
            ArrayList<Integer> F = new ArrayList<>(this.accept);
            ArrayList<Integer> QmF = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                Class.add(0);
                if (!this.accept.contains(i)) {
                    QmF.add(i);
                    Class.set(i, 1);
                }
            }
            P.add(F);
            P.add(QmF);
            Queue<Pair<ArrayList<Integer>, Integer>> queue = new LinkedList<>();
            for (int i = 0; i < power; i++) {
                if (F.size() < QmF.size()) {
                    queue.add(new Pair<>(F, i));
                } else
                    queue.add(new Pair<>(QmF, i));
            }
            HashMap<Integer, ArrayList<Integer>> Involved;
            while (!queue.isEmpty()) {
                Pair<ArrayList<Integer>, Integer> pair = queue.poll();
                Involved = new HashMap<>();
                for (Integer q : pair.getKey()) {
                    for (Integer r : Inv.get(q).get(pair.getValue())) {

                        int i = Class.get(r);
                        if (Involved.get(i) == null) {
                            Involved.put(i, new ArrayList<>());
                        }
                        Involved.get(i).add(r);

                    }
                }
                for (Integer i : Involved.keySet()) {
                    if (Involved.get(i).size() < P.get(i).size()) {
                        P.add(new ArrayList<>());
                        int j = P.size() - 1;
                        for (Integer r : Involved.get(i)) {
                            P.get(i).remove(P.get(i).indexOf(r));
                            P.get(j).add(r);
                        }
                        if (P.get(j).size() > P.get(i).size())
                            Collections.swap(P, i, j);
                        for (Integer r : P.get(j))
                            Class.set(r, j);
                        for (int c = 0; c < power; c++) {
                            queue.add(new Pair<>(P.get(j), c));
                        }
                    }
                }
            }
            int k = -1;
            for (int i = 0; i < P.size(); i++)
                if (P.get(i).contains(this.start)) {
                    k = i;
                    break;
                }
            if (k != -1) {
                Collections.swap(P, 0, k);
                Class.set(start, 0);
                for (Integer i : P.get(0))
                    Class.set(i, 0);
                for (Integer i : P.get(k)) {
                    Class.set(i, k);
                }
                int t = 0;
                for (ArrayList<Integer> aP : P)
                    if (aP.isEmpty())
                        t++;
                DFA min = new DFA(P.size() - t, power);
                for (int i = 0; i < statesNumber; i++) {
                    if (isAcceptable(i))
                        min.addAcceptState(Class.get(i));
                    for (int c = 0; c < power; c++) {
                        int r = table.get(i).get(c);
                        if (r != -1) {
                            min.addTransition(Class.get(i), Class.get(r), (char) (c + 'a'));
                        }
                    }
                }
                return min;

            }
            return null;
        }

        public DFA removeUnreachableStates() {
            BitSet reachableStates = new BitSet(statesNumber);
            BitSet newStates = new BitSet(statesNumber);
            reachableStates.set(0, true);
            newStates.set(0, true);
            do {
                BitSet temp = new BitSet();
                for (int p = newStates.nextSetBit(0); p >= 0; p = newStates.nextSetBit(p + 1)) {
                    for (int c = 0; c < power; c++) {
                        int r = table.get(p).get(c);
                        if (r != -1)
                            temp.set(r, true);
                    }
                }
                newStates = temp;
                newStates.andNot(reachableStates);
                reachableStates.or(newStates);
            } while (!newStates.isEmpty());
            for (int i = 0; i < statesNumber; i++)
                newStates.set(i, true);
            BitSet unreachableStates = newStates;
            unreachableStates.andNot(reachableStates);
            if (unreachableStates.cardinality() == 0 || reachableStates.cardinality() == 0)
                return this;
            DFA result = new DFA(reachableStates.cardinality(), power);
            HashMap<Integer, Integer> map = new HashMap<>();
            int k = -1;
            for (int p = reachableStates.nextSetBit(0); p >= 0; p = reachableStates.nextSetBit(p + 1)) {
                k++;
                map.put(p, k);
            }
            for (int p = reachableStates.nextSetBit(0); p >= 0; p = reachableStates.nextSetBit(p + 1)) {
                for (int c = 0; c < power; c++) {
                    int r = table.get(p).get(c);
                    if (r != -1) {
                        result.addTransition(map.get(p), map.get(r), (char) (c + 'a'));
                    }
                }
                if (isAcceptable(p))
                    result.addAcceptState(map.get(p));
            }
            return result;
        }

        public ArrayList<ArrayList<Integer>> getTable() {
            return table;
        }

        public void setTable(ArrayList<ArrayList<Integer>> table) {
            this.table = table;
        }

        public HashSet<Integer> getAccept() {
            return accept;
        }

        public void setAccept(HashSet<Integer> accept) {
            this.accept = accept;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public int getStatesNumber() {
            return statesNumber;
        }

        public void setStatesNumber(int statesNumber) {
            this.statesNumber = statesNumber;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getTransitionsNumber() {
            return transitionsNumber;
        }

        private void dfsForUseful(int k) {
            useful.set(k, 1);
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++)
                    if (table.get(i).get(c) == k) {
                        if (useful.get(i) != 1)
                            dfsForUseful(i);
                    }
            }
        }

        private boolean dfsForCycles(int v) {
            colors.set(v, 1);
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    if (table.get(v).get(c) == i) {
                        if (useful.get(i) == 0) continue;
                        if (colors.get(i) == 0) {
                            if (dfsForCycles(i))
                                return true;
                        } else if (colors.get(i) == 1) {
                            return true;
                        }
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
            for (int i = 0; i < statesNumber; i++) {
                int n = 0;
                for (int c = 0; c < power; c++) {
                    if (table.get(v).get(c) == i && useful.get(i) == 1) {
                        n++;
                    }
                }
                if (n > 0)
                    p = (p + paths(i) * n) % 1000000007;
            }
            return p;
        }

        public int countWords() {
            useful = new ArrayList<>();
            colors = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                useful.add(0);
                colors.add(0);
            }
            accept.forEach(this::dfsForUseful);
            return dfsForCycles(0) ? -1 : paths(0);
        }

        @Override
        public String toString() {
            String s = "";
            s += (statesNumber + " " + transitionsNumber + " " + accept.size()) + "\n";
            int f = 0;
            for (Integer i : accept) {
                if (f == 0) {
                    s += "" + (i + 1);
                    f = 1;
                } else s += " " + (i + 1);
            }
            s += "\n";
            for (int i = 0; i < statesNumber; i++)
                for (int c = 0; c < power; c++) {
                    int r = table.get(i).get(c);
                    if (r != -1) {
                        s += (i + 1) + " " + (r + 1) + " " + (char) (c + 'a') + "\n";
                    }
                }
            return s;
        }
    }


    void solve() throws IOException {
        int n, m, k;
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        DFA a = new DFA(n, 26);
        for (int i = 0; i < k; i++)
            a.addAcceptState(in.nextInt() - 1);
        for (int i = 0; i < m; i++) {
            a.addTransition(in.nextInt() - 1, in.nextInt() - 1, in.next().charAt(0));
        }
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        DFA b = new DFA(n, 26);
        for (int i = 0; i < k; i++)
            b.addAcceptState(in.nextInt() - 1);
        for (int i = 0; i < m; i++) {
            b.addTransition(in.nextInt() - 1, in.nextInt() - 1, in.next().charAt(0));
        }
        out.println(a.isIsomorphic(b) ? "YES" : "NO");
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