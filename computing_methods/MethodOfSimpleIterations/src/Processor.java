import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.*;
import java.util.function.BiFunction;

import static org.lwjgl.opengl.GL11.*;

public class Processor {
    public static double MAX_X = 4;
    public static final double STEP = 0.0001;
    public static int LENGTH = 800;
    public static int TOP = 600;
    public static int PADDING = 25;
    public static int LENGTH_SEPARATOR = 30;
    public static boolean isOriginal = true;
    public static boolean isClick = true;
    public static int x, y;
    public static int x0, y0;
    public static BiFunction<Double, Double, Double> function = (x, r) -> r * x * (1 - x);

    private static Map<Double, ArrayList<Double>> points;

    private static void prepare() {
        try {
            Display.setFullscreen(true);
            Display.create();
            TOP = Display.getHeight();
            LENGTH = Display.getWidth();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, LENGTH, 0, TOP, 1, -1);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        glColor3f(0f, 0f, 0f);
        glPointSize(1.2f);
        glLineWidth(0);
    }

    public static void start(double r, double initialValue, int plot) {
        prepare();
        switch (plot) {
            case 1: {
                calculateSequence(r, initialValue);
                break;
            }
            case 3: {
                calculateInLimit();
                break;
            }

        }

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            onDraw(plot, r, initialValue);

            inputMouse();
            inputKey();

            Display.update();

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                break;
        }
        Display.destroy();
    }

    private static void inputMouse() {
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0) {
                    x = Mouse.getX();
                    y = Mouse.getY();
                    isClick = true;
                }
            } else {
                if (isClick && !isOriginal) {
                    x0 += Mouse.getX() - x;
                    y0 += Mouse.getY() - y;
                    glTranslated(Mouse.getX() - x, Mouse.getY() - y, 0);
                    x = Mouse.getX();
                    y = Mouse.getY();
                }
                if (Mouse.getEventButton() == 0)
                    isClick = false;
            }

        }

    }

    private static void inputKey() {
        while (Keyboard.next()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if (isOriginal) {
                    TOP *= 2;
                    LENGTH *= 2;
                    PADDING *= 2;
                    LENGTH_SEPARATOR *= 2;
                    isOriginal = false;
                } else {
                    TOP /= 2;
                    LENGTH /= 2;
                    PADDING /= 2;
                    LENGTH_SEPARATOR /= 2;
                    glTranslated(-x0, -y0, 0);
                    x0 = 0;
                    y0 = 0;
                    isOriginal = true;
                }
            }


        }
    }

    private static void onDraw(int plot, double r, double value) {
        glColor3f(0f, 0f, 0f);
        //Y
        glBegin(GL_LINES);
        glVertex2f(PADDING, 0);
        glVertex2f(PADDING, TOP);
        glEnd();
        //X
        glBegin(GL_LINES);
        glVertex2f(0, PADDING);
        glVertex2f(LENGTH, PADDING);
        glEnd();

        glBegin(GL_LINES);
        glVertex2f(PADDING - (LENGTH_SEPARATOR / 2), TOP);
        glVertex2f(PADDING + (LENGTH_SEPARATOR / 2), TOP);
        glEnd();

        for (int i = 1; i <= MAX_X; i++) {
            glBegin(GL_LINES);
            glVertex2f(
                    (float) (PADDING + i * ((LENGTH - PADDING) / MAX_X)),
                    PADDING - (LENGTH_SEPARATOR / 2)
            );
            glVertex2f(
                    (float) (PADDING + i * ((LENGTH - PADDING) / MAX_X)),
                    PADDING + (LENGTH_SEPARATOR / 2)
            );
            glEnd();
        }

        drawPoints(plot, r, value);
    }

    private static void drawPoints(int plot, double r, double value) {
        glColor3f(0.5f, 0.5f, 0.5f);
        if (plot == 3) {
            glBegin(GL_POINTS);
            ArrayList<Double> keys = new ArrayList<>(points.keySet());
            Collections.sort(keys);
            for (Double i : keys) {
                final double a = i;
                points.get(i).stream().forEach(z -> glVertex2d(getCoordinateX(a), getCoordinateY(z)));
            }
            glEnd();
        } else if (plot == 1) {
            glBegin(GL_LINES);
            for (double i = 1; i < points.size(); i++) {

                final double a = i - 1;
                points.get(i - 1).stream().forEach(z -> glVertex2d(getCoordinateX(a), getCoordinateY(z)));
                final double b = i;
                points.get(i).stream().forEach(z -> glVertex2d(getCoordinateX(b), getCoordinateY(z)));

            }
            glEnd();
        } else if (plot == 2) {
            MAX_X = 1;
            glColor3f(0f, 0f, 1f);
            glBegin(GL_LINES);
            glVertex2d(getCoordinateX(0), getCoordinateY(0));
            glVertex2d(getCoordinateX(MAX_X), getCoordinateY(MAX_X));
            glEnd();
            glColor3f(0f, 1f, 0f);
            glBegin(GL_POINTS);
            for (double i = 0; i < MAX_X; i += STEP) {
                glVertex2d(getCoordinateX(i), getCoordinateY(function.apply(i, r)));
            }
            glEnd();
            glColor3f(1f, 0f, 0f);
            double a = value;
            double b = function.apply(a, r);
            int k = 0;
            glBegin(GL_LINES);
            glVertex2d(getCoordinateX(a), getCoordinateY(0));
            glVertex2d(getCoordinateX(a), getCoordinateY(b));
            while (Math.abs(a - b) >= 1e-9 && k < 1000) {
                glVertex2d(getCoordinateX(a), getCoordinateY(b));
                glVertex2d(getCoordinateX(b), getCoordinateY(b));
                glVertex2d(getCoordinateX(b), getCoordinateY(b));
                a = b;
                b = function.apply(a, r);
                glVertex2d(getCoordinateX(a), getCoordinateY(b));
                k++;
            }
            glEnd();
        }
    }

    private static void calculateSequence(double r, double initialValue) {
        Solver solver = new Solver(initialValue, function, 1e-5, 1000);
        points = solver.solveSequence(r);
        MAX_X = points.size();
    }

    private static void calculateInLimit() {
        Solver solver = new Solver(0.5, function, 1e-5, 1000);
        points = new HashMap<>();
        for (double i = 0; i < MAX_X; i += STEP) {
            points.put(i, solver.solveInLimit(i));
        }
    }

    private static double getCoordinateX(double c) {
        return PADDING + c * ((LENGTH - PADDING) / MAX_X);
    }

    private static double getCoordinateY(double c) {
        return PADDING + c * (TOP - PADDING);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double r, x0;
        int plot;
        try {
            r = scanner.nextDouble();
            x0 = scanner.nextDouble();
            plot = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("USING: [r] [initial value] [number of plot]");
            return;
        }
        start(r, x0, plot);
    }
}
