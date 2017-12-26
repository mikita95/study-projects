import java.io.*;
import java.util.*;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "check";

    ArrayList<HashSet<Integer>> sets = new ArrayList<>();

    public static void main(String[] args) {
        new D().run();
    }

    boolean checkFirstAxiom() {
        return sets.contains(Collections.emptySet());
    }

    public static ArrayList<HashSet<Integer>> powerArrayList(Set<Integer> originalSet) {
        ArrayList<HashSet<Integer>> sets = new ArrayList<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<Integer> list = new ArrayList<>(originalSet);
        Integer head = list.get(0);
        Set<Integer> rest = new HashSet<>(list.subList(1, list.size()));
        for (HashSet<Integer> set : powerArrayList(rest)) {
            HashSet<Integer> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;

    }

    boolean checkSecondAxiom() {
        for (HashSet<Integer> set : sets) {
            ArrayList<HashSet<Integer>> subsets = powerArrayList(set);
            for (HashSet<Integer> subset : subsets) {
                if (!sets.contains(subset))
                    return false;
            }
        }
        return true;
    }

    boolean checkThirdAxiom() {
        sets.sort(new Comparator<HashSet<Integer>>() {
            @Override
            public int compare(HashSet<Integer> o1, HashSet<Integer> o2) {
                return Integer.compare(o2.size(), o1.size());
            }
        });
        for (int i = 0; i < sets.size() - 1; i++) {
            for (int k = i + 1; k < sets.size(); k++) {
                HashSet<Integer> s1 = sets.get(i);
                HashSet<Integer> s2 = sets.get(k);
                if (s1.size() == s2.size())
                    break;
                s1.removeAll(s2);
                boolean flag = false;
                for (Integer e : s1) {
                    s2.add(e);
                    if (sets.contains(s2)) {
                        s2.remove(e);
                        flag = true;
                        break;
                    }
                    s2.remove(e);
                }
                if (!flag)
                    return false;
            }
        }
        return true;
    }

    ArrayList<Integer> masks = new ArrayList<>();
    HashSet<Integer> set = new HashSet<>();
    int answerMask, n, flag;

    void check() {
        if (!set.contains(answerMask)) {
            flag = 1;
        }
    }

    int get_cnt(int x) {
        int i, res = 0;
        for (i = 0; i < n; i++) {
            if ((x & (1 << i)) >= 0) {
                res++;
            }
        }
        return res;
    }


    void go(int x, int ind) {
        if (ind == n) {
            check();
            return;
        }
        if ((x & (1 << ind)) >= 0) {
            go(x, ind + 1);
            answerMask = (answerMask | (1 << ind));
            go(x, ind + 1);
            answerMask = (answerMask ^ (1 << ind));
        } else {
            go(x, ind + 1);
        }
    }


    void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        boolean first = false;
        for (int i = 0; i < m; i++) {
            int c = in.nextInt();
            if (c == 0)
                first = true;
            int mask = 0;
            for (int j = 0; j < c; j++) {
                mask |= 1 << (in.nextInt() - 1);
            }
            masks.add(mask);
            set.add(mask);
        }
        if (!first) {
            out.print("NO");
            return;
        }
        for (int i = 0; i < masks.size(); i++) {
            flag = 0;
            answerMask = 0;
            go(masks.get(i), 0);
            if (flag != 0) {
                out.print("NO");
                return;
            }
        }
        int Min = 0;
        int Max = 0;
        for (int i = 0; i < masks.size(); i++) {
            for (int j = i + 1; j < masks.size(); j++) {
                if (get_cnt(masks.get(i)) != get_cnt(masks.get(j))) {
                    if (get_cnt(masks.get(j)) < get_cnt(masks.get(j))) {
                        Min = masks.get(i);
                        Max = masks.get(j);
                    } else {
                        Min = masks.get(j);
                        Max = masks.get(i);
                    }
                    int flag1 = 0;
                    for (int ii = 0; ii < n; ii++) {
                        if ((Max & (1 << ii)) >= 0 && ((Min & (1 << ii)) == 0)) {
                            if (set.contains(Min | (1 << ii))) {
                                flag1 = 1;
                            }
                        }
                    }
                    if (flag1 == 0) {
                        out.print("NO");
                        return;
                    }
                }
            }
        }
        /*if (!checkFirstAxiom() || !checkSecondAxiom() || !checkThirdAxiom()) {
            out.print("NO");
            return;
        }*/
        out.print("YES");
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