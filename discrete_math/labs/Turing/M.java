import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class M {
    private FastScanner in;
    private static PrintWriter out;
    private final static String FILE_NAME = "sorting";

    public static void main(String[] args) {
        new M().run();
    }

    private static class Turing {
        private class Rool {
            String from, to;
            Arrow[] arrow;
            String[] symbol, write;

            public Rool(String from, String[] symbol, String to, String[] write, Arrow[] arrow) {
                this.from = from;
                this.symbol = symbol;
                this.to = to;
                this.write = write;
                this.arrow = arrow;
            }

            @Override
            public String toString() {
                String s = from + " ";
                for (int i = 0; i < lines; i++) {
                    s += symbol[i] + " ";
                }
                s += "-> " + to;
                for (int i = 0; i < lines; i++) {
                    s += " " + write[i];
                    switch (arrow[i]) {
                        case Right: {
                            s += " >";
                            break;
                        }
                        case Left: {
                            s += " <";
                            break;
                        }
                        case Non: {
                            s += " ^";
                        }
                    }
                }
                return s;
            }
        }

        String start, accept, reject, blank;
        int lines;
        ArrayList<Rool> rools;

        public Turing(String start, String accept, String reject, String blank, int lines) {
            this.start = start;
            this.accept = accept;
            this.reject = reject;
            this.blank = blank;
            this.lines = lines;
            rools = new ArrayList<Rool>();
        }

        private enum Arrow {
            Right(">"), Left("<"), Non("^");
            private final String name;

            private Arrow(String s) {
                name = s;
            }

            public boolean equalsName(String otherName) {
                return otherName != null && name.equals(otherName);
            }

            public String toString() {
                return this.name;
            }
        }

        public void addRool(String rool) {

            String[] split = rool.split(" ");
            String from = split[0];
            String[] symbol = new String[lines];
            System.arraycopy(split, 1, symbol, 0, lines);
            String to = split[lines + 2];
            String[] write = new String[lines];
            Arrow[] arrow = new Arrow[lines];
            int k = lines + 3;

            for (int i = 0; i < lines; i++, k += 2) {
                write[i] = split[k];
                if (split[k + 1].equals("<")) {
                    arrow[i] = Arrow.Left;
                }
                if (split[k + 1].equals(">")) {
                    arrow[i] = Arrow.Right;
                }
                if (split[k + 1].equals("^")) {
                    arrow[i] = Arrow.Non;
                }
            }

            rools.add(new Rool(from, symbol, to, write, arrow));
        }

        public void addRool(String from, String[] symbol, String to, String[] write, Arrow[] arrow) {

            rools.add(new Rool(from, symbol, to, write, arrow));
        }

        Rool getRool(String current, String ch) {
            int k = 0;
            Rool r = null;
            for (Rool rool : rools) {
                if (rool.from.equals(current) && rool.symbol.equals(ch)) {
                    k++;
                    r = rool;

                }
            }
            if (k > 1) System.err.print(k);

            return r;

        }

        String run(String w, int s) {
            char[] tmp = w.toCharArray();
            String[][] line = new String[lines][tmp.length];
            for (int i = 0; i < tmp.length; i++)
                line[0][i] = "" + tmp[i];
            String current = start;
            while (!current.equals(accept) && !current.equals(reject)) {
                String ch = "" + line[s];
                Rool rool = getRool(current, ch);

                line[s] = rool.write;

                current = rool.to;


            }
            return Arrays.toString(line) + s;
        }

        @Override
        public String toString() {
            String s = "" + lines + "\n";
            for (Rool r : rools) {
                s += r + "\n";
            }
            return s;
        }
    }

    void solve() throws IOException {
        Turing t = new Turing("S", "AC", "RJ", "_", 3);
        t.addRool("f0", new String[]{"0", t.blank, t.blank}, "f0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"|", "1", "1"}, "put", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q22", new String[]{"0", "0", "0"}, "q22", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", t.blank, "0"}, "l1", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", "|", "|"}, "f0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"0", t.blank, t.blank}, "b0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "0", "0"}, t.start, new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, "1", t.blank}, "r0", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"1", "0", t.blank}, t.start, new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "0", "0"}, "p0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, "0", t.blank}, "r0", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "1", "|"}, "f0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("x1", new String[]{"|", "1", t.blank}, "l1", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"|", "|", "1"}, "take", new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q52", new String[]{"0", "0", "0"}, "q52", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"|", "0", t.blank}, "c0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{t.blank, "0", "|"}, "a", new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q105", new String[]{"0", "0", "0"}, "q105", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", "1", "0"}, t.start, new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", "0", "1"}, "r0", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("put", new String[]{"1", t.blank, "0"}, "take", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{t.blank, "|", "0"}, t.start, new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", t.blank, t.blank}, "l1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q44", new String[]{"0", "0", "0"}, "q44", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g27", new String[]{"0", "0", "|"}, "g27", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("q49", new String[]{"0", "0", "0"}, "q49", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "|", t.blank}, "b0", new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "1", t.blank}, "x0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q9", new String[]{"0", "0", "0"}, "q9", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "|", "|"}, "a", new String[]{t.blank, "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", t.blank, "|"}, "f0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("r0", new String[]{"|", t.blank, "1"}, "r0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{t.blank, "1", "|"}, "a", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q80", new String[]{"0", "0", "0"}, "q80", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "1", "1"}, "p0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", t.blank, t.blank}, "p0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q2", new String[]{"0", "0", "0"}, "q2", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v1", new String[]{t.blank, "0", "1"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q56", new String[]{"0", "0", "0"}, "q56", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q68", new String[]{"0", "0", "0"}, "q68", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "0", "|"}, "f0", new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "1", t.blank}, "put", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"1", "0", "1"}, t.start, new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "0", "0"}, "f0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("a", new String[]{t.blank, t.blank, "|"}, "b0", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "1", "0"}, t.accept, new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q90", new String[]{"0", "0", "0"}, "q90", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"|", "1", t.blank}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q37", new String[]{"0", "0", "0"}, "q37", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "0", "|"}, "put", new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("g6", new String[]{"0", "0", "|"}, "g6", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("b0", new String[]{t.blank, t.blank, "|"}, t.accept, new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"1", "0", "0"}, "v0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x1", new String[]{t.blank, "1", "1"}, "x1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("put", new String[]{t.blank, t.blank, "0"}, "take", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"|", t.blank, "0"}, "b0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "1", "0"}, "take", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"|", "0", "1"}, "take", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q42", new String[]{"0", "0", "0"}, "q42", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "1", t.blank}, t.start, new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", "0", t.blank}, "put", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q62", new String[]{"0", "0", "0"}, "q62", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", t.blank, "|"}, "b0", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", t.blank, "0"}, "put", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"1", t.blank, t.blank}, "p0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", t.blank, "1"}, "b0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", t.blank, t.blank}, "c0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{t.blank, "0", "0"}, t.accept, new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"1", t.blank, "0"}, "r0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "|", t.blank}, "f0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("take", new String[]{"1", "0", "0"}, "take", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"1", "1", t.blank}, "put", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("z4", new String[]{"1", "0", "1"}, "z4", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "0", t.blank}, t.accept, new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "1", "|"}, "l1", new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "0", "1"}, "v0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", "0", t.blank}, "l1", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q41", new String[]{"0", "0", "0"}, "q41", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "1", t.blank}, "a", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q118", new String[]{"0", "0", "0"}, "q118", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "1", t.blank}, "b0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", "0", "1"}, "put", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"1", t.blank, t.blank}, "take", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"0", "|", t.blank}, "l1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, t.blank, t.blank}, "l1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "0", t.blank}, "b0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "1", "0"}, "put", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q14", new String[]{"0", "0", "0"}, "q14", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", t.blank, t.blank}, "x1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"1", "1", t.blank}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"0", "0", "|"}, "put", new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("a", new String[]{"1", t.blank, "|"}, "b0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "1", t.blank}, "r0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"0", t.blank, "1"}, "b0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "1", "1"}, "take", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q127", new String[]{"0", "0", "0"}, "q127", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", "0", "0"}, "x1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"0", "0", "1"}, t.start, new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"1", "1", "0"}, "r0", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"0", "0", t.blank}, "b0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g12", new String[]{"0", "0", "|"}, "g12", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"1", "|", "0"}, "x1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"0", t.blank, "0"}, "b0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "1", "1"}, "put", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"1", t.blank, t.blank}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{t.blank, "|", "1"}, "take", new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"0", "0", "0"}, "l1", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "0", "1"}, "l1", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", t.blank, "0"}, "f0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"1", "0", t.blank}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"0", "1", "1"}, "r0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{t.blank, "0", "0"}, "a", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"|", t.blank, "1"}, "v0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x1", new String[]{"0", "0", t.blank}, "l1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "|", "1"}, "x1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "0", "0"}, "b0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, t.blank, "1"}, t.accept, new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", "1", "0"}, "f0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"|", "|", "0"}, "l1", new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "1", "|"}, "put", new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q116", new String[]{"0", "0", "0"}, "q116", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, t.blank, "1"}, "x1", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", t.blank, "0"}, "c0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q93", new String[]{"0", "0", "0"}, "q93", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "1", t.blank}, "l1", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, t.blank, "1"}, "v0", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"|", t.blank, "0"}, "take", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"|", t.blank, "1"}, "b0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "0", t.blank}, "a", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "|", "|"}, "p0", new String[]{"0", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "0", t.blank}, "c0", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "1", t.blank}, "c0", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "1", "|"}, "f0", new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", "1", "0"}, "v0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"0", "0", "|"}, "f0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"0", "0", "0"}, "f0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"|", "0", "0"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"1", "0", t.blank}, "r0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("c0", new String[]{t.blank, t.blank, "0"}, "c0", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"0", "0", "0"}, t.start, new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"0", t.blank, t.blank}, "x0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{t.blank, "1", "|"}, "a", new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "0", "0"}, "c0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"|", "0", t.blank}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q126", new String[]{"0", "0", "0"}, "q126", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", t.blank, "0"}, "take", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"1", "|", "|"}, "b0", new String[]{"1", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"0", "0", "0"}, "r0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{t.blank, "1", "|"}, "put", new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"0", "1", t.blank}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"1", "1", "0"}, "x0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"|", "0", "0"}, "c0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q64", new String[]{"0", "0", "0"}, "q64", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q29", new String[]{"0", "0", "0"}, "q29", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "0", "|"}, "l1", new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "0", "1"}, "b0", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "0", "0"}, "c0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"1", t.blank, "1"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"0", "0", "1"}, "v0", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"|", "|", "|"}, t.start, new String[]{"|", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", "0", "1"}, "x0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q12", new String[]{"0", "0", "0"}, "q12", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", t.blank, "0"}, "l1", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", "1", "1"}, "x0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "|", "0"}, t.start, new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q36", new String[]{"0", "0", "0"}, "q36", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "1", t.blank}, t.start, new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{t.blank, "1", t.blank}, "v0", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{t.blank, "|", t.blank}, "put", new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"0", "1", t.blank}, "l1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q97", new String[]{"0", "0", "0"}, "q97", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "0", "|"}, "a", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q125", new String[]{"0", "0", "0"}, "q125", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "0", "1"}, "p0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", t.blank, t.blank}, "x1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q87", new String[]{"0", "0", "0"}, "q87", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"|", t.blank, "0"}, "v0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"1", "|", "0"}, t.start, new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, t.blank, "1"}, "take", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("a", new String[]{t.blank, t.blank, "1"}, "b0", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "0", "1"}, "x0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", "|", t.blank}, "b0", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", "0", "0"}, "r0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x1", new String[]{t.blank, "|", "0"}, "x1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"0", "|", t.blank}, "p0", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "|", "0"}, t.start, new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "0", t.blank}, t.start, new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", t.blank, t.blank}, "l1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "0", "1"}, "p0", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", "0", "|"}, "a", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q94", new String[]{"0", "0", "0"}, "q94", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "0", "0"}, "take", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"|", "1", "1"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", "|", "1"}, "f0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"1", t.blank, t.blank}, "p0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", "0", "|"}, "b0", new String[]{"1", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", t.blank, "0"}, t.start, new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", t.blank, t.blank}, "b0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, t.blank, "1"}, t.start, new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "0", "0"}, "a", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", t.blank, "1"}, "l1", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "|", "1"}, "put", new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"|", "0", "0"}, t.start, new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "0", "|"}, "p0", new String[]{"1", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{t.blank, "1", "1"}, "v0", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{t.blank, "0", "1"}, "a", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", t.blank, "1"}, "x1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"|", t.blank, "|"}, "b0", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q109", new String[]{"0", "0", "0"}, "q109", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q84", new String[]{"0", "0", "0"}, "q84", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", "1", "0"}, "a", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("g15", new String[]{"0", "0", "|"}, "g15", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"1", "|", t.blank}, "p0", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q69", new String[]{"0", "0", "0"}, "q69", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q86", new String[]{"0", "0", "0"}, "q86", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q81", new String[]{"0", "0", "0"}, "q81", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "1", "|"}, t.start, new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q32", new String[]{"0", "0", "0"}, "q32", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q1", new String[]{"0", "0", "0"}, "q1", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{t.blank, "1", t.blank}, "a", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "0", t.blank}, "put", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{t.blank, "0", "|"}, t.accept, new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", "1", "1"}, "x1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"|", "1", "1"}, "x1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q59", new String[]{"0", "0", "0"}, "q59", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "|", "1"}, "f0", new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", "1", "1"}, "take", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"0", "1", "1"}, "put", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"|", t.blank, "|"}, "x1", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, "0", "1"}, "r0", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"0", "|", "1"}, "p0", new String[]{"0", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "0", "0"}, "take", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("g5", new String[]{"0", "0", "|"}, "g5", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("p0", new String[]{t.blank, "1", t.blank}, t.start, new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q121", new String[]{"0", "0", "0"}, "q121", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", t.blank, t.blank}, "l1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "|", "|"}, "b0", new String[]{"0", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", "|", "0"}, "x1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("p0", new String[]{t.blank, t.blank, "0"}, t.start, new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{t.blank, t.blank, "0"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", t.blank, "0"}, "b0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q114", new String[]{"0", "0", "0"}, "q114", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g2", new String[]{"0", "0", "|"}, "g2", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", t.blank, "|"}, "b0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "0", "1"}, "v0", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", t.blank, "|"}, "put", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"0", "1", "|"}, "b0", new String[]{"0", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, t.blank, "|"}, t.start, new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q3", new String[]{"0", "0", "0"}, "q3", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"0", "0", "0"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q35", new String[]{"0", "0", "0"}, "q35", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{t.blank, "0", "|"}, "a", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "0", "0"}, "put", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("g11", new String[]{"0", "0", "|"}, "g11", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("a", new String[]{"1", "1", "|"}, "a", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", t.blank, "1"}, "take", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("g29", new String[]{"0", "0", "|"}, "g29", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"|", "0", t.blank}, "x0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", "1", "0"}, "put", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("c0", new String[]{"|", "0", "1"}, "v0", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "0", "|"}, t.start, new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q19", new String[]{"0", "0", "0"}, "q19", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", t.blank, "1"}, "b0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", t.blank, "|"}, "p0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"1", "1", "1"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{t.blank, "1", "1"}, t.accept, new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", t.blank, "0"}, "l1", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "0", "1"}, "x1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("c0", new String[]{"|", t.blank, "0"}, "c0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{"|", "0", t.blank}, "a", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", t.blank, "1"}, "take", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"|", "|", "|"}, "f0", new String[]{"|", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", t.blank, "0"}, "b0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"1", t.blank, "1"}, "r0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "0", "|"}, "f0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "|", "|"}, "f0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"1", t.blank, t.blank}, "l1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"1", "1", "0"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "0", "1"}, "b0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q20", new String[]{"0", "0", "0"}, "q20", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", t.blank, "0"}, "x1", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "0", "0"}, "c0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{t.blank, t.blank, t.blank}, "b0", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", "1", "|"}, "b0", new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, t.blank, t.blank}, t.accept, new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{t.blank, t.blank, "1"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q16", new String[]{"0", "0", "0"}, "q16", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g10", new String[]{"0", "0", "|"}, "g10", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"|", "|", t.blank}, "l1", new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", t.blank, "|"}, "take", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"|", "|", t.blank}, "l1", new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q61", new String[]{"0", "0", "0"}, "q61", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q75", new String[]{"0", "0", "0"}, "q75", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"1", "1", "1"}, "r0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{t.blank, "0", "0"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"0", t.blank, "0"}, "p0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q28", new String[]{"0", "0", "0"}, "q28", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, t.blank, "0"}, "x1", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "0", "|"}, "p0", new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"1", "1", t.blank}, "r0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"1", t.blank, "0"}, "p0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q77", new String[]{"0", "0", "0"}, "q77", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "|", t.blank}, "a", new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "|", "0"}, "f0", new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q99", new String[]{"0", "0", "0"}, "q99", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "0", t.blank}, "x0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "0", "1"}, t.accept, new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "|", "|"}, t.accept, new String[]{t.blank, "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", t.blank, t.blank}, t.start, new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", "1", "1"}, "put", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q120", new String[]{"0", "0", "0"}, "q120", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "0", "1"}, "put", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x1", new String[]{t.blank, t.blank, "0"}, "x1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"|", "|", "1"}, "b0", new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q23", new String[]{"0", "0", "0"}, "q23", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "1", "0"}, "b0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "1", "1"}, t.start, new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g23", new String[]{"0", "0", "|"}, "g23", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"|", "0", "1"}, "x1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q122", new String[]{"0", "0", "0"}, "q122", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "1", t.blank}, "l1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "1", t.blank}, "l1", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"1", "0", "0"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"0", "1", "|"}, "put", new String[]{"0", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q47", new String[]{"0", "0", "0"}, "q47", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", "0", "0"}, "x1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"1", "0", "1"}, "v0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q106", new String[]{"0", "0", "0"}, "q106", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q96", new String[]{"0", "0", "0"}, "q96", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "1", "|"}, "l1", new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "1", "0"}, "p0", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "|", "0"}, "b0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "0", t.blank}, "f0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, "0", "0"}, "put", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"|", t.blank, "|"}, "f0", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", t.blank, "|"}, t.start, new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, t.blank, "0"}, "a", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"1", "1", "0"}, "v0", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"0", "|", "0"}, "p0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "0", "0"}, "a", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q50", new String[]{"0", "0", "0"}, "q50", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("z8", new String[]{"1", "0", "1"}, "z8", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", t.blank, t.blank}, "b0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", "|", t.blank}, "f0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("a", new String[]{"0", "0", "1"}, "a", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, "1", "1"}, "r0", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q124", new String[]{"0", "0", "0"}, "q124", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g13", new String[]{"0", "0", "|"}, "g13", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"1", "1", "0"}, "x1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"|", t.blank, t.blank}, "f0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", t.blank, "1"}, "x1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"1", "|", "1"}, "x1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"0", "1", "0"}, "l1", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", "0", "0"}, "v0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"|", "0", "|"}, "put", new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", t.blank, "0"}, "f0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("x0", new String[]{"|", "1", t.blank}, "x0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, t.blank, "1"}, "r0", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "|", "0"}, "f0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"1", "1", t.blank}, t.start, new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "0", t.blank}, "a", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("z6", new String[]{"1", "0", "1"}, "z6", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", "0", "0"}, "x0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "0", "1"}, "v0", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "|", "1"}, "a", new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", "1", "0"}, "b0", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", t.blank, "1"}, "v0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "0", "0"}, "x0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", "0", t.blank}, "f0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"|", "0", "1"}, "f0", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "0", "|"}, "l1", new String[]{"1", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "1", "1"}, "a", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "0", "1"}, t.start, new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", t.blank, "1"}, "put", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"|", "1", "0"}, "f0", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", "1", "0"}, "r0", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"|", "1", "0"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "0", t.blank}, "b0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q103", new String[]{"0", "0", "0"}, "q103", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", t.blank, "1"}, "x1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"0", "0", "1"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"|", t.blank, "0"}, t.start, new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "1", "|"}, "a", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", "0", t.blank}, "x0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, "0", "|"}, "put", new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"1", "|", "1"}, "l1", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", t.blank, t.blank}, t.start, new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "0", "0"}, "c0", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("take", new String[]{"|", t.blank, t.blank}, "c0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("g4", new String[]{"0", "0", "|"}, "g4", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("put", new String[]{"0", t.blank, "1"}, "take", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"0", t.blank, t.blank}, t.start, new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "1", "0"}, "l1", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", t.blank, t.blank}, "take", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x1", new String[]{"1", "1", "1"}, "x1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", "1", "0"}, "f0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"0", "1", "|"}, "l1", new String[]{"0", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, t.blank, "1"}, "put", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"1", t.blank, t.blank}, "f0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"|", "|", "0"}, "b0", new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "0", "|"}, t.start, new String[]{t.blank, "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"|", "0", t.blank}, "c0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"0", "0", t.blank}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"0", "1", "|"}, "x0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"|", t.blank, t.blank}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"1", "0", "1"}, "take", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"1", "0", "0"}, "f0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("r0", new String[]{"0", "0", t.blank}, "r0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v0", new String[]{"|", "0", t.blank}, "v0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"0", "0", t.blank}, "f0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("x0", new String[]{t.blank, "0", "0"}, "x0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("g0", new String[]{"0", "0", "|"}, "g0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"1", "|", "1"}, t.start, new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", "0", t.blank}, "l1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", "1", "0"}, "x1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q73", new String[]{"0", "0", "0"}, "q73", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, "1", "0"}, "x0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "1", "0"}, "r0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("take", new String[]{t.blank, t.blank, "0"}, "take", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{t.blank, "1", "|"}, "put", new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("c0", new String[]{t.blank, "0", "0"}, "c0", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"1", "0", "0"}, t.start, new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "1", "0"}, t.start, new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "1", "1"}, "c0", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"|", "0", "1"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"|", "1", t.blank}, "put", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"1", t.blank, t.blank}, "x0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q102", new String[]{"0", "0", "0"}, "q102", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{t.blank, "0", t.blank}, "v0", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"|", "0", "0"}, "l1", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q48", new String[]{"0", "0", "0"}, "q48", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, "1", t.blank}, "put", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"|", "|", "|"}, "b0", new String[]{"|", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "0", t.blank}, "c0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("put", new String[]{t.blank, t.blank, t.blank}, "take", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("z1", new String[]{"1", "0", "1"}, "z1", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", t.blank, "1"}, "f0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("q58", new String[]{"0", "0", "0"}, "q58", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", t.blank, "0"}, "v0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"|", "|", "1"}, t.start, new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"0", t.blank, t.blank}, "p0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, "1", t.blank}, "x0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "0", "0"}, "b0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q24", new String[]{"0", "0", "0"}, "q24", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q8", new String[]{"0", "0", "0"}, "q8", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "1", "|"}, "put", new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"|", "1", "1"}, "a", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "1", t.blank}, "put", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"1", "0", t.blank}, "a", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"1", "1", "1"}, "v0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q6", new String[]{"0", "0", "0"}, "q6", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g21", new String[]{"0", "0", "|"}, "g21", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"0", "|", t.blank}, t.start, new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "0", t.blank}, "l1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", "1", t.blank}, "f0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", t.blank, "|"}, "l1", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", "1", "0"}, "x1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"1", t.blank, "|"}, "x1", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "0", "0"}, "x1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"1", "0", "0"}, "l1", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"1", "0", "1"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"0", t.blank, "1"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"1", t.blank, "1"}, "p0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "|", "0"}, "l1", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", t.blank, "|"}, "take", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("c0", new String[]{"1", "0", "0"}, "c0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("z3", new String[]{"1", "0", "1"}, "z3", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q0", new String[]{"0", "0", "0"}, "q0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q63", new String[]{"0", "0", "0"}, "q63", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "0", t.blank}, "p0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q91", new String[]{"0", "0", "0"}, "q91", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g16", new String[]{"0", "0", "|"}, "g16", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("v0", new String[]{t.blank, "1", "0"}, "v0", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v0", new String[]{t.blank, "0", "1"}, "v0", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{t.blank, "0", t.blank}, "a", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", "0", "1"}, "a", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", t.blank, "0"}, "take", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"1", "0", "|"}, "x0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "0", "1"}, "put", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"0", "1", "|"}, t.start, new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", t.blank, t.blank}, "l1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{t.blank, "1", "1"}, "a", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "1", "0"}, "r0", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v0", new String[]{"|", "0", "1"}, "v0", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"0", "1", "0"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("a", new String[]{t.blank, "1", "0"}, "a", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "|", "1"}, "p0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "0", t.blank}, t.start, new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "1", "1"}, "p0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "0", "1"}, "a", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", "1", t.blank}, "v0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q111", new String[]{"0", "0", "0"}, "q111", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "0", "1"}, "l1", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "1", t.blank}, t.accept, new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g22", new String[]{"0", "0", "|"}, "g22", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("take", new String[]{"1", "|", "0"}, "take", new String[]{"1", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"0", "0", "|"}, "x0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", "1", t.blank}, "l1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", "0", "|"}, t.start, new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "1", "1"}, "take", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"0", "|", t.blank}, "l1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "0", "1"}, t.start, new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, t.blank, "0"}, t.accept, new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", "1", "1"}, "r0", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"1", "|", "|"}, "l1", new String[]{"1", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "1", "|"}, t.start, new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, "1", "1"}, "put", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"0", t.blank, "|"}, "b0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "0", "0"}, "put", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x1", new String[]{t.blank, "|", t.blank}, "l1", new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "0", t.blank}, "put", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x0", new String[]{t.blank, "0", t.blank}, "x0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q65", new String[]{"0", "0", "0"}, "q65", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q57", new String[]{"0", "0", "0"}, "q57", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q83", new String[]{"0", "0", "0"}, "q83", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{t.blank, "0", "0"}, "r0", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q123", new String[]{"0", "0", "0"}, "q123", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "1", "0"}, "put", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"1", "|", "|"}, "p0", new String[]{"1", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "0", "1"}, "take", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"0", "1", "0"}, "r0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"|", "|", t.blank}, t.start, new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "|", "|"}, "l1", new String[]{"|", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", t.blank, "0"}, "x1", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("z2", new String[]{"1", "0", "1"}, "z2", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q113", new String[]{"0", "0", "0"}, "q113", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, t.blank, t.blank}, "put", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q70", new String[]{"0", "0", "0"}, "q70", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q51", new String[]{"0", "0", "0"}, "q51", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, t.blank, "|"}, "take", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v1", new String[]{"0", "1", "1"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q38", new String[]{"0", "0", "0"}, "q38", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", t.blank, "|"}, "f0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("v1", new String[]{t.blank, "0", t.blank}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("a", new String[]{t.blank, t.blank, "0"}, "b0", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "0", "1"}, "put", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"|", "1", "|"}, "b0", new String[]{"|", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", "1", "0"}, "put", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{t.blank, "1", "0"}, "r0", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{t.blank, t.blank, "|"}, t.start, new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "0", t.blank}, "put", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("x1", new String[]{t.blank, t.blank, "1"}, "x1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("g9", new String[]{"0", "0", "|"}, "g9", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"0", "|", "1"}, "l1", new String[]{"0", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", "1", t.blank}, "x0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"1", t.blank, "0"}, "v0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("z7", new String[]{"1", "0", "1"}, "z7", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", t.blank, "1"}, "x1", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"|", t.blank, "1"}, "v0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", "1", "0"}, "x0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", t.blank, "1"}, "take", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q7", new String[]{"0", "0", "0"}, "q7", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, "1", "|"}, "x0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", t.blank, "1"}, "p0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", "|", "0"}, "x1", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q71", new String[]{"0", "0", "0"}, "q71", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q60", new String[]{"0", "0", "0"}, "q60", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", "|", "1"}, "x1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"|", "1", t.blank}, "a", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("take", new String[]{t.blank, "1", "0"}, "take", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"|", t.blank, "1"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"|", t.blank, "0"}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("c0", new String[]{"|", "0", "0"}, "c0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q34", new String[]{"0", "0", "0"}, "q34", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q119", new String[]{"0", "0", "0"}, "q119", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"0", t.blank, "0"}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q5", new String[]{"0", "0", "0"}, "q5", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"1", t.blank, "1"}, "v0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q43", new String[]{"0", "0", "0"}, "q43", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "0", "|"}, "put", new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{t.blank, t.blank, "0"}, "put", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("a", new String[]{"|", "0", "1"}, "a", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", t.blank, "0"}, "x1", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "1", t.blank}, "b0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "1", t.blank}, "p0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g28", new String[]{"0", "0", "|"}, "g28", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("v1", new String[]{t.blank, "1", "0"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"0", t.blank, "1"}, "v0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("r0", new String[]{t.blank, t.blank, t.blank}, "x0", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("l1", new String[]{t.blank, "0", t.blank}, t.start, new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g20", new String[]{"0", "0", "|"}, "g20", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"1", "1", t.blank}, "v0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "1", "1"}, "f0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"1", "1", t.blank}, "p0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", t.blank, t.blank}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"1", "0", t.blank}, "put", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v0", new String[]{t.blank, t.blank, t.blank}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", "1", "1"}, "f0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("take", new String[]{"1", "1", "0"}, "take", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"1", t.blank, "0"}, "take", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"|", "1", "1"}, "f0", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", t.blank, "1"}, "f0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("r0", new String[]{"|", t.blank, t.blank}, "x0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"0", "|", "0"}, "take", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"1", "0", "0"}, "r0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("c0", new String[]{"|", t.blank, t.blank}, "v1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q18", new String[]{"0", "0", "0"}, "q18", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "1", t.blank}, t.start, new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q85", new String[]{"0", "0", "0"}, "q85", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, t.blank, "1"}, "a", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "0", "|"}, t.start, new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", t.blank, "|"}, "x1", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"0", "1", t.blank}, "r0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("r0", new String[]{"0", t.blank, "0"}, "r0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v0", new String[]{"|", "1", "1"}, "v0", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q40", new String[]{"0", "0", "0"}, "q40", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", "0", t.blank}, "c0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "|", "1"}, "b0", new String[]{"0", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", "0", "0"}, "put", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q31", new String[]{"0", "0", "0"}, "q31", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q21", new String[]{"0", "0", "0"}, "q21", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "|", "1"}, t.accept, new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q79", new String[]{"0", "0", "0"}, "q79", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", t.blank, "|"}, t.start, new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, t.blank, t.blank}, "a", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "1", "|"}, "p0", new String[]{"0", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "|", "|"}, "put", new String[]{t.blank, "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("c0", new String[]{"1", t.blank, t.blank}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"0", "|", t.blank}, "b0", new String[]{"0", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q67", new String[]{"0", "0", "0"}, "q67", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", "0", "1"}, "x0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", "1", t.blank}, "f0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("take", new String[]{t.blank, t.blank, t.blank}, "c0", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("put", new String[]{t.blank, "0", t.blank}, "put", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"0", "1", "1"}, "b0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", "|", "|"}, t.start, new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"0", "0", "1"}, "r0", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{"|", "0", "|"}, "a", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "1", "1"}, "put", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v1", new String[]{t.blank, "1", t.blank}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"0", "0", t.blank}, "c0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", "0", "0"}, "put", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"1", "0", "1"}, "l1", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "|", t.blank}, "l1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, t.blank, t.blank}, "x1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "1", "1"}, "b0", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q89", new String[]{"0", "0", "0"}, "q89", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", "0", "0"}, "x1", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"0", "1", "1"}, "v0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("c0", new String[]{"|", "1", "1"}, "c0", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"1", "|", "1"}, "f0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("z0", new String[]{"1", "0", "1"}, "z0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", "1", "1"}, t.start, new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"0", "0", "0"}, "b0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "1", "1"}, "c0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("r0", new String[]{t.blank, t.blank, "0"}, "r0", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x0", new String[]{"0", "1", "1"}, "x0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, t.blank, t.blank}, t.start, new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q33", new String[]{"0", "0", "0"}, "q33", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", "0", "1"}, "put", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("put", new String[]{"1", "0", "|"}, "put", new String[]{"1", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{t.blank, "|", "0"}, "take", new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x1", new String[]{"1", t.blank, "0"}, "x1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v0", new String[]{t.blank, t.blank, "0"}, "v0", new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"|", "|", t.blank}, "f0", new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q30", new String[]{"0", "0", "0"}, "q30", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "1", "0"}, "b0", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q11", new String[]{"0", "0", "0"}, "q11", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g26", new String[]{"0", "0", "|"}, "g26", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"|", "1", t.blank}, "r0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{t.blank, "|", "1"}, "put", new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q82", new String[]{"0", "0", "0"}, "q82", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q92", new String[]{"0", "0", "0"}, "q92", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", t.blank, "1"}, "l1", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", t.blank, "1"}, "l1", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, "0", "1"}, "put", new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q10", new String[]{"0", "0", "0"}, "q10", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, t.blank, "1"}, t.start, new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "1", "|"}, t.start, new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{t.blank, "0", "0"}, "v0", new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("x1", new String[]{"0", "0", "1"}, "x1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q115", new String[]{"0", "0", "0"}, "q115", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "1", "0"}, "r0", new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("take", new String[]{"0", t.blank, t.blank}, "c0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q4", new String[]{"0", "0", "0"}, "q4", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"|", "0", "1"}, "b0", new String[]{"|", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "1", "1"}, t.start, new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, t.blank, "|"}, "x1", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g25", new String[]{"0", "0", "|"}, "g25", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("p0", new String[]{t.blank, "0", "0"}, t.start, new String[]{t.blank, "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "|", "0"}, "a", new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", "1", "0"}, "a", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", "1", "|"}, "p0", new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q25", new String[]{"0", "0", "0"}, "q25", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "|", "0"}, t.accept, new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"1", "0", "1"}, "f0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"|", "0", "|"}, "l1", new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", t.blank, "0"}, "x1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("take", new String[]{"|", "|", "0"}, "take", new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("l1", new String[]{t.blank, "|", t.blank}, t.start, new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "1", "|"}, "x0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"|", t.blank, "1"}, "f0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"|", t.blank, "1"}, t.start, new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q54", new String[]{"0", "0", "0"}, "q54", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, t.blank, "|"}, "put", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"1", t.blank, "|"}, "l1", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", t.blank, t.blank}, "b0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q104", new String[]{"0", "0", "0"}, "q104", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q46", new String[]{"0", "0", "0"}, "q46", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", t.blank, "1"}, "v0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "|", "|"}, t.start, new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"0", "1", "1"}, t.start, new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", t.blank, "1"}, "take", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{t.blank, "1", "|"}, t.accept, new String[]{t.blank, "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "|", "|"}, t.start, new String[]{t.blank, "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"1", "1", "1"}, "x0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q15", new String[]{"0", "0", "0"}, "q15", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "0", t.blank}, "c0", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("put", new String[]{"0", "1", t.blank}, "put", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("c0", new String[]{"|", "1", "0"}, "r0", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"0", t.blank, "1"}, t.start, new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q112", new String[]{"0", "0", "0"}, "q112", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "|", "|"}, "put", new String[]{"|", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("l1", new String[]{t.blank, "0", "1"}, t.start, new String[]{t.blank, "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "1", "0"}, t.start, new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "0", "1"}, "take", new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "|", "1"}, "b0", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", "1", "|"}, "a", new String[]{"|", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("take", new String[]{"|", "1", "0"}, "take", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("q72", new String[]{"0", "0", "0"}, "q72", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", t.blank, "0"}, "c0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("l1", new String[]{t.blank, "|", "0"}, t.start, new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q76", new String[]{"0", "0", "0"}, "q76", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "1", "0"}, t.start, new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g19", new String[]{"0", "0", "|"}, "g19", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("c0", new String[]{t.blank, t.blank, t.blank}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("a", new String[]{"1", "1", t.blank}, "a", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"|", "1", t.blank}, "r0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"0", "0", "|"}, "b0", new String[]{"0", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"1", "1", t.blank}, "r0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("take", new String[]{"|", "0", "0"}, "take", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"|", "1", "|"}, "x0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"|", "|", t.blank}, "put", new String[]{"|", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", "|", "0"}, "f0", new String[]{"0", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("v1", new String[]{t.blank, "1", "1"}, "v1", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v1", new String[]{"1", t.blank, "0"}, "v1", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("take", new String[]{"|", "1", t.blank}, "c0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q107", new String[]{"0", "0", "0"}, "q107", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q95", new String[]{"0", "0", "0"}, "q95", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", "|", t.blank}, t.start, new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", "|", t.blank}, "l1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", "0", "0"}, "a", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q108", new String[]{"0", "0", "0"}, "q108", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("g17", new String[]{"0", "0", "|"}, "g17", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"|", "0", "|"}, t.start, new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q27", new String[]{"0", "0", "0"}, "q27", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "1", "0"}, "x1", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q117", new String[]{"0", "0", "0"}, "q117", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", "0", "0"}, "put", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"|", "0", "0"}, "v0", new String[]{"|", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("g18", new String[]{"0", "0", "|"}, "g18", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"|", t.blank, t.blank}, "put", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("p0", new String[]{t.blank, "|", t.blank}, t.start, new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"1", t.blank, "|"}, "p0", new String[]{"1", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q66", new String[]{"0", "0", "0"}, "q66", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "1", "1"}, "l1", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", t.blank, "|"}, "l1", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "|", "1"}, "take", new String[]{"0", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("f0", new String[]{"0", "0", "1"}, "f0", new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("r0", new String[]{"0", t.blank, "1"}, "r0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("q39", new String[]{"0", "0", "0"}, "q39", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", t.blank, t.blank}, "b0", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g7", new String[]{"0", "0", "|"}, "g7", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "1", t.blank}, "b0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"1", "0", "1"}, "x1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"|", "|", "1"}, "l1", new String[]{"|", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, "1", "1"}, "x0", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", "0", "|"}, "x0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", "1", "|"}, t.start, new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", t.blank, t.blank}, "take", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{t.blank, "0", t.blank}, t.start, new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"1", "1", "|"}, "put", new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"0", "|", "1"}, t.start, new String[]{"0", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{"|", t.blank, "0"}, "take", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("a", new String[]{"1", "1", "1"}, "a", new String[]{"1", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "1", t.blank}, "l1", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", "|", "1"}, "take", new String[]{"1", "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("z5", new String[]{"1", "0", "1"}, "z5", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "|", "|"}, t.start, new String[]{t.blank, "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", "0", "0"}, "x0", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q78", new String[]{"0", "0", "0"}, "q78", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v1", new String[]{"|", t.blank, t.blank}, "p0", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"0", t.blank, "0"}, "x1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v1", new String[]{t.blank, t.blank, t.blank}, "p0", new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g14", new String[]{"0", "0", "|"}, "g14", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("p0", new String[]{"1", "|", "0"}, "p0", new String[]{"1", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{t.blank, "1", "1"}, "a", new String[]{t.blank, "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q88", new String[]{"0", "0", "0"}, "q88", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", t.blank, "0"}, "b0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("c0", new String[]{t.blank, "1", t.blank}, "r0", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"0", "1", "0"}, "p0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"1", "0", "0"}, "a", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q53", new String[]{"0", "0", "0"}, "q53", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", "0", t.blank}, "r0", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("v0", new String[]{"|", "1", "0"}, "v0", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("g8", new String[]{"0", "0", "|"}, "g8", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("q100", new String[]{"0", "0", "0"}, "q100", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("take", new String[]{"0", "1", t.blank}, "c0", new String[]{"0", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("g1", new String[]{"0", "0", "|"}, "g1", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"0", "1", "0"}, t.start, new String[]{"0", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"0", "1", "1"}, "l1", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("f0", new String[]{t.blank, "1", "0"}, "put", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"|", t.blank, "0"}, "f0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("g3", new String[]{"0", "0", "|"}, "g3", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool("l1", new String[]{"0", t.blank, t.blank}, "l1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", t.blank, "|"}, t.start, new String[]{"1", "1", "|"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "1", "1"}, "c0", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{t.blank, "1", t.blank}, "a", new String[]{t.blank, "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x1", new String[]{"|", "|", "1"}, "x1", new String[]{"1", "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("v0", new String[]{"1", "0", t.blank}, "v0", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("b0", new String[]{"|", "0", "|"}, "b0", new String[]{"|", "0", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "0", t.blank}, "l1", new String[]{"|", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "1", "1"}, "l1", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "0", t.blank}, "l1", new String[]{"1", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"|", "1", "0"}, "l1", new String[]{"|", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x1", new String[]{t.blank, "0", t.blank}, "l1", new String[]{t.blank, "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("z9", new String[]{"1", "0", "1"}, "z9", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, t.blank, t.blank}, t.start, new String[]{t.blank, t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", t.blank, t.blank}, "b0", new String[]{"1", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", t.blank, "|"}, "b0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"|", t.blank, "1"}, "take", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("r0", new String[]{"1", "0", "1"}, "r0", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("take", new String[]{"|", "1", "1"}, "take", new String[]{"|", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("b0", new String[]{"1", "1", "1"}, "b0", new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"0", t.blank, "1"}, "x1", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("put", new String[]{"0", t.blank, "|"}, "take", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("g24", new String[]{"0", "0", "|"}, "g24", new String[]{"1", "0", "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Right, Turing.Arrow.Left});
        t.addRool(t.start, new String[]{"1", t.blank, "0"}, t.start, new String[]{"1", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, "|", "1"}, t.start, new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("b0", new String[]{"1", "|", "0"}, "b0", new String[]{"1", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", "1", "0"}, "a", new String[]{"0", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q26", new String[]{"0", "0", "0"}, "q26", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", t.blank, "0"}, "b0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{"1", "|", "0"}, "l1", new String[]{"1", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("take", new String[]{"1", "1", t.blank}, "c0", new String[]{"1", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q45", new String[]{"0", "0", "0"}, "q45", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("p0", new String[]{"0", "0", t.blank}, "p0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q13", new String[]{"0", "0", "0"}, "q13", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("q55", new String[]{"0", "0", "0"}, "q55", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("b0", new String[]{t.blank, "|", t.blank}, t.accept, new String[]{t.blank, "|", t.blank}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("l1", new String[]{t.blank, t.blank, "0"}, t.start, new String[]{t.blank, t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"0", t.blank, "1"}, "b0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("a", new String[]{"|", t.blank, "1"}, "b0", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("p0", new String[]{t.blank, "|", "1"}, t.start, new String[]{t.blank, "|", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{t.blank, t.blank, "1"}, "v0", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{"1", "1", "1"}, t.start, new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("x0", new String[]{t.blank, "0", "|"}, "x0", new String[]{"0", t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", t.blank, t.blank}, "v1", new String[]{"0", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"0", "1", "0"}, "x0", new String[]{"1", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("q110", new String[]{"0", "0", "0"}, "q110", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("put", new String[]{t.blank, t.blank, "1"}, "take", new String[]{t.blank, t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("put", new String[]{t.blank, "1", "0"}, "put", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Left, Turing.Arrow.Left});
        t.addRool("x0", new String[]{"1", t.blank, "1"}, "x1", new String[]{"|", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q98", new String[]{"0", "0", "0"}, "q98", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("r0", new String[]{"|", t.blank, "0"}, "r0", new String[]{"|", t.blank, "0"}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{t.blank, "1", "0"}, "a", new String[]{t.blank, "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", "1", "|"}, "f0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("l1", new String[]{"0", "|", "|"}, "l1", new String[]{"0", "|", "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"0", "0", t.blank}, "v0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("f0", new String[]{t.blank, "|", "0"}, "put", new String[]{t.blank, "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
        t.addRool("q101", new String[]{"0", "0", "0"}, "q101", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("v0", new String[]{"|", "1", t.blank}, "v0", new String[]{"|", "1", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool(t.start, new String[]{t.blank, t.blank, "|"}, "a", new String[]{t.blank, t.blank, "|"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});
        t.addRool("x0", new String[]{"|", t.blank, t.blank}, "x1", new String[]{"|", t.blank, t.blank}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q17", new String[]{"0", "0", "0"}, "q17", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool(t.start, new String[]{"1", t.blank, "1"}, t.start, new String[]{"1", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("c0", new String[]{"0", "0", t.blank}, "c0", new String[]{"0", "0", t.blank}, new Turing.Arrow[]{Turing.Arrow.Non, Turing.Arrow.Right, Turing.Arrow.Right});
        t.addRool("p0", new String[]{"1", "0", "0"}, "p0", new String[]{"1", "0", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Non});
        t.addRool("q74", new String[]{"0", "0", "0"}, "q74", new String[]{"0", "1", "1"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Right, Turing.Arrow.Non});
        t.addRool("f0", new String[]{"0", "1", t.blank}, "f0", new String[]{"0", "1", "0"}, new Turing.Arrow[]{Turing.Arrow.Right, Turing.Arrow.Non, Turing.Arrow.Right});
        t.addRool("f0", new String[]{"|", "|", "0"}, "put", new String[]{"|", "|", "0"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Non, Turing.Arrow.Left});
      //  t.addRool("x0", new String[]{t.blank, "0", "1"}, "x0", new String[]{"0", t.blank, "1"}, new Turing.Arrow[]{Turing.Arrow.Left, Turing.Arrow.Left, Turing.Arrow.Non});

         out.print(t);
    }

    public void run() {
        try {
          //  InputStream is = new FileInputStream(FILE_NAME + ".in");
          //  in = new FastScanner(is);
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