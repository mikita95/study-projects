import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class G {
    private final static String fileName = "planaritycheck";
    ArrayList<ArrayList<Integer>> graph;
    private final static int maxN = 6;
    int n;

    private final static String no = "1111111111";

    class Pair<S, T> implements Comparable<Pair<S, T>> {
        final S first;
        final T second;
        final boolean e1Comparable;
        final boolean e2Comparable;

        Pair(final S first, final T second) {
            this.first = first;
            this.second = second;
            this.e1Comparable = (first instanceof Comparable);
            this.e2Comparable = (second instanceof Comparable);
        }

        public int compareTo(Pair<S, T> o) {
            if (e1Comparable) {
                final int k = ((Comparable<S>) first).compareTo(o.first);
                if (k > 0) return 1;
                if (k < 0) return -1;
            }

            if (e2Comparable) {
                final int k = ((Comparable<T>) second).compareTo(o.second);
                if (k > 0) return 1;
                if (k < 0) return -1;
            }

            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                final Pair<S, T> o = (Pair<S, T>) obj;
                return (first.equals(o.first) && second.equals(o.second));
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 19 * hash + (first != null ? first.hashCode() : 0);
            hash = 19 * hash + (second != null ? second.hashCode() : 0);
            return hash;
        }
    }

    class Edge {
        int x;
        int y;
        long weight;

        Edge(int x, int y, long weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }



    void checkPlanarity(int n) {
        ArrayList<ArrayList<Boolean>> gr = new ArrayList<ArrayList<Boolean>>();
        if (n >= maxN - 1) {
            if (n == maxN) {
                int i = 0;
                while (i < maxN) {
                    int h = 0;
                    while (h < maxN) {
                        int c = h + 1;
                        while (c < maxN) {
                            if (gr.get(i).get(h)) {
                                if (gr.get(i).get(c)) {
                                    gr.get(i).set(c, false);
                                    gr.get(c).set(i, false);
                                    gr.get(i).set(h, false);
                                    gr.get(h).set(i, false);
                                    boolean isSuccess = gr.get(h).get(c);
                                    gr.get(h).set(c, true);
                                    gr.get(c).set(h, true);
                                    gr.get(i).set(c, true);
                                    gr.get(c).set(i, true);
                                    gr.get(i).set(h, true);
                                    gr.get(h).set(i, true);
                                    gr.get(h).set(c, isSuccess);
                                    gr.get(c).set(h, isSuccess);
                                }
                            }
                            c++;
                        }
                        h++;
                    }
                    i++;
                }
            }
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

        public String list() {
            return tokenizer.nextToken();
        }

    }

    boolean checkFive(int m) {
        int i = 0;
        while (i < maxN) {
            if (graph.get(i).size() <= 1)
                if ((maxN + 4) == (m - graph.get(i).size()))
                    return true;
            int cnt = 0;
            ArrayList<Boolean> used = new ArrayList<Boolean>();
            for (int us = 0; us < maxN; us++)
                used.add(false);

            if (maxN - 4 <= graph.get(i).size()) {
                ArrayList<Integer> integers = graph.get(i);
                for (int i1 = 0, integersSize = integers.size(); i1 < integersSize; i1++) {
                    int tmp = integers.get(i1);
                    for (Iterator<Integer> iterator = graph.get(tmp).iterator(); iterator.hasNext(); ) {
                        int dist = iterator.next();
                        if (graph.get(i).contains(dist)) {
                            if (!used.get(dist)) {
                                if (dist != i) {
                                    cnt++;
                                }
                            }
                        }
                    }
                    used.set(tmp, true);
                }
            }
            if (graph.get(i).size() - cnt == 1 && m - graph.get(i).size() + 1 == maxN + 4 && graph.get(i).size() > 2) {
                return true;
            } else if (!(cnt != 0 || m - graph.get(i).size() + 1 != maxN + 4 || graph.get(i).size() != 2)) {
                return true;
            } else if (m - graph.get(i).size() == maxN + 4) {
                return true;
            }
            i++;
        }
        return false;
    }


    boolean checkBiPart() {
        boolean check = false;
        int i = 0;
        while (!(i >= maxN || check)) {
            for (int j = 0; !(j >= maxN || check); j++) {
                if (j == i)
                    continue;
                here:
                for (int k = 0; k < maxN; k++) {
                    if (k == i || k == j)
                        continue;
                    int cnt = 0;
                    int pp = 0;
                    while (pp < maxN) {
                        if (pp != i)
                            if (pp != k)
                                if (!(pp == j || !graph.get(i).contains(pp) || !graph.get(j).contains(pp))) {
                                    if (graph.get(k).contains(pp)) {
                                        cnt++;
                                    }
                                }
                        pp++;
                    }
                    switch (cnt) {
                        case maxN - 3:
                            check = true;
                            break here;
                    }
                }
            }
            i++;
        }
        return check;
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(fileName + ".out"));
        int testsCount = in.nextInt();
        for (int i = 0; i < testsCount; i++) {
            String s = in.list();

            for (n = 1; n <= maxN; n++)
                if (s.length() == (n - 1) * n >> 1)
                    break;


            if (s.equals(no)) {
                out.println("NO");
                continue;
            }
            graph = new ArrayList<ArrayList<Integer>>();
            int edgeCount = 0;
            if (!(n > maxN - 2 && !(!s.equals(no) && n == maxN - 1))) { out.println("YES"); continue; }
            for (int x = 0; x < n; x++) {
                graph.add(new ArrayList<Integer>());
            }
            int pl = 0;
            for (int h = 0; h < n; h++)
                for (int k = 0; k < h; k++) {
                    if (s.charAt(pl++) == '1') {
                        edgeCount++;
                        graph.get(h).add(k);
                        graph.get(k).add(h);
                    }
                }
            checkPlanarity(maxN - 3);

            if (checkBiPart())
                out.println("NO");
            else if (checkFive(edgeCount))
                out.println("NO");
            else
                out.println("YES");
        }


        out.close();
    }

    public static void main(String[] args) {
        new G().run();
    }

    public void run() {
        try {
            solve();
        } catch (IOException ignored) {
        }
    }




}