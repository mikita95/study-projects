import java.io.*;
import java.util.StringTokenizer;

public class Main {
    private final static String fileName = "input";
    private static final int N = 8000;
    int n, currentChar;
    String s;
    public static void main(String[] args) {
        new Main().run();
    }

    boolean checkEqualHard(Node a, Node b) {
        return !((a.left != null && b.left == null) || (a.left == null && b.left != null)) && !((a.right != null && b.right == null) || (a.right == null && b.right != null)) && a.s.equals(b.s) && !(a.left != null && b.left != null && !checkEqualHard(a.left, b.left)) && !(a.right != null && b.right != null && !checkEqualHard(a.right, b.right));
    }

    boolean checkEqual(Node a, Node b) {
        return a.hash == b.hash && checkEqualHard(a, b);
    }

    Node parseExpression() throws Exception {
        Node expr1 = parseDisjuction();
        if (currentChar < n && s.charAt(currentChar) == '-' && s.charAt(++currentChar) == '>') {
            currentChar++;
            Node expr2 = parseExpression();
            return new Node("->", expr1, expr2);
        }
        return expr1;
    }

    Node parseNegation() throws Exception {
        char c = s.charAt(currentChar);
        if (c >= 'A' && c <= 'Z') {
            String name = "";
            name += c;
            currentChar++;
            if (currentChar < n && Character.isDigit(s.charAt(currentChar))) {
                name += s.charAt(currentChar++);
            }
            return new Node(name, null, null);
        } else if (c == '!') {
            currentChar++;
            Node expr = parseNegation();
            return new Node("!", null, expr);
        } else if (c == '(') {
            currentChar++;
            Node expr = parseExpression();
            if (currentChar >= n || s.charAt(currentChar++) != ')') {
                throw new Exception(") doesn't exist");
            }
            return expr;
        }
        throw new Exception("incorrect formula");
    }

    Node parseConjuction() throws Exception {
        Node expr = parseNegation();
        while (currentChar < n && s.charAt(currentChar) == '&') {
            currentChar++;
            Node expr2 = parseNegation();
            expr = new Node("&", expr, expr2);
        }
        return expr;
    }

    Node parseDisjuction() throws Exception {
        Node expr = parseConjuction();
        while (currentChar < n && s.charAt(currentChar) == '|') {
            currentChar++;
            Node expr2 = parseConjuction();
            expr = new Node("|", expr, expr2);
        }
        return expr;
    }

    boolean checkFirst(Node v) {
        return v != null && v.s.equals("->") && v.right != null && v.right.s.equals("->") && (v.left != null && v.right.right != null && checkEqual(v.left, v.right.right));
    }

    boolean checkSecond(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.right != null && v.right.right.s.equals("->")) {
                if (v.right.left.right != null && v.right.left.right.s.equals("->")) {
                    if (v.left.left != null && v.left.right != null && v.right.left.left != null && v.right.left.right.left != null && v.right.left.right.right != null &&
                            v.right.right.left != null && v.right.right.right != null) {
                        return ((checkEqual(v.left.left, v.right.left.left) && checkEqual(v.right.right.left, v.left.left)) &&
                                (checkEqual(v.left.right, v.right.left.right.left)) &&
                                (checkEqual(v.right.right.right, v.right.left.right.right)));
                    }
                }
            }
        }
        return false;
    }

    boolean checkThird(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("->") && v.right.right != null && v.right.right.s.equals("&")) {
            if (v.left != null && v.right.left != null && v.right.right.left != null && v.right.right.right != null) {
                return (checkEqual(v.left, v.right.right.left) && checkEqual(v.right.left, v.right.right.right));
            }
        }
        return false;
    }

    boolean checkFourth(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("&")) {
            if (v.left.left != null && v.left.right != null && v.right != null) {
                return checkEqual(v.left.left, v.right);
            }
        }
        return false;
    }

    boolean checkFifth(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("&")) {
            if (v.left.left != null && v.left.right != null && v.right != null) {
                return checkEqual(v.left.right, v.right);
            }
        }
        return false;
    }

    boolean checkSixth(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("|")) {
            if (v.left != null && v.right.left != null && v.right.right != null) {
                return checkEqual(v.left, v.right.left);
            }
        }
        return false;
    }

    boolean checkSeventh(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("|")) {
            if (v.left != null && v.right.left != null && v.right.right != null) {
                return checkEqual(v.left, v.right.right);
            }
        }
        return false;
    }

    boolean checkEighth(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.right != null && v.right.right.s.equals("->") &&
                    v.right.right.left != null && v.right.right.left.s.equals("|")) {
                if (v.left.left != null && v.left.right != null && v.right.left.left != null && v.right.left.right != null && v.right.right.right != null &&
                        v.right.right.left.left != null && v.right.right.left.right != null) {
                    return (checkEqual(v.left.left, v.right.right.left.left) &&
                            checkEqual(v.right.left.left, v.right.right.left.right) &&
                            checkEqual(v.left.right, v.right.left.right));
                }
            }
        }
        return false;
    }

    boolean checkNinth(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.left.right != null && v.right.left.right.s.equals("!") &&
                    v.right.right != null && v.right.right.s.equals("!")) {
                if (v.left.left != null && v.right.left.left != null && v.right.right.right != null && v.left.right != null && v.right.left.right.right != null) {
                    return (checkEqual(v.left.left, v.right.left.left) && checkEqual(v.left.left, v.right.right.right) &&
                            checkEqual(v.left.right, v.right.left.right.right));
                }
            }
        }
        return false;
    }

    boolean checkTenth(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("!") && v.left.right != null && v.left.right.s.equals("!")) {
            if (v.right != null && v.left.right.right != null) {
                return checkEqual(v.left.right.right, v.right);
            }
        }
        return false;
    }

    int checkItIsAxiom(Node v) {
        if (checkFirst(v)) return 1;
        else if (checkSecond(v)) return 2;
        else if (checkThird(v)) return 3;
        else if (checkFourth(v)) return 4;
        else if (checkFifth(v)) return 5;
        else if (checkSixth(v)) return 6;
        else if (checkSeventh(v)) return 7;
        else if (checkEighth(v)) return 8;
        else if (checkNinth(v)) return 9;
        else if (checkTenth(v)) return 10;
        return -1;
    }

    Pair checkModusPonens(int id) {
        for (int i = id - 1; i >= 0; i--) {
            if (!wasProofed[i]) continue;
            Node AB = formulas[i];
            if (AB != null && AB.s.equals("->") && AB.right != null && formulas[id] != null && checkEqual(AB.right, formulas[id])) {
                for (int j = 0; j < id; j++) {
                    if (!wasProofed[j]) continue;
                    Node A = formulas[j];
                    if (A != null && AB.left != null && checkEqual(A, AB.left)) {
                        return new Pair(j, i);
                    }
                }
            }
        }
        return new Pair(-1, -1);
    }

    Node formulas[] = new Node[N];
    boolean wasProofed[] = new boolean[N];
    private class Pair {
        int first, second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }


    void solve() throws IOException {
        InputStream is = new FileInputStream(fileName + ".txt");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("output" + ".txt"));

        int counter = 1;
        while (in.hasNext()) {
            s = in.next();
            s = s.replace(" ", "");
            n = s.length();
            currentChar = 0;
            if (n == 0) break;
            out.print("(" + counter + ") " + s);
            try {
                Node expr = parseExpression();
                formulas[counter - 1] = expr;
                int axiomNumber  = checkItIsAxiom(expr);
                if (axiomNumber != -1) {
                    out.println(" (Сх. акс. " + axiomNumber + ")");
                    wasProofed[counter - 1] = true;
                } else {
                    Pair mp = checkModusPonens(counter - 1);
                    if (mp.first != -1) {
                        out.println(" (M.P. " + (mp.first + 1) + ", " + (mp.second + 1) + ")");
                        wasProofed[counter - 1] = true;
                    } else {
                        out.println(" (Не доказано)");
                    }
                }

            } catch (Exception e) {
                out.println(e.getMessage() + " in " + s);
            }
            counter++;
        }

        out.close();
    }

    public void run() {
        try {
            solve();
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