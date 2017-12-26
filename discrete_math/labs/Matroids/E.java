import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "cycles";

    public static void main(String[] args) {
        new E().run();
    }

    ArrayList<Pair<Integer, Integer>> weights;
    ArrayList<ArrayList<Integer>> cycles;
    ArrayList<Integer> masks;
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

    void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        weights = new ArrayList<>();
        cycles = new ArrayList<>();
        masks = new ArrayList<>();
        for (int i = 0; i < n; i++)
            weights.add(new Pair<Integer, Integer>(in.nextInt(), i));
        weights.sort(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return Integer.compare(o2.getKey(), o1.getKey());
            }
        });
        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            int mask = 0;
            for (int j = 0; j < k; j++) {
                mask |= (1 << (in.nextInt() - 1));
            }
            masks.add(mask);

        }
        int res = 0;
        int answer = 0;
        for (int i = 0; i < n; i++) {
            int flag = 0;
            res |= (1 << weights.get(i).getValue());
            for (Integer mask : masks) {
                int tmp = 0;
                int fl = 0;
                for (int t = 0; t < n; t++) {
                    if ((mask & (1 << t)) > 0) if ((res & (1 << t)) <= 0) fl = 1;
                    tmp = fl == 1 ? 0 : 1;
                }
                flag += tmp;
            }
            if (flag > 0) {
                res ^= (1 << weights.get(i).getValue());
            } else {
                answer += weights.get(i).getKey();
            }
        }
        out.print(answer);
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