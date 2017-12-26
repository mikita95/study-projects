import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class C {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "minimization";

    public static void main(String[] args) {
        new C().run();
    }

    private static class DFA {
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
        public void merge(DFA b) {
            for (int i = 0; i < b.statesNumber; i++) {
                table.add(new ArrayList<>());
                for (int c = 0; c < power; c++) {
                    table.get(i + this.statesNumber).add(-1);
                }
            }
            this.accept.addAll(b.accept.stream().map(a -> a + statesNumber).collect(Collectors.toList()));
            for (int i = 0; i < b.statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    int r = b.table.get(i).get(c);
                    if (r != -1) {
                        this.table.get(i + this.statesNumber).set(c, r + statesNumber);
                    }
                }
            }
            statesNumber += b.statesNumber;
            addHell();
        }

        public ArrayList<HashSet<Integer>> slowPartition() {
            ArrayList<HashSet<Integer>> P = new ArrayList<>();
            LinkedList<Pair<HashSet<Integer>, Integer>> Q = new LinkedList<>();
            HashSet<Integer> F = new HashSet<>();
            HashSet<Integer> FmQ = new HashSet<>();
            for (int i = 0; i < statesNumber; i++) {
                if (isAcceptable(i))
                    F.add(i);
                else FmQ.add(i);
            }
            P.add(F);
            P.add(FmQ);
            for (int c = 0; c < power; c++) {
                Q.add(new Pair<>(F, c));
                Q.add(new Pair<>(FmQ, c));
            }
            while (!Q.isEmpty()) {
                ArrayList<HashSet<Integer>> Inv = new ArrayList<>();
                Pair<HashSet<Integer>, Integer> p = Q.poll();
                for(HashSet<Integer> R: P) {
                    HashSet<Integer> r1 = new HashSet<>();
                    HashSet<Integer> r2 = new HashSet<>();
                    for (Integer i: R) {
                        int to = table.get(i).get(p.getValue());
                        if (p.getKey().contains(to))
                            r1.add(i);
                        else r2.add(i);
                    }
                    if (!r1.isEmpty() && !r2.isEmpty()) {
                        Inv.add(r1);
                        Inv.add(r2);
                        Q.add(new Pair<>(r1, p.getValue()));
                        Q.add(new Pair<>(r2, p.getValue()));
                    } else {
                        Inv.add(R);
                    }
                }
                P = Inv;
            }
            return P;
        }

        public boolean isIsomorphic(DFA other) {
            if (statesNumber == 1 || other.statesNumber == 1) {
                return statesNumber == other.statesNumber;
            }
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
                int p = this.other.keyNumeric.get(valueNumeric.get(i));
                for (int c = 0; c < this.power; c++) {
                    int r1 = this.table.get(i).get(c);
                    int r2 = this.other.table.get(p).get(c);
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
                valueNumeric.add(-1);
                keyNumeric.add(-1);
            }
        }

        private void addHell() {
            statesNumber++;
            table.add(new ArrayList<>());
            for (int c = 0; c < power; c++)
                table.get(statesNumber - 1).add(-1);
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    if (table.get(i).get(c) == -1) {
                        table.get(i).set(c, statesNumber - 1);
                    }
                }
            }
            for (int c = 0; c < power; c++)
                table.get(statesNumber - 1).set(c, statesNumber - 1);
        }

        private ArrayList<ArrayList<ArrayList<Integer>>> getInvertedTable() {
            ArrayList<ArrayList<ArrayList<Integer>>> result = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                result.add(new ArrayList<>());
                for (int c = 0; c < power; c++)
                    result.get(i).add(new ArrayList<>());
            }
            for (int i = 0; i < statesNumber; i++)
                for (int c = 0; c < power; c++) {
                    result.get(table.get(i).get(c)).get(c).add(i);
                }
            return result;
        }

        public ArrayList<HashSet<Integer>> middlePartition() {
            ArrayList<ArrayList<ArrayList<Integer>>> par = getInvertedTable();
            ArrayList<ArrayList<Boolean>> a = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++) {
                a.add(new ArrayList<>());
                for (int k = 0; k < statesNumber; k++)
                    a.get(i).add(false);
            }
            LinkedList<Pair<Integer, Integer>> q = new LinkedList<>();
            for (int i = 0; i < statesNumber; i++) {
                for (int k = 0; k < statesNumber; k++) {
                    if ((accept.contains(i) && !accept.contains(k)) || (!accept.contains(i) && accept.contains(k))) {
                        a.get(i).set(k, true);
                        q.add(new Pair<>(i, k));
                    }
                }
            }
            while (!q.isEmpty()) {
                Pair<Integer, Integer> p = q.poll();
                for (int c = 0; c < power; c++) {
                    for (int i = 0; i < par.get(p.getKey()).get(c).size(); i++) {
                        for (int k = 0; k < par.get(p.getValue()).get(c).size(); k++) {
                            int r1 = par.get(p.getKey()).get(c).get(i);
                            int r2 = par.get(p.getValue()).get(c).get(k);
                            if (a.get(r1).get(r2)) continue;
                            a.get(r1).set(r2, true);
                            q.add(new Pair<>(r1, r2));
                        }
                    }
                }
            }
            ArrayList<HashSet<Integer>> P = new ArrayList<>();
            ArrayList<Boolean> Class = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++)
                Class.add(false);
            for (int i = 0; i < statesNumber; i++) {
                if (Class.get(i)) continue;
                HashSet<Integer> newClass = new HashSet<>();
                newClass.add(i);
                Class.set(i ,true);
                for (int k = 0; k < statesNumber; k++) {
                    if (!a.get(i).get(k) && !Class.get(k)) {
                        newClass.add(k);
                        Class.set(k, true);
                    }
                }
                P.add(newClass);
            }
            return P;
        }

        public void slowMinimize() {
            addHell();
            removeUnreachableStates();
            if (accept.isEmpty()) {
                statesNumber = 1;
                transitionsNumber = 0;
                accept.add(0);
                table = new ArrayList<>();
                return;
            }
            ArrayList<HashSet<Integer>> P = middlePartition();
            for (int i = 0; i < P.size(); i++) {
                if (P.get(i).contains(statesNumber - 1)) {
                    Collections.swap(P, i, P.size() - 1);
                }
            }
            ArrayList<Integer> map = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++)
                map.add(-1);
            for (int i = 0; i < statesNumber; i++) {
                for (int k = 0; k < P.size(); k++) {
                    if (P.get(k).contains(i))
                        map.set(i, k);
                }
            }
            HashSet<Integer> newAccept = new HashSet<>();
            for (Integer i: accept)
                newAccept.add(map.get(i));
            ArrayList<ArrayList<Integer>> newTable = new ArrayList<>();
            for (int i = 0; i < P.size(); i++) {
                newTable.add(new ArrayList<>());
                for (int c = 0; c < power; c++)
                    newTable.get(i).add(-1);
            }

            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    newTable.get(map.get(i)).set(c, map.get(table.get(i).get(c)));
                }
            }
            table = newTable;
            accept = newAccept;
            statesNumber = table.size();
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    if (table.get(i).get(c) == statesNumber - 1) {
                        table.get(i).set(c, -1);
                    }
                }
            }
            table.remove(table.size() - 1);
            statesNumber--;
            int newTransitionsNumber = 0;
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0 ;c < power; c++)
                    if (table.get(i).get(c) != -1)
                        newTransitionsNumber++;
            }
            this.transitionsNumber = newTransitionsNumber;
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

        public ArrayList<HashSet<Integer>> fastPartition() {
            ArrayList<ArrayList<ArrayList<Integer>>> Inv = getInvertedTable();
            ArrayList<Integer> Class = new ArrayList<>();
            ArrayList<HashSet<Integer>> P = new ArrayList<>();
            HashSet<Integer> F = new HashSet<>(this.accept);
            HashSet<Integer> QmF = new HashSet<>();
            for (int i = 0; i < statesNumber; i++) {
                Class.add(0);
                if (!this.accept.contains(i)) {
                    QmF.add(i);
                    Class.set(i, 1);
                }
            }
            P.add(F);
            P.add(QmF);
            Queue<Pair<HashSet<Integer>, Integer>> queue = new LinkedList<>();
            for (int i = 0; i < power; i++) {
                if (F.size() <= QmF.size()) {
                    queue.add(new Pair<>(F, i));
                } else
                    queue.add(new Pair<>(QmF, i));
            }
            HashMap<Integer, ArrayList<Integer>> Involved;
            while (!queue.isEmpty()) {
                Pair<HashSet<Integer>, Integer> pair = queue.poll();
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
                        P.add(new HashSet<>());
                        int j = P.size() - 1;
                        for (Integer r : Involved.get(i)) {
                            P.get(i).remove(r);
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
            return P;

        }

        public void minimize() {
            addHell();
            removeUnreachableStates();
            if (accept.isEmpty()) {
                statesNumber = 1;
                transitionsNumber = 0;
                accept.add(0);
                table = new ArrayList<>();
                return;
            }
            ArrayList<HashSet<Integer>> P = fastPartition();
            for (int i = 0; i < P.size(); i++) {
                if (P.get(i).contains(statesNumber - 1)) {
                    Collections.swap(P, i, P.size() - 1);
                }
            }
            ArrayList<Integer> map = new ArrayList<>();
            for (int i = 0; i < statesNumber; i++)
                map.add(-1);
            for (int i = 0; i < statesNumber; i++) {
                for (int k = 0; k < P.size(); k++) {
                    if (P.get(k).contains(i))
                        map.set(i, k);
                }
            }
            HashSet<Integer> newAccept = new HashSet<>();
            for (Integer i: accept)
                newAccept.add(map.get(i));
            ArrayList<ArrayList<Integer>> newTable = new ArrayList<>();
            for (int i = 0; i < P.size(); i++) {
                newTable.add(new ArrayList<>());
                for (int c = 0; c < power; c++)
                    newTable.get(i).add(-1);
            }

            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    newTable.get(map.get(i)).set(c, map.get(table.get(i).get(c)));
                }
            }
            table = newTable;
            accept = newAccept;
            statesNumber = table.size();
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0; c < power; c++) {
                    if (table.get(i).get(c) == statesNumber - 1) {
                        table.get(i).set(c, -1);
                    }
                }
            }
            table.remove(table.size() - 1);
            statesNumber--;
            int newTransitionsNumber = 0;
            for (int i = 0; i < statesNumber; i++) {
                for (int c = 0 ;c < power; c++)
                    if (table.get(i).get(c) != -1)
                        newTransitionsNumber++;
            }
            this.transitionsNumber = newTransitionsNumber;
        }

        public void removeUnreachableStates() {
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
                return;
            //DFA result = new DFA(reachableStates.cardinality(), power);
            HashMap<Integer, Integer> map = new HashMap<>();
            int k = -1;
            for (int p = reachableStates.nextSetBit(0); p >= 0; p = reachableStates.nextSetBit(p + 1)) {
                k++;
                map.put(p, k);
            }

            HashSet<Integer> newAccept = new HashSet<>();
            ArrayList<ArrayList<Integer>> newTable = new ArrayList<>();
            int newTransitionsNumber = 0;
            for (int i = 0; i < reachableStates.cardinality(); i++) {
                newTable.add(new ArrayList<>());
                for (int c = 0; c < power; c++) {
                    newTable.get(i).add(-1);
                }
            }
            for (int p = reachableStates.nextSetBit(0); p >= 0; p = reachableStates.nextSetBit(p + 1)) {
                for (int c = 0; c < power; c++) {
                    int r = table.get(p).get(c);
                    if (r != -1) {
                        newTable.get(map.get(p)).set(c, map.get(r));
                        newTransitionsNumber++;
                    }
                }
                if (isAcceptable(p))
                    newAccept.add(map.get(p));
            }
            this.table = newTable;
            this.accept = newAccept;
            this.transitionsNumber = newTransitionsNumber;
            this.statesNumber = reachableStates.cardinality();
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
            for (int i = 0; i < table.size(); i++)
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
        a.slowMinimize();

        out.println(a.toString());
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