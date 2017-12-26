import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "rainbow";

    private abstract class Matroid<X> {
        protected List<X> independentSets;

        public abstract List<X> accept();

        public Matroid() {
            independentSets = null;
        }

        public List<X> greedy(final Comparator<X> comparator) {
            List<X> list = this.independentSets;
            Collections.sort(list, comparator);
            return accept();
        }

        public List<X> greedy(final Function<X, Integer> w) {
            List<X> list = this.independentSets;
            Collections.sort(list, new Comparator<X>() {
                @Override
                public int compare(X o1, X o2) {
                    return w.apply(o2) - w.apply(o1);
                }
            });
            return accept();
        }
    }
    private class ScheduleMatroid extends Matroid<Pair<Integer, Integer>> {

        public ScheduleMatroid(LinkedList<Pair<Integer, Integer>> set) {
            this.independentSets = set;
        }

        @Override
        public List<Pair<Integer, Integer>> accept() {
            List<Pair<Integer, Integer>> answer = new ArrayList<>();
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (Pair<Integer, Integer> p : independentSets) {
                if (priorityQueue.size() < p.getKey()) {
                    priorityQueue.add(p.getValue());
                } else if (!priorityQueue.isEmpty()) {
                    if (priorityQueue.peek() < p.getValue()) {
                        answer.add(new Pair<>(0, priorityQueue.poll()));
                        priorityQueue.add(p.getValue());
                    } else {
                        answer.add(p);
                    }
                } else {
                    answer.add(p);
                }
            }
            return answer;
        }
    }

    private static final int MAX_M = 10010;
    private static final int MAX_N = 210;
    private static final long INF = (long) (1e9 + 9);

    ArrayList<Long> places;
    ArrayList<Integer> answer, a, b, c, colors, set, weights, values,
            points, priorities, states, data, queue, independent;

    int finish, high, low, result, minimum, insideData;
    ArrayList<ArrayList<Integer>> graph;

    ArrayList<Integer> used, temp;
    boolean flag;
    int n, m;


    void init() {
        set = new ArrayList<>();
        weights = new ArrayList<>();
        colors = new ArrayList<>();
        a = new ArrayList<>();
        b = new ArrayList<>();
        c = new ArrayList<>();
        independent = new ArrayList<>();
        points = new ArrayList<>();
        states = new ArrayList<>();
        values = new ArrayList<>();
        places = new ArrayList<>();
        priorities = new ArrayList<>();
        data = new ArrayList<>();
        queue = new ArrayList<>();
        answer = new ArrayList<>();
        graph = new ArrayList<>();
        for (int i = 0; i < MAX_N; i++) {
            set.add(i);
            weights.add(0);
            colors.add(0);
        }
    }

    void reverseColors() {
        int i = 0;
        while (i < m) {
            if ((take(a.get(i)) != take(b.get(i))))
                if ((colors.get(c.get(i)) == 0)) {
                    int f = a.get(i);
                    int g = b.get(i);
                    f = take(f);
                    g = take(g);
                    if (f != g) {
                        if (weights.get(f) < weights.get(g)) Collections.swap(weights, f, g);
                        set.set(g, f);
                        if (weights.get(f).equals(weights.get(g)))
                            weights.set(f, weights.get(f) + 1);
                    }
                    colors.set(c.get(i), 1);
                    answer.add(i);
                }
            i++;
        }
    }

    int countValues() {
        int res = 0;
        for (int i = 0; i < m; i++) {
            if (points.get(i) == 0) {
                values.set(res, i);
                res++;
            }
        }
        return res;
    }

    int take(int o) {
        if (set.get(o) == o)
            return o;
        else {
            set.set(o, take(set.get(o)));
            return set.get(o);
        }
    }

    void buildGraphOfChanges() {
        for (int i = 0; i < answer.size(); i++) {
            for (int k = 0; k < MAX_N; k++) set.set(k, k);

            Collections.fill(weights, 0);

            for (Integer an : answer) {
                if (independent.get(answer.get(i)).equals(an)) continue;
                int f = a.get(an);
                int g = b.get(an);
                f = take(f);
                g = take(g);
                if (f != g) {
                    if (weights.get(f) < weights.get(g)) Collections.swap(weights, f, g);
                    set.set(g, f);
                    if (weights.get(f).equals(weights.get(g)))
                        weights.set(f, weights.get(f) + 1);
                }
            }
            for (int k = 0; k < finish; k++) {
                if (take(a.get(values.get(k))) != take(b.get(values.get(k))))
                    graph.get(independent.get(answer.get(i))).add(independent.get(values.get(k)));
            }
        }
    }

    void setColorTree() {
        for (Integer an1 : answer) colors.set(c.get(an1), 1);

        for (Integer an1 : answer) {
            colors.set(c.get(an1), 0);
            for (int k = 0; k < finish; k++) {
                if (colors.get(c.get(values.get(k))) == 0)
                    graph.get(independent.get(values.get(k))).add(independent.get(an1));
            }
            colors.set(c.get(an1), 1);
        }
    }

    int countLow() {
        for (int i = 0; i < finish; i++) {
            if (take(a.get(values.get(i))) != take(b.get(values.get(i)))) {
                places.set(values.get(i), 0L);
                data.set(values.get(i), 1);
                queue.set(low, values.get(i));
                low++;
            }
            if (colors.get(c.get(values.get(i))) == 0) data.set(values.get(i), 33);
        }
        return low;
    }

    void setPriorities() {
        for (int i = 0; i < graph.get(result).size(); i++) {
            if (places.get(result) + 1 < places.get(graph.get(result).get(i))) {
                priorities.set(graph.get(result).get(i), result);
                places.set(graph.get(result).get(i), places.get(result) + 1);
            }

            if (states.get(graph.get(result).get(i)) == 0) {
                if (low == 10010)
                    low = low;
                queue.set(low, graph.get(result).get(i));
                low++;
            }
        }
    }

    public static int safeLongToInt(long l) {
        return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
    }

    int findMinimum() {
        int min = -1;
        for (int i = 0; i < m; i++) {
            if (data.get(i) == 33) {
                if (min == -1 && places.get(i) != INF) {
                    insideData = i;
                    min = safeLongToInt(places.get(i));
                } else if ((places.get(i) < min)) {
                    insideData = i;
                    min = safeLongToInt(places.get(i));
                }
            }
        }
        return min;

    }

    void rainbow() {
        flag = false;

        while (true) {
            if (flag) break;
            high = 0;
            low = 0;
            points = new ArrayList<>();
            for (int i = 0; i < MAX_M; i++) points.add(0);

            Collections.fill(points.subList(0, answer.size()), 1);
            values = new ArrayList<>();
            graph = new ArrayList<>();
            places = new ArrayList<>();
            priorities = new ArrayList<>();
            states = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                graph.add(new ArrayList<>());
                places.add(i, INF);
                priorities.add(-1);
                states.add(0);
            }

            for (int i = 0; i < MAX_M * 100; i++)
                values.add(0);

            finish = countValues();
            buildGraphOfChanges();

            Collections.fill(colors, 0);

            setColorTree();

            for (int i = 0; i < MAX_N; i++) {
                set.set(i, i);
                weights.set(i, 0);
            }

            data = new ArrayList<>();
            queue = new ArrayList<>();

            for (int i = 0; i < MAX_M * 100; i++)
                queue.add(0);

            for (int i = 0; i < MAX_M; i++) {
                data.add(0);
            }

            for (Integer an : answer) {
                int f = a.get(an);
                int g = b.get(an);
                f = take(f);
                g = take(g);
                if (f != g) {
                    if (weights.get(f) < weights.get(g)) Collections.swap(weights, f, g);
                    set.set(g, f);
                    if (weights.get(f).equals(weights.get(g)))
                        weights.set(f, weights.get(f) + 1);
                }
            }

            low = countLow();

            while (true) {
                if (!(high < low)) break;
                result = queue.get(high);
                high++;
                states.set(result, 1);
                setPriorities();
            }

            used = new ArrayList<>();
            Collections.fill(points.subList(0, m), 0);
            insideData = -1;

            minimum = findMinimum();

            while (true) {
                if (insideData == -1) break;
                used.add(insideData);
                insideData = priorities.get(insideData);
            }

            if (used.size() == 0) {
                flag = true;
            } else {
                temp = new ArrayList<>();
                for (Integer aTmp : used) points.set(aTmp, 1);
                for (Integer an : answer)
                    if (points.get(an) == 0) temp.add(an);
                    else points.set(an, 0);

                temp.addAll(used.stream().filter(anUsed -> points.get(anUsed) != 0).collect(Collectors.toList()));
                answer.clear();

                answer.addAll(temp.stream().collect(Collectors.toList()));


                temp.clear();
            }
        }

    }

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        init();

        n = in.nextInt();
        m = in.nextInt();
        LinkedList<Pair<Integer, Integer>> list = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            a.add(in.nextInt() - 1);
            b.add(in.nextInt() - 1);
            c.add(in.nextInt());
            list.add(new Pair<>(i, i));
            independent.add(i);
        }

        ScheduleMatroid matroid = new ScheduleMatroid(list);

        reverseColors();
        rainbow();

        out.println(answer.size());
        for (Integer an : answer) out.print(String.format("%d ", an + 1));

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