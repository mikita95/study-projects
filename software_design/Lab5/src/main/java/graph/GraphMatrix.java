package graph;

import drawing.DrawingApi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by nikita on 25.11.16.
 */
public class GraphMatrix extends OrientedGraph {
    private final int[][] matrix;

    public GraphMatrix(DrawingApi drawingApi, int[][] matrix) {
        super(drawingApi, matrix.length);
        this.matrix = matrix;
    }

    @Override
    public List<Integer> getEdges(int v) {
        return IntStream.range(0, matrix.length).
                boxed().
                map(i -> matrix[v][i] == 0 ? null : i).
                filter(x -> x != null).
                collect(Collectors.toList());
    }
}
