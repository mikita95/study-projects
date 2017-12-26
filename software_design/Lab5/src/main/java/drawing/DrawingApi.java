package drawing;

/**
 * Created by nikita on 25.11.16.
 */
public interface DrawingApi {
    void drawCircle(double x, double y, double r);
    void drawLine(double x1, double y1, double x2, double y2);
    void drawText(String text, double x, double y, int fontSize);
    int getDrawingAreaWidth();
    int getDrawingAreaHeight();
    void draw();
}
