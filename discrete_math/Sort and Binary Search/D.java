import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class D {
 
    public InputStream is;
    public FastScanner in;
    public PrintWriter out;
    ArrayList<Double> h;
    double eps = 1e-9;
 
    public static void main(String[] args) {
        new D().run();
    }
 
    void solve() throws IOException {
        is = new FileInputStream("garland.in");
        in = new FastScanner(is);
        out = new PrintWriter(new File("garland.out"));
        int n = in.nextInt();
        h = new ArrayList<Double>(n);
        for (int i = 0; i < n; i++)
            h.add(0.);
        h.set(0, in.nextDouble());
        double res = 1e9;
        double left = 0, right = h.get(0);
        while (less(left, right)) {
            h.set(1, left + (right  - left) / 2);
            h.set(h.size() - 1, 0.);
            boolean flag = false;
            for (int i = 2; i < n; i++) {
 
                h.set(i, getPoint(i));
 
                if (!more(h.get(i), 0)) {
                    flag = true;
                    break;
                }
            }
            if (more(h.get(h.size() - 1), 0))
                res = Math.min(res, h.get(h.size() - 1));
            if (flag)
                left = h.get(1);
            else
                right = h.get(1);
        }
 
 
        String tmp = String.format("%.2f", res);
        tmp = tmp.replaceAll(",", ".");
        out.println(tmp);
 
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
 
    double getPoint(int i) {
        return 2 * h.get(i - 1) - h.get(i - 2) + 2;
    }
 
 
 
    boolean equal(double a, double b) {
        double tmp = a - b;
        if (tmp < 0)
            tmp = -tmp;
        return tmp <= eps;
    }
 
    boolean less(double a, double b) {
        return a < b && !equal(a, b);
    }
 
    boolean more(double a, double b) {
        return a > b && !equal(a, b);
    }
 
}