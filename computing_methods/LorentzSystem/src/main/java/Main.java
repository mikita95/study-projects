import javafx.util.Pair;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nikita on 12.09.16.
 */
public class Main {
    private static double sigma, r, b, step, maxT;
    private static int method;
    private static Pair<Double, Double> x0, y0, z0;
    private static ArrayList<ArrayList<Double>> answer;
    private static Solver solver;
    private static String usage = "USAGE: [sigma] [r] [b] [x(0)] [y(0)] [z(0)] [step] [maxT] [method]\nwhere method:\n\t0 = euler method\n\t1 = modified euler method\n\t2 = runge-kutta method\n\t3 = adams method\n";

    private static void readData(String[] args) throws Exception {
        try {
            sigma = Double.parseDouble(args[0]);
            r = Double.parseDouble(args[1]);
            b = Double.parseDouble(args[2]);
            x0 = new Pair<>(0d, Double.parseDouble(args[3]));
            y0 = new Pair<>(0d, Double.parseDouble(args[4]));
            z0 = new Pair<>(0d, Double.parseDouble(args[5]));
            step = Double.parseDouble(args[6]);
            maxT = Double.parseDouble(args[7]);
            method = Integer.parseInt(args[8]);
            DifferentialSystem lorenz = new DifferentialSystem(new ArrayList<>(Arrays.asList(
                    (t, ys) -> -sigma * ys.get(0) + sigma * ys.get(1),
                    (t, ys) -> -ys.get(0) * ys.get(2) + r * ys.get(0) - ys.get(1),
                    (t, ys) -> ys.get(0) * ys.get(1) - b * ys.get(2)
            )));

            solver = getSolver(method, lorenz);
            ArrayList<Pair<Double, Double>> init = new ArrayList<>(Arrays.asList(x0, y0, z0));
            answer = solver.solve(step, init, maxT);
        } catch (Exception e) {
            System.out.print(usage);
        }
    }

    public static void main(String[] args) throws Exception {
            readData(args);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);

    }

    private static void createAndShowGUI() {
        Main demo = new Main();
        JFrame frame1 = new JFrame("Lorenz system");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame frame2 = new JFrame("x(t)");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame frame3 = new JFrame("y(t)");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame frame4 = new JFrame("z(t)");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Plot3DPanel plot = new Plot3DPanel();
        double[] x = new double[answer.get(0).size()];
        double[] y = new double[answer.get(1).size()];
        double[] z = new double[answer.get(2).size()];
        for (int i = 0; i < answer.get(0).size(); i++) {
            x[i] = answer.get(0).get(i);
            y[i] = answer.get(1).get(i);
            z[i] = answer.get(2).get(i);
        }

        plot.addLinePlot("Lorenz system", Color.BLUE, x, y, z);

        Plot2DPanel plot2DX = new Plot2DPanel();
        for (int i = 0; i < answer.get(0).size(); i++) {
            x[i] = i * step;
            y[i] = answer.get(0).get(i);
        }

        plot2DX.addScatterPlot("x(t)", Color.BLUE, x, y);
        for (int i = 0; i < answer.get(0).size(); i++) {
            y[i] = answer.get(1).get(i);
            z[i] = answer.get(2).get(i);
        }
        Plot2DPanel plot2DY = new Plot2DPanel();
        plot2DY.addScatterPlot("y(t)", Color.BLUE, x, y);
        Plot2DPanel plot2DZ = new Plot2DPanel();
        plot2DZ.addScatterPlot("z(t)", Color.BLUE, x, z);


        frame1.add(plot);
        frame2.add(plot2DX);
        frame3.add(plot2DY);
        frame4.add(plot2DZ);

        frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame1.setVisible(true);

        frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame2.setVisible(true);

        frame3.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame3.setVisible(true);

        frame4.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame4.setVisible(true);
    }

    private static Solver getSolver(int method, DifferentialSystem system) {
        switch (method) {
            case 0:
                return new EulerSolver(system);
            case 1:
                return new ModifiedEulerSolver(system);
            case 2:
                return new RungeKuttaSolver(system);
            case 3:
                return new AdamsSolver(system);
        }
        return null;
    }

}
