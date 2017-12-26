import java.io.*;
import java.util.StringTokenizer;

public class F {

    public InputStream is;
    public FastScanner in;
    public PrintWriter out;


    public static void main(String[] args) {
        new F().run();
    }

    void solve() throws IOException {
        is = new FileInputStream("antiqs.in");
        in = new FastScanner(is);
        out = new PrintWriter(new File("antiqs.out"));

        int n = in.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i + 1;
        for (int i = 2; i < n; i++)
            swap(a, i, i / 2);
        for (int i = 0; i < n; i++)
            out.print(a[i] + " ");

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

    void swap(int a[], int x, int y) {
        int c = a[x];
        a[x] = a[y];
        a[y] = c;
    }




}