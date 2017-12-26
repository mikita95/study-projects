package graph;

import drawing.DrawingApi;

import java.util.List;

/**
 * Created by nikita on 25.11.16.
 */
public abstract class OrientedGraph extends Graph {

    private final double VERTEX_RADIUS = 32d;
    private final static int FONT_SIZE = 30;
    private final static double ARROW_SIZE = 10d;

    private final int vertices;
    private double degree;
    private double centerX, centerY;
    private double radius;

    public OrientedGraph(DrawingApi drawingApi, int vertices) {
        super(drawingApi);
        this.vertices = vertices;
        this.degree = 2d * Math.PI / vertices;
        this.centerX = drawingApi.getDrawingAreaHeight() / 2d;
        this.centerY = drawingApi.getDrawingAreaWidth() / 2d;
        this.radius = Math.min(drawingApi.getDrawingAreaWidth(), drawingApi.getDrawingAreaHeight()) / 2 - VERTEX_RADIUS * 2;
    }

    public abstract List<Integer> getEdges(int v);

    @Override
    public void draw() {
        drawVertices();
        drawEdges();
    }

    private void drawVertices() {
        for (int i = 0; i < vertices; i++) {
            double x = getX(i, degree, radius, centerX);
            double y = getY(i, degree, radius, centerY);
            drawingApi.drawCircle(x, y, VERTEX_RADIUS);
            drawingApi.drawText("" + (i + 1), x + FONT_SIZE / 3d, y - FONT_SIZE / 3d, FONT_SIZE);
        }
    }

    private void drawEdges() {
        for (int i = 0; i < vertices; i++) {
            double x = getX(i, degree, radius, centerX);
            double y = getY(i, degree, radius, centerY);
            for (Integer to: getEdges(i)) {
                double toX = getX(to, degree, radius, centerX);
                double toY = getY(to, degree, radius, centerY);

                double d = Math.sqrt((toX - x) * (toX - x) + (toY - y) * (toY - y));

                double deltaX = VERTEX_RADIUS / d * (toX - x);
                double deltaY = VERTEX_RADIUS / d * (toY - y);

                double dToX = toX - deltaX;
                double dToY = toY - deltaY;

                double aDeltaX = ARROW_SIZE * (y - toY) / d;
                double aDeltaY = ARROW_SIZE * (toX - x) / d;

                drawingApi.drawLine(x + deltaX, y + deltaY, toX - deltaX, toY - deltaY);
                drawingApi.drawLine(dToX, dToY, dToX + aDeltaX - deltaX, dToY + aDeltaY - deltaY);
                drawingApi.drawLine(dToX, dToY, dToX - aDeltaX - deltaX, dToY - aDeltaY - deltaY);

            }
        }
    }

    private double getX(int index, double degree, double radius, double centerX) {
        return centerX - radius * Math.cos(degree * index);
    }

    private double getY(int index, double degree, double radius, double centerY) {
        return centerY - radius * Math.sin(degree * index);
    }
}
