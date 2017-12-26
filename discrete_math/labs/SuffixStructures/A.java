import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "trie";

    class SuffixTrie {
        private ArrayList<HashMap<Character, Integer>> trie;
        private int nodeCount;
        private int edgeCount;
        public SuffixTrie(final String s) {
            nodeCount = 1;
            edgeCount = 0;
            trie = new ArrayList<HashMap<Character, Integer>>();
            for (int i = 0; i < s.length() * s.length(); i++)
                trie.add(new HashMap<Character, Integer>());
            for (int i = 0; i < s.length(); i++) {
                int current = 0;
                for (int k = i; k < s.length(); k++) {
                    char c = s.charAt(k);
                    if (!trie.get(current).containsKey(c)) {
                        trie.get(current).put(c, nodeCount);
                        nodeCount++;
                    }
                    current = trie.get(current).get(c);
                }
            }
            for (HashMap<Character, Integer> aTrie : trie) {
                edgeCount += aTrie.size();
            }
        }

        public int getNumberOfNodes() {
            return nodeCount;
        }

        public int getNumberOfEdges() {
            return edgeCount;
        }

        public HashMap<Character, Integer> getEdges(int node) {
            return trie.get(node);
        }
    }

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        SuffixTrie trie = new SuffixTrie(in.next());
        out.println(trie.getNumberOfNodes() + " " + trie.getNumberOfEdges());
        for (int i = 0; i < trie.getNumberOfNodes(); i++) {
            HashMap<Character, Integer> map = trie.getEdges(i);
            for (HashMap.Entry<Character, Integer> e : map.entrySet()) {
                out.println(i + 1 + " " + (e.getValue() + 1) + " " + e.getKey());
            }
        }
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