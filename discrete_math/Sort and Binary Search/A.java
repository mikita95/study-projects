import java.io.*;
import java.util.StringTokenizer;

public class A {

    public InputStream is;
    public FastScanner in;
    public PrintWriter out;

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream("sort.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("sort.out"));
        int n = in.nextInt();
        int data[] = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        quickSort(data, 0, n - 1);
        out.print(data[0]);
        for (int i = 1; i < n; i++) {
            out.print(' ');
            out.print(data[i]);
        }


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

        public String list() {
            return tokenizer.nextToken();
        }


    }

    void quickSort(int data[], int l, int r) {
        int i = l;
        int j = r;
        int x = data[l + (int) (Math.random() * ((r - l) + 1))];
        while (i < j) {
            while (data[i] < x)
                i++;
            while (data[j] > x)
                j--;
            if (i <= j) {
                int c = data[i];
                data[i] = data[j];
                data[j] = c;
                i++;
                j--;
            }
        }
        if (i < r)
            quickSort(data, i, r);
        if (l < j)
            quickSort(data, l, j);


    }

}