package drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

/**
 * Created by nikita on 25.11.16.
 */
public class DrawingApiAwt extends Frame implements DrawingApi {

    private final int WIDTH = 900;
    private final int HEIGHT = 600;

    public DrawingApiAwt() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        graphics2D.setPaint(Color.ORANGE);
        graphics2D.fill(new Ellipse2D.Float((float) (y - r), (float) (x - r), (float) r * 2, (float) r * 2));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        graphics2D.setColor(Color.lightGray);
        graphics2D.drawLine((int) y1, (int) x1, (int) y2, (int) x2);
    }

    @Override
    public void drawText(String text, double x, double y, int fontSize) {
        Graphics2D graphics2D = (Graphics2D) getGraphics();
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        graphics2D.drawString(text, (float) y, (float) x);
    }

    @Override
    public int getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public int getDrawingAreaHeight() {
        return HEIGHT;
    }


    @Override
    public void draw() {
        super.paint(getGraphics());
    }
}
