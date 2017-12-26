import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class F {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "continuous";

    public static void main(String[] args) {
        new F().run();
    }

    private class Equation {
        ArrayList<Double> left;
        Double right;

        public Equation(ArrayList<Double> left, Double right) {
            this.left = left;
            this.right = right;
        }
    }

    private class EquationsSystem {
        ArrayList<Equation> equations;

        public EquationsSystem() {
            equations = new ArrayList<>();
        }

        public EquationsSystem(ArrayList<Equation> equations) {
            this.equations = equations;
        }

        public void addEquation(Equation e) {
            equations.add(e);
        }

        public ArrayList<Double> solve(double epsilon) {
            IntStream.range(0, equations.get(0).left.size()).forEach(i -> {
                final int[] t = {i};
                final int f = i;
                IntStream.range(i, equations.size()).filter(c -> Math.abs(equations.get(f).left.get(c)) >= epsilon).forEach(c -> t[0] = c);
                Collections.swap(equations, i, t[0]);
                if (Math.abs(equations.get(i).left.get(i)) >= epsilon) {
                    IntStream.range(0, equations.get(0).left.size()).filter(k -> f != k).forEach(k -> IntStream.range(0, equations.size()).filter(j -> f != j).forEach(j -> equations.get(j).left.set(k, equations.get(j).left.get(k) - equations.get(j).left.get(f) / equations.get(f).left.get(f) * equations.get(f).left.get(k))));
                    IntStream.range(0, equations.size()).filter(k -> f != k).forEach(k -> equations.get(k).right -= equations.get(k).left.get(f) / equations.get(f).left.get(f) * equations.get(f).right);
                    IntStream.range(0, equations.size()).filter(k -> f != k).forEach(k -> equations.get(k).left.set(f, 0d));
                }
            });
            IntStream.range(0, equations.get(0).left.size()).forEach(i -> {
                if (Math.abs(equations.get(i).left.get(i)) < epsilon) equations.get(i).right = 0d;
                else equations.get(i).right /= equations.get(i).left.get(i);
            });
            return new ArrayList<>(equations.stream().mapToDouble(e -> e.right).boxed().collect(Collectors.toList()));
        }


    }

    private class Milli {
        private ArrayList<ArrayList<Integer>> skeleton;
        private ArrayList<ArrayList<Double>> f;
        private ArrayList<Double> results;
        private final int POWER = 26;
        private int events;
        private int n;

        public Milli(int n, int events) {
            this.n = n;
            this.events = events;
            skeleton = new ArrayList<>();
            results = new ArrayList<>();
            f = new ArrayList<>();
            for (int i = 0; i < 2 * n; i++) {
                f.add(new ArrayList<>());
                if (i < n) {
                    skeleton.add(new ArrayList<>());
                    for (int k = 0; k < events; k++) skeleton.get(i).add(0);
                }
                results.add(0d);
                for (int k = 0; k < 2 * n; k++) f.get(i).add(0d);
            }
        }

        public void addEdge(int from, int to, int event) {
            skeleton.get(from).set(event, to);
        }

        public void addTest(ArrayList<Double> test, String In) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int i = 0; i < 2 * n; i++) temp.add(0d);
            double k = 1. / (double) test.size();
            int t = 0;
            for (int i = 0; i < test.size(); i++) {
                int ch = In.charAt(i) - '0';
                temp.set(t * 2 + ch, temp.get(t * 2 + ch) + k);
                t = skeleton.get(t).get(ch);
                for (int j = 0; j < 2 * n; j++) {
                    for (int c = 0; c < 2 * n; c++) {
                        f.get(j).set(c, f.get(j).get(c) + temp.get(j) * temp.get(c) * test.size());
                        f.get(c).set(j, f.get(c).get(j) + temp.get(j) * temp.get(c) * test.size());
                    }
                    results.set(j, results.get(j) + 2 * temp.get(j) * test.get(i));
                }
            }
        }

        public ArrayList<ArrayList<Double>> maximize() {
            EquationsSystem es = new EquationsSystem();
            IntStream.range(0, results.size()).forEach(i -> es.addEquation(new Equation(f.get(i), results.get(i))));
            ArrayList<Double> solved = es.solve(1e-9);
            return new ArrayList<>(IntStream.range(0, n).mapToObj(i -> new ArrayList<>(IntStream.range(0, events).mapToDouble(k -> solved.get(2 * i + k)).boxed().collect(Collectors.toList()))).collect(Collectors.toList()));
        }
    }

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        Milli milli = new Milli(n, 2);
        for (int i = 0; i < n; i++) {
            milli.addEdge(i, in.nextInt() - 1, 0);
            milli.addEdge(i, in.nextInt() - 1, 1);
        }
        for (int i = 0; i < m; i++) {
            int l = in.nextInt();
            String s = in.next();
            ArrayList<Double> test = new ArrayList<>();
            IntStream.range(0, l).forEach(c -> test.add(in.nextDouble()));
            milli.addTest(test, s);
        }
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(7);
        milli.maximize().forEach(e -> out.println(decimalFormat.format(e.get(0)) + " " + decimalFormat.format(e.get(1))));
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