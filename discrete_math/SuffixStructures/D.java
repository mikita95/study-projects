import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "automaton";

    class Automata {
        public class State {
            long length;
            int suffixLink;
            HashMap<Character, Integer> children;

            State() {
                children = new HashMap<Character, Integer>();
                length = suffixLink = 0;
            }
        }

        Automata(String s) {
            states = new State[100004 * 2];
            for (int i = 0; i < states.length; i++)
                states[i] = new State();
            size = 1;
            last = 0;
            states[0].length = 0;
            states[0].suffixLink = -1;
            edges = 0;
            for (int i = 0; i < s.length(); i++)
                addChar(s.charAt(i));

        }

        public long getEdgesCount() {
            return edges;
        }

        public long getNodesCount() {
            return size;
        }

        public State[] getStates() {
            return states;
        }

        State states[];
        int size, last;
        long edges;

        public int getIndexOfStateOfString() {
            return last;
        }

        void addChar(char ch) {

            int current = size++;
            states[current].length = states[last].length + 1;
            int lingIndex;
            for (lingIndex = last; lingIndex != -1 && !states[lingIndex].children.containsKey(ch); lingIndex = states[lingIndex].suffixLink) {
                edges++;
                states[lingIndex].children.put(ch, current);
            }

            if (lingIndex == -1) {
                states[current].suffixLink = 0;

            } else {
                int chIndex = states[lingIndex].children.get(ch);
                if (states[chIndex].length == states[lingIndex].length + 1)
                    states[current].suffixLink = chIndex;
                else {
                    int cl = size++;
                    states[cl].length = states[lingIndex].length + 1;
                    edges += states[chIndex].children.size();
                    states[cl].children = states[chIndex].children;
                    states[cl].suffixLink = states[chIndex].suffixLink;
                    while (lingIndex != -1 && states[lingIndex].children.get(ch) == chIndex) {
                        states[lingIndex].children.put(ch, cl);
                        lingIndex = states[lingIndex].suffixLink;
                    }
                    states[chIndex].suffixLink = states[current].suffixLink = cl;
                }

            }
            last = current;
        }

    }


    public static void main(String[] args) {
        new D().run();
    }

    void solve() throws IOException {
        String s = in.next();
        Automata automata = new Automata(s);
        out.println(automata.getNodesCount() + " " + automata.getEdgesCount());
        Automata.State states[] = automata.getStates();
        for (int i = 0; i < automata.getNodesCount(); i++) {
            HashMap<Character, Integer> map = states[i].children;
            for (HashMap.Entry<Character, Integer> e : map.entrySet()) {
                out.println((i + 1) + " " + (e.getValue() + 1) + " " + e.getKey());
            }
        }
        ArrayList<Integer> answer = new ArrayList<Integer>();
        for (int i = automata.getIndexOfStateOfString(); i != -1; i = states[i].suffixLink)
            answer.add(i);
        out.println(answer.size());
        for (Integer p : answer) {
            out.print((p + 1) + " ");
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

        public long nextInteger() {
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