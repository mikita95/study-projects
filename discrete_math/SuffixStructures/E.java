import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "refrain";

    final int alp = 28;
    class SuffixTree {
        String str;
        static final String ALPHA = "abcdefghijklmnopqrstuvwxyz$&";
        private Node root;
        public SuffixTree(final String s) {
            this.str = s;
            root = buildSuffixTree(s);
        }

        public Node getRoot() {
            return root;
        }

        public class Node {
            int begin;
            int end;
            int depth;
            Node parent;
            Node[] children;
            Node suffixLink;
            int count;

            Node(int begin, int end, int depth, Node parent) {
                children = new Node[alp];
                this.begin = begin;
                this.end = end;
                this.parent = parent;
                this.depth = depth;
            }

            boolean contains(int d) {
                return depth <= d && d < depth + (end - begin);
            }
        }

        private Node buildSuffixTree(String s) {
            int n = s.length();
            byte[] a = new byte[n];
            for (int i = 0; i < n; i++)
                a[i] = (byte) ALPHA.indexOf(s.charAt(i));
            Node root = new Node(0, 0, 0, null);
            Node nodesUnder = root;
            root.suffixLink = root;
            Node slNeed = null;
            int endMust = 0;
            int j = 0;
            int i = -1;
            while (i < n - 1) {
                int current = a[i + 1];
                while (j <= i + 1) {
                    int now = i + 1 - j;
                    if (endMust != 3) {
                        nodesUnder = nodesUnder.suffixLink != null ? nodesUnder.suffixLink : nodesUnder.parent.suffixLink;
                        int k = j + nodesUnder.depth;
                        while (true) {
                            if (!(now > 0 && !nodesUnder.contains(now - 1))) break;
                            k += nodesUnder.end - nodesUnder.begin;
                            nodesUnder = nodesUnder.children[a[k]];
                        }
                    }
                    if (!nodesUnder.contains(now)) {
                        if (slNeed != null) {
                            slNeed.suffixLink = nodesUnder;
                            slNeed = null;
                        }
                        if (nodesUnder.children[current] == null) {
                            nodesUnder.children[current] = new Node(i + 1, n, now, nodesUnder);
                            endMust = 2;
                        } else {
                            nodesUnder = nodesUnder.children[current];
                            endMust = 3;
                            break;
                        }
                    } else {
                        int end = nodesUnder.begin + now - nodesUnder.depth;
                        if (a[end] != current) {
                            Node newn = new Node(nodesUnder.begin, end, nodesUnder.depth, nodesUnder.parent);
                            newn.children[current] = new Node(i + 1, n, now, newn);
                            newn.children[a[end]] = nodesUnder;
                            nodesUnder.parent.children[a[nodesUnder.begin]] = newn;
                            if (slNeed != null) {
                                slNeed.suffixLink = newn;
                            }
                            nodesUnder.begin = end;
                            nodesUnder.depth = now;
                            nodesUnder.parent = newn;
                            nodesUnder = slNeed = newn;
                            endMust = 2;
                        } else if (nodesUnder.end != n || nodesUnder.begin - nodesUnder.depth < j) {
                            endMust = 3;
                            break;
                        } else {
                            endMust = 1;
                        }
                    }
                    j++;
                }
                i++;
            }
            root.suffixLink = null;
            return root;
        }
    }

    long refrainLength, refrainBegin, refrainCount;
    SuffixTree.Node edge;
    void findCount(SuffixTree.Node current) {
        if (current.end == stringLength) {
            current.count = 1;
            return;
        }
        int res = 0;
        for (char f = 0; f < alp; f++) {
            SuffixTree.Node c = current.children[f];
            if (c == null)
                continue;;
            findCount(c);
            res += c.count;
        }
        current.count = res;
    }

    void findCurrent(SuffixTree.Node current) {
        if (current == null)
            return;
        for (char ch = 0; ch < alp; ch++) {
            SuffixTree.Node c = current.children[ch];
            if (c == null)
                continue;
            long curLength = c.depth;
            curLength += c.end - c.begin;
            if (c.end == stringLength)
                curLength--;
            if (curLength * c.count > refrainLength * refrainCount) {
                refrainLength = curLength;
                refrainCount = c.count;
                edge = c;
            }
            findCurrent(c);
        }
    }

    int findRefrain(SuffixTree.Node root) {
        refrainLength = refrainBegin = refrainCount = 0;
        findCount(root);
        findCurrent(root);
        out.println(refrainLength * refrainCount);
        out.println(refrainLength);
        ArrayList<String> answer = new ArrayList<String>();
        while (true) {
            if (edge == null) break;
            int finish = edge.end;
            if (finish == stringLength)
                --finish;

        answer.add(str.substring(edge.begin, finish));
        edge = edge.parent;
        }
        int i = answer.size() - 1;
        while (i >= 0) {
            for (int j = 0; j <  answer.get(i).length(); j++)
                out.print((answer.get(i).charAt(j) - 'a' + 1) + " ");
            i--;
        }
        return 0;
    }

    int stringLength = 0;
    String str = "";

    void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += (char)('a' + in.nextInt() - 1);
        }
        s += "$";
        str = s;
        stringLength = s.length();
        SuffixTree tree = new SuffixTree(s);
        findRefrain(tree.getRoot());
    }
    public static void main(String[] args) {
        new E().run();
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