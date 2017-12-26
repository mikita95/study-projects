import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G {
    private final static String FILE_NAME = "search4";

    public static void main(String[] args) {
        new G().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream(FILE_NAME + ".in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File(FILE_NAME + ".out"));
        int n = in.nextInt();
        AC tree = new AC(n);
        for (int i = 0; i < n; i++) {
            tree.add(in.next());
        }
        String text = in.next();
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ArrayList<Boolean> cond = new ArrayList<Boolean>();
        for (int i = 0; i < n; i++)
            ans.add(0);
        int cur = 0;
        for (int i = 0; i < tree.tree.size(); i++) {
            cond.add(false);
        }
        for (int i = 0; i < text.length(); ++i) {
            char c = (char)(text.charAt(i) - 'a');
            cur = tree.go(cur, c);
            int cur_link = cur;
            while (true) {
                if (cond.get(cur_link)) {
                    break;
                }

                if (tree.tree.get(cur_link).leaf) {
                    cond.set(cur_link, true);
                }

                if (cur_link == 0) break;
                cur_link = tree.prev_leaf(cur_link);
            }
        }
        for (int i = 0; i < n; i++) {
            if (cond.get(tree.indexes.get(i)))
                out.println("YES");
            else
                out.println("NO");

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

    class AC {
        ArrayList<Node> tree;
        ArrayList<Integer> indexes;
        int sz;
        AC(int n) {
            Node t = new Node();
            t.p = t.link = -1;
            tree = new ArrayList<Node>();
            tree.add(t);
            sz = 0;
            indexes = new ArrayList<Integer>();
            for (int i = 0; i < n; i++)
                indexes.add(-1);
        }

        void add(String s) {
            int cur = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = (char) (s.charAt(i) - 'a');
                if (tree.get(cur).next[c] == -1) {
                    Node t = new Node();
                    t.link = -1;
                    t.p = cur;
                    t.pch = c;
                    tree.get(cur).next[c] = tree.size();
                    tree.add(t);
                }
                cur = tree.get(cur).next[c];
            }
            tree.get(cur).leaf = true;
            indexes.set(sz, cur);
            ++sz;
        }

        int getLink(int v) {
            if (tree.get(v).link == -1) {
                if (v == 0 || tree.get(v).p == 0)
                    tree.get(v).link = 0;
                else tree.get(v).link = go(getLink(tree.get(v).p), tree.get(v).pch);
            }
            return tree.get(v).link;
        }

        int go(int v, char c) {
            if (tree.get(v).go[c] == -1) {
                if (tree.get(v).next[c] != -1) {
                    tree.get(v).go[c] = tree.get(v).next[c];
                } else {
                    if (v == 0) {
                        tree.get(v).go[c] = 0;
                    } else {
                        tree.get(v).go[c] = go(getLink(v), c);
                    }
                }
            }
            return tree.get(v).go[c];
        }

        int prev_leaf(int v) {
            if (tree.get(v).prev_leaf == -1) {
                if (v == 0) {
                    tree.get(v).prev_leaf = 0;
                } else
                {
                    int prev = getLink(v);
                    if (tree.get(prev).leaf) {
                        tree.get(v).prev_leaf = prev;
                    } else
                    {
                        tree.get(v).prev_leaf = prev_leaf(prev);
                    }
                }
            }
            return tree.get(v).prev_leaf;
        }

        class Node {
            int prev_leaf;
            boolean leaf;
            int link;
            int next[] = new int[26];
            char pch;
            int go[] = new int[26];
            int p;

            Node() {
                for (int i = 0; i < 26; i++) {
                    next[i] = -1;
                    go[i] = 255;
                }
                leaf = false;
                prev_leaf = -1;
            }
        }
    }


}