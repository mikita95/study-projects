import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G {
    private FastScanner in;
    private PrintWriter out;
    private final static String FILE_NAME = "artificial";

    public static void main(String[] args) {
        new G().run();
    }

    void solve() throws IOException {

        ArrayList<String> result = new ArrayList<>();
        result.add("");
        result.add( "3 2 L M\n" +
                    "3 3 R M\n" +
                    "2 4 R M\n" +
                    "2 1 M M\n");

        result.add( "1 1 L M\n");

        result.add( "2 3 L M\n" +
                    "3 2 R M\n" +
                    "1 2 M R");

        result.add( "2 1 M M\n" +
                    "3 4 M M\n" +
                    "1 1 R L\n" +
                    "1 4 L M\n");

        result.add( "3 2 M M\n" +
                    "1 1 R M\n" +
                    "2 3 M L");

        result.add( "2 2 R M\n" +
                    "3 4 M M\n" +
                    "3 5 L M\n" +
                    "3 5 M M\n" +
                    "1 2 M M\n");

        result.add( "2 4 R M\n" +
                    "2 3 L M\n" +
                    "2 4 M M\n" +
                    "1 3 M M\n");

        result.add( "2 3 M M\n" +
                    "3 3 L M\n" +
                    "4 3 M M\n" +
                    "5 2 R M\n" +
                    "2 1 L M\n");

        result.add( "2 3 M M\n" +
                    "4 3 R M\n" +
                    "5 6 M M\n" +
                    "6 6 L M\n" +
                    "1 2 R M\n" +
                    "5 1 L M\n");

        result.add( "2 3 L M\n" +
                    "4 4 M M\n" +
                    "2 4 R M\n" +
                    "5 6 R M\n" +
                    "1 1 L M\n" +
                    "1 4 R M\n");
        out.println(result.get(in.nextInt()));
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