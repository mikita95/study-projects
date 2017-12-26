package data;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by nikita on 04.12.16.
 */
public class Feature {
    public double[] x;
    public int width, height;

    public Feature(int width, int height) {
        this.x = new double[width * height];
        this.width = width;
        this.height = height;
    }

    public void set(int i, double v) {
        x[i] = v;
    }

    public void setStraight(int i, int k, double v) {
        x[i + width * k] = v;
    }

    public Feature(BufferedImage image) {
        this.width = image.getWidth();
        this. height = image.getHeight();
        x = new double[width * height];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixels.length; i++) {
            int p = pixels[i];
            x[i] = (p < 0 ? (256 + p) : p) / 255d;
        }
        double[][] centered = center(transform(x));
        for (int k = 0; k < height; k++) {
            for (int i = 0; i < width; i++) {
                x[i + width * k] = centered[i][k];
            }
        }
    }

    private double[][] transform(double[] v) {
        double[][] result = new double[28][28];
        for (int k = 0; k < height; k++) {
            for (int i = 0; i < width; i++) {
                result[i][k] = x[i + width * k];
            }
        }
        return result;
    }

    private double[][] center(double[][] input) {
        double cx = 0, cy = 0, m = 0;

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {

                cx += input[x][y] * x;
                cy += input[x][y] * y;
                m += input[x][y];
            }
        }

        int cmx = (int) (cx / m);
        int cmy = (int) (cy / m);

        double[][] result = new double[28][28];

        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 28; y++) {
                if (input[x][y] > 0) {
                    int newX = x + 13 - cmx;
                    int newY = y + 13 - cmy;
                    if (newX >= 28 || newY >= 28 || newX < 0 || newY  < 0)
                        continue;
                    result[newX][newY] = input[x][y];
                }
            }
        }
        return result;
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int k = 0; k < height; k++) {
            for (int i = 0; i < width; i++) {
                image.setRGB(i, k, (byte) (x[i + width * k] * 255));
            }
        }
        return image;
    }

    public Feature(Feature other) {
        this.width = other.width;
        this.height = other.height;
        x = new double[other.size()];
        System.arraycopy(other.x, 0, x, 0, size());
    }

    public int size() {
        return x.length;
    }
    public double get(int i) {
        return x[i];
    }

    public void addHead(double v) {
        double[] newX = new double[size() + 1];
        newX[0] = v;
        System.arraycopy(x, 0, newX, 1, x.length);
        x = newX;
    }
}
