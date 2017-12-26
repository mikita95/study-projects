import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class J {

    // private FastScanner in;
    // private PrintWriter out;
    // private final static String FILE_NAME = "";
    private final int MOD = 10;
    private final double EPSILON = 0.5;
    private int n;

    public static void main(String[] args) {
        new J().run();
    }


    public class MyRandom extends Random {
        public MyRandom() {
        }
        public int nextNonNegative() {
            return next(Integer.SIZE - 1);
        }
    }

    MyRandom myRandom;
    BiFunction<ArrayList<Double>, ArrayList<Double>, ArrayList<Double>> crossover = (a, b) ->
            new ArrayList<>(IntStream.range(0, n).mapToDouble(i -> {
                if (1. / (double) myRandom.nextNonNegative() <= 1. / (double)n) return b.get(i);
                else return a.get(i);
            }).boxed().collect(Collectors.toList()));

    void solve() throws IOException {
        myRandom = new MyRandom();
        myRandom.setSeed(System.currentTimeMillis());
        Scanner in = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(7);
        n = in.nextInt();
        String s;
        in.nextLine();
        ArrayList<ArrayList<Double>> population = new ArrayList<>();
        ArrayList<Double> function = new ArrayList<>();
        ArrayList<ArrayList<Double>> temp = new ArrayList<>();
        for (int i = 0; i < MOD; i++) {
            population.add(new ArrayList<>());
            temp.add(new ArrayList<>());
            for (int k = 0; k < n; k++) {
                population.get(i).add((double) i);
                System.out.print(decimalFormat.format(population.get(i).get(k)) + " ");
            }
            System.out.println();
            s = in.nextLine();
            if (s.charAt(0) == 'B') return;
            function.add(Double.valueOf(s));
        }
        for (; ; ) {
            for (int i = 0; i < MOD; i++) {
                int t;
                while ((t = myRandom.nextNonNegative() % MOD) == i) ;
                final int alpha = t;
                while ((t = myRandom.nextNonNegative() % MOD) == i || t == alpha) ;
                final int beta = t;
                while ((t = myRandom.nextNonNegative() % MOD) == i || t == beta || t == alpha) ;
                final int gamma = t;
                final ArrayList<Double> newPopulation = new ArrayList<>(crossover.apply(population.get(i),
                        new ArrayList<>(IntStream.range(0, n).mapToDouble(
                                k -> population.get(alpha).get(k) +
                                        (population.get(beta).get(k) - population.get(gamma).get(k)) * EPSILON)
                                .boxed().collect(Collectors.toList()))).stream().mapToDouble(
                        e -> Math.max(-MOD, Math.min(e, MOD))).boxed().collect(Collectors.toList()));
                newPopulation.forEach(d -> System.out.print(decimalFormat.format(d) + " "));
                System.out.println();
                s = in.nextLine();
                if (s.charAt(0) == 'B') return;
                double value = Double.valueOf(s);
                if (function.get(i) > value) {
                    function.set(i, value);
                    temp.set(i, new ArrayList<>(newPopulation));
                } else temp.set(i, new ArrayList<>(population.get(i)));
            }
            for (int i = 0; i < MOD; i++) {
                population.set(i, new ArrayList<>(temp.get(i)));
                temp.get(i).clear();
            }

        }
    }

    public void run() {
        try {
         /*   InputStream is = new FileInputStream(FILE_NAME + ".in");
            in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".out"));*/
            solve();
            //out.close();
        } catch (IOException ignored) {
        }
    }

}