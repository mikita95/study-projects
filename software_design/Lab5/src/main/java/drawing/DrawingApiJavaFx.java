package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by nikita on 25.11.16.
 */
public class DrawingApiJavaFx extends Application implements DrawingApi {

    private final static int WIDTH = 900;
    private final static int HEIGHT = 600;

    private final static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private final static GraphicsContext context = canvas.getGraphicsContext2D();

    public DrawingApiJavaFx() {}

    @Override
    public int getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public int getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        context.setStroke(Color.rgb(41, 150, 52));
        context.setFill(Color.rgb(61, 227, 78));
        context.fillOval(y - r, x - r, r * 2, r * 2);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        context.setFill(Color.rgb(61, 227, 188));
        context.setStroke(Color.rgb(41, 150, 125));
        context.moveTo(y1, x1);
        context.lineTo(y2, x2);
        context.stroke();
    }

    @Override
    public void drawText(String text, double x, double y, int fontSize) {
        context.setFill(Color.rgb(105, 105, 105));
        context.setFont(new Font(fontSize));
        context.fillText(text, y, x);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Graph");
        Group root = new Group();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void draw() {
        launch();
    }
}
