import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "schedule";

    private abstract class Matroid<X, T extends Set<X>> {
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

    private class ScheduleMatroid extends Matroid<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> {

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

    public static void main(String[] args) {
        new A().run();
    }

    private class MyComparator implements Comparator<Pair<Integer, Integer>> {

        @Override
        public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
            return o1.getKey().equals(o2.getKey()) ? Integer.compare(o2.getValue(), o1.getValue()) :
                    Integer.compare(o1.getKey(), o2.getKey());
        }
    }

    void solve() throws IOException {
        int n = in.nextInt();
        LinkedList<Pair<Integer, Integer>> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Pair<>(in.nextInt(), in.nextInt()));
        }
        out.print(new ScheduleMatroid(list).greedy(new MyComparator()).stream().map((i) -> (long) i.getValue()).reduce(0L, Math::addExact));
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