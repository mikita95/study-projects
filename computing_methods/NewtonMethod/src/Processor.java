import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import static org.lwjgl.opengl.GL11.*;

public class Processor {
    public static double MAX_X = 2;
    public static final double STEP = 0.0025;
    public static double LENGTH = 800;
    public static double TOP = 600;

    public static Function<Complex, Complex> function = z -> z.times(z).times(z).minus(new Complex(1, 0));
    public static Function<Complex, Complex> derivative = z -> z.times(z).times(new Complex(2, 0));
    public static Complex z1 = new Complex(Math.cos(2 * Math.PI / 3), Math.sin(2 * Math.PI / 3));
    public static Complex z2 = new Complex(Math.cos(4 * Math.PI / 3), Math.sin(4 * Math.PI / 3));
    public static Complex z3 = new Complex(Math.cos(6 * Math.PI / 3), Math.sin(6 * Math.PI / 3));

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

    public static void start(Complex v) {
        prepare();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //long m = System.currentTimeMillis();
        onDraw(v);
        //m = System.currentTimeMillis() - m;
        //System.out.println(m);
        Display.update();
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.update();
        }
        Display.destroy();
    }

    private static void onDraw(Complex v) {

        double epsilon = 1e-5;
        glBegin(GL_POINTS);
        for (double x = -MAX_X; x <= MAX_X; x += STEP) {
            for (double y = 0; y <= MAX_X; y += STEP) {
                ArrayList<Complex> result = solve(new Complex(x, y), epsilon, 1000);
                Complex last = result.get(result.size() - 1);
                double c = (-7. / (8 * 50) * result.size() + 1 + 7. / 200);
                if (last.toString().isEmpty()) {
                    glColor3f(0f, 0f, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(y));
                } else if (last.minus(z1).mod() < epsilon) {
                    glColor3d(c, 0f, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(y));
                    glColor3d(0f, c, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(-y));
                } else if (last.minus(z2).mod() < epsilon) {
                    glColor3d(0f, c, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(y));
                    glColor3d(c, 0f, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(-y));
                } else if (last.minus(z3).mod() < epsilon) {
                    glColor3d(0f, 0f, c);
                    glVertex2d(getCoordinateX(x), getCoordinateY(y));
                    glVertex2d(getCoordinateX(x), getCoordinateY(-y));
                } else {
                    glColor3f(0f, 0f, 0f);
                    glVertex2d(getCoordinateX(x), getCoordinateY(y));
                }

            }
        }

        glEnd();
        ArrayList<Complex> result = solve(v, epsilon, 1000);
        Complex last = result.get(result.size() - 1);
        if (!last.toString().isEmpty()) {
            glColor3f(1f, 1f, 0f);
            glBegin(GL_LINES);
            for (int i = 1; i < result.size(); i++) {
                glVertex2d(getCoordinateX(result.get(i - 1).real()), getCoordinateY(result.get(i - 1).image()));
                glVertex2d(getCoordinateX(result.get(i).real()), getCoordinateY(result.get(i).image()));
                //glVertex2d(getCoordinateX(result.get(i - 1).real() + STEP), getCoordinateY(result.get(i - 1).image() + STEP));
                //glVertex2d(getCoordinateX(result.get(i).real() + STEP), getCoordinateY(result.get(i).image() + STEP));
            }
            glEnd();
        }

        glColor3f(0f, 0f, 0f);
        glBegin(GL_LINES);
        glVertex2d(LENGTH / 2, 0);
        glVertex2d(LENGTH / 2, TOP);
        glEnd();
        glBegin(GL_LINES);
        glVertex2d(0, TOP / 2);
        glVertex2d(LENGTH, TOP / 2);
        glEnd();
        int lineAmount = 100;
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i <= lineAmount; i++) {
            glVertex2d(
                    getCoordinateX((1 * Math.cos(i * 2 * Math.PI / lineAmount))),
                    getCoordinateY((1 * Math.sin(i * 2 * Math.PI / lineAmount)))
            );
        }
        glEnd();
    }

    private static double getCoordinateX(double c) {
        return LENGTH / 2 + c * ((LENGTH / 2) / MAX_X);
    }

    private static double getCoordinateY(double c) {
        return TOP / 2 + c * ((TOP / 2) / 1.14);
    }

    private static ArrayList<Complex> solve(Complex v, double epsilon, int number) {
        ArrayList<Complex> result = new ArrayList<>();
        Complex v1 = v.minus(function.apply(v).div(derivative.apply(v)));
        result.add(v);
        result.add(v1);
        int k = 0;
        while (v.minus(v1).mod() >= epsilon && k < number) {
            v = v1;
            v1 = v.minus(function.apply(v).div(derivative.apply(v)));
            result.add(v1);
            k++;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x0, x1;
        try {
            x0 = scanner.nextDouble();
            x1 = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("USING: [real part] [imaginary part]");
            return;
        }

        start(new Complex(x0, x1));

    }
}
