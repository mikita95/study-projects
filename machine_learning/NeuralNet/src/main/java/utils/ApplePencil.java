package utils;

import data.Feature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Created by sergeybp on 18.12.16.
 */
public class ApplePencil extends JPanel implements MouseListener, MouseMotionListener {

    protected int lastX, lastY;
    private BufferedImage image;
    private Graphics2D imageGraphics;
    private Color painting;
    private int strokeWidth;

    public ApplePencil() {
        this.setStroke(20);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setColor(Color.WHITE);
        setBackground(Color.BLACK);
        image = new BufferedImage(280, 280, BufferedImage.TYPE_BYTE_GRAY);
        imageGraphics = (Graphics2D) image.getGraphics();
    }

    public Feature getFeature() {
        return new Feature(resize(image, 28, 28));
    }

    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setStroke(new BasicStroke((float) strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(painting);
        g.drawLine(lastX, lastY, x, y);
        imageGraphics.setStroke(new BasicStroke((float) strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        imageGraphics.setColor(painting);
        imageGraphics.drawLine(lastX, lastY, x, y);
        lastX = x;
        lastY = y;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image temp = img.getScaledInstance(newW, newH, Image.SCALE_REPLICATE);
        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }

    public void setColor(Color clr) {
        painting = clr;
    }


    public void setStroke(int width) {
        strokeWidth = width;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}
