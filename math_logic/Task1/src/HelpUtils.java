public class HelpUtils {
    public static class HashNode {
        long degree[];
        private final static int N = 8000;
        HashNode() {
            degree = new long[N * N];
            degree[0] = 1;
            degree[1] = 31;
            for (int i = 2; i < N * N; i++)
                degree[i] = degree[i - 1] * degree[1];
        }
        long getHash(int i) {
            return degree[i];
        }
    }
    static HashNode hashNode = new HashNode();
    HelpUtils() {
       hashNode = new HashNode();
    }
}
