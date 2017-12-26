import java.io.*;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

public class C {
    private FastScanner in;
    private static PrintWriter out;
    private final static String FILE_NAME = "tree";
    public static void main(String[] args) {
        new C().run();
    }

    class SuffixTree {
        static final String ALPHA = "abcdefghijklmnopqrstuvwxyz$&";
        private Node root;
        private int nodesCount, edgesCount;
        public SuffixTree(final String s) {
            root = buildSuffixTree(s);
            nodesCount = 1;
            edgesCount = 0;
            count(root);
        }

        public int getNodesCount() {
            return nodesCount;
        }

        public int getEdgesCount() {
            return edgesCount;
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

            Node(int begin, int end, int depth, Node parent) {
                children = new Node[ALPHA.length()];
                this.begin = begin;
                this.end = end;
                this.parent = parent;
                this.depth = depth;
            }

            boolean contains(int d) {
                if (depth <= d && d < depth + (end - begin)) return true;
                else return false;
            }
        }

        private void count(Node v) {
            for (int i = 0; i < v.children.length; i++) {
                if (v.children[i] != null && i != ALPHA.length() - 2) {
                    edgesCount++;
                    count(v.children[i]);
                    nodesCount++;
                }
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

    static void dump(SuffixTree.Node v, AtomicReference<Integer> count) {
        int id = count.get();
        for (int i = 0; i < v.children.length; i++) {
            SuffixTree.Node curr = v.children[i];
            if (curr == null || i == SuffixTree.ALPHA.length() - 2)
                continue;
            count.set(count.get() + 1);
            out.println(id + " " + (count.get()) + " " + (curr.begin + 1) + " " + curr.end);
            dump(curr, count);
        }
    }

    void solve() throws IOException {
        String s = in.next();
        SuffixTree tree = new SuffixTree(s);
        out.println(tree.getNodesCount() + " " + tree.getEdgesCount());
        AtomicReference<Integer> count = new AtomicReference<Integer>(1);
        dump(tree.getRoot(), count);
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