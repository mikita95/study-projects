package list;

import list.fatcopy.FatCopyPersistentList;
import list.slow.SlowPersistentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class PersistentListPerformanceTest {
    private double addP, editP, getP;
    private int maxOperations;
    private int step;

    public PersistentListPerformanceTest(double addP, double editP, double getP, int maxOperations, int step){
        this.addP = addP;
        this.editP = editP;
        this.getP = getP;
        this.maxOperations = maxOperations;
        this.step = step;
    }

    @Parameters
    public static Collection<Object[]> data() {
        // add, edit, get, operations
        Object[][] data = new Object[][]{
                {1, 0.0, 0.0, 12000, 500},
                //{0.2, 0.5, 0.3, 25000, 500},
                //{0.3, 0.2, 0.5, 25000, 500},
        };

        return Arrays.asList(data);
    }

    @Test
    public void performanceTest() {
        double[] fatCopyTime = estimate(new FatCopyPersistentList<>());
        double[] slowTime = estimate(new SlowPersistentList<>());
        XYChart chart = new XYChartBuilder().width(800).height(600).
                title(String.format("add = %f, replace = %f, get = %f, operations = %d", addP, editP, getP, maxOperations)).
                xAxisTitle("Operations, number").
                yAxisTitle("Time, ms").
                build();
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setChartTitleFont(new Font("TimesRoman", Font.BOLD, 8));
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setMarkerSize(5);

        XYSeries series1 = chart.addSeries("Slow implementation",
                IntStream.range(0, slowTime.length).map(n -> n * step).mapToDouble(i -> (double) i).toArray(),
                slowTime);
        series1.setMarker(SeriesMarkers.NONE);
        series1.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        XYSeries series2 = chart.addSeries("FatCopy implementation",
                IntStream.range(0, fatCopyTime.length).map(n -> n * step).mapToDouble(i -> (double) i).toArray(),
                fatCopyTime);
        series2.setMarker(SeriesMarkers.NONE);
        series2.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        try {
            BitmapEncoder.saveBitmap(chart, String.format("%f_%f_%f_perf.png", addP, editP, getP), BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.err.println("Unable to save " + e.getMessage());
        }
    }

    private double[] estimate(PersistentListInterface<Integer> persistentList) {
        Random random = new Random();

        double[] result = new double[maxOperations / step];
        int t = 0;
        for (int i = 0; i < maxOperations; i += step) {
            long time = System.currentTimeMillis();
            for (int c = 0; c < 3; c++) {
                persistentList.clear();
                int size = 0;
                for (int k = 0; k < i; k++) {
                    double p = random.nextDouble();
                    if (p <= addP || size == 0) {
                        persistentList.add(size, random.nextInt());
                        size++;
                    } else if (p > addP && p <= addP + editP) {
                        persistentList.replace(random.nextInt(size), random.nextInt());
                    } else {
                        persistentList.getListHead(random.nextInt(persistentList.getListVersion()));
                    }
                }
            }
            time = System.currentTimeMillis() - time;
            result[t] = (double) time / 3;
            t++;
            System.out.println(i + "/" + maxOperations);
        }
        return result;
    }
}
