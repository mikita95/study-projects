package graph;

import drawing.DrawingApi;

import java.util.List;
import java.util.Map;

/**
 * Created by nikita on 25.11.16.
 */
public class GraphIncident extends OrientedGraph {

    private final Map<Integer, List<Integer>> edges;

    public GraphIncident(DrawingApi drawingApi, Map<Integer, List<Integer>> edges) {
        super(drawingApi, edges.size());
        this.edges = edges;
    }

    @Override
    public List<Integer> getEdges(int v) {
        return edges.get(v);
    }
}
