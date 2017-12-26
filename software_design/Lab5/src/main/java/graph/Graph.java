package graph;

import drawing.DrawingApi;

/**
 * Created by nikita on 25.11.16.
 */
public abstract class Graph {
    protected final DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void draw();
}

