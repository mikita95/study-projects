import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class G {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
    boolean flag = true;
 
    private class pair {
        int first;
        int second;
 
        pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
 
        pair() {
            first = second = 0;
        }
 
    }
 
    private class Network {
        ArrayList<pair> net = new ArrayList<pair>();
        Network(ArrayList<pair> net) {
            this.net = net;
        }
 
    }
 
    Network sortNet;
 
 
    public static void main(String[] args) {
        new G().run();
    }
 
    void solve() throws IOException {
        is = new FileInputStream("netcheck.in");
        in = new FastScanner(is);
        out = new PrintWriter(new File("netcheck.out"));
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        ArrayList<pair> comp = new ArrayList<pair>(m);
 
        for (int i = 0; i < k; i++) {
            int r = in.nextInt();
            for (int j = 0; j < r; j++) {
                int a = in.nextInt();
                int b = in.nextInt();
                if (a < b) {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                comp.add(new pair(b - 1, a - 1));
            }
        }
 
        sortNet = new Network(comp);
        String tmp = new String();
        gen(tmp, 0, n);
        if (flag)
            out.println("Yes");
        else
            out.println("No");
 
        out.close();
    }
 
    public void run() {
        try {
            solve();
        } catch (IOException e) {
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
 
    boolean check(String ss) {
        char[] mas = new char[ss.length()];
        mas = ss.toCharArray();
 
        char tmp;
        for (int i = 0; i < sortNet.net.size(); i++) {
            if (mas[sortNet.net.get(i).first] > mas[sortNet.net.get(i).second]) {
                tmp = mas[sortNet.net.get(i).first];
                mas[sortNet.net.get(i).first] = mas[sortNet.net.get(i).second];
                mas[sortNet.net.get(i).second] = tmp;
            }
        }
 
        for (int i = 0; i < mas.length - 1; i++) {
            if (mas[i] > mas[i + 1])
                return false;
        }
        return true;
    }
 
    void gen(String s, int p, int n) {
        if (p == n) {
            if (!check(s))
                flag = false;
        } else {
            for (char c = '0'; c <= '1'; c++) {
 
                gen(s + c, p + 1, n);
 
            }
        }
    }
 
 
}