import drawing.DrawingApi;
import drawing.DrawingApiAwt;
import drawing.DrawingApiJavaFx;
import graph.Graph;
import graph.GraphIncident;
import graph.GraphMatrix;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: (awt | javafx) (incident | matrix) filename.txt");
            return;
        }

        DrawingApi drawingApi = null;

        switch (args[0]) {
            case "awt": drawingApi = new DrawingApiAwt(); break;
            case "javafx": drawingApi = new DrawingApiJavaFx(); break;
        }

        assert drawingApi != null;

        try {
            Graph graph = null;
            InputStream is = new FileInputStream(args[2]);
            FastScanner in = new FastScanner(is);
            switch (args[1]) {
                case "incident": {
                    Map<Integer, List<Integer>> incident = new HashMap<>();
                    while (in.hasNext()) {
                        String[] splitted = in.nextLine().split(" ");
                        List<Integer> nodes = Arrays.
                                stream(splitted).
                                skip(1).mapToInt(s -> Integer.parseInt(s) - 1).
                                boxed().
                                collect(Collectors.toList());
                        incident.put(Integer.parseInt(splitted[0]) - 1, nodes);
                    }
                    graph = new GraphIncident(drawingApi, incident);
                    break;
                }
                case "matrix": {
                    int n = in.nextInt();
                    int[][] matrix = new int[n][n];
                    for (int i = 0; i < n; i++) {
                        for (int k = 0; k < n; k++) {
                            matrix[i][k] = in.nextInt();
                        }
                    }
                    graph = new GraphMatrix(drawingApi, matrix);
                    break;
                }
            }
            assert graph != null;
            graph.draw();
            drawingApi.draw();
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

        public String nextLine() { return tokenizer.nextToken("\n"); }

        public String next() {
            return tokenizer.nextToken();
        }

    }
}