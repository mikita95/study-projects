import data.Data;
import data.DataInstance;
import data.Feature;
import data.Label;
import net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import params.Activation;
import params.Params;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by nikita on 03.12.16.
 */
public class Main {

    private static final String TRAIN_IMAGES = "train-images.idx3-ubyte";
    private static final String TRAIN_LABELS = "train-labels.idx1-ubyte";
    private static final String TEST_IMAGES = "t10k-images.idx3-ubyte";
    private static final String TEST_LABELS = "t10k-labels.idx1-ubyte";

    private static Draw draw;

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static Net net;
    private static int nowLabel;
    private static Params params;

    private static final double RATE = 0.07d;
    private static final double REG = 0d;
    private static final double PERCENTAGE = 100;

    private static final Params BEST_PAPAMS = new Params(7E-2, 0, 1, 0);

    private static Data testData;

    public static void main(String[] args) {

        logger.debug("Hello");

        try {
            // File trainFeatures = Paths.get(Main.class.getResource(TRAIN_IMAGES).toURI()).toFile();
            // File trainLabels = Paths.get(Main.class.getResource(TRAIN_LABELS).toURI()).toFile();
            // Data trainData = Utils.readData(trainFeatures, trainLabels);
            File testFeatures = Paths.get(Main.class.getResource(TEST_IMAGES).toURI()).toFile();
            File testLabels = Paths.get(Main.class.getResource(TEST_LABELS).toURI()).toFile();

            testData = Utils.readData(testFeatures, testLabels);

            goTest(".");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Just an interface to get pictures from your perfect fingers.
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(() -> {
            System.out.println("Usage:\n" +
                    "draw \t\t\t-- creates window to draw in\n" +
                    "get <label> \t-- gets picture you drawn + real label for it\n" +
                    "learn <label> \t-- learn on the drawn picture with specified label"
            );
            while (true) {
                try {
                    String line = reader.readLine();

                    switch (line) {
                        case "exit": System.exit(0);
                        case "draw": goDraw(); break;
                        case "save": goSave(); break;
                        case "test": goTest("temp"); break;
                    }
                    String[] slices = line.trim().split(" +");
                    switch (slices[0]) {
                        case "get":
                            nowLabel = Integer.parseInt(slices[1]);
                            getValues();
                            break;
                        case "learn":
                            nowLabel = Integer.parseInt(slices[1]);
                            learn();
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void goSave() {
        for (int w = 0; w < net.weights.length; w++) {
            net.weights[w].writeFile("./temp/w" + w + ".txt");
        }
    }

    public static void goTest(String dir) {
        net = new Net(new int[]{28 * 28, 60, 60, 60, 10}, Activation.SIGMOID);

        net.initWeights(new File[]{
                new File(dir, "w0.txt"),
                new File(dir, "w1.txt"),
                new File(dir, "w2.txt"),
                new File(dir, "w3.txt")});

        //params = net.learn(trainData, testData);
//            Main.logger.info("Best params: {}", params.toString());

        int failed;
        cleanFailed();
        failed = 0;
        for (int i = 0; i < testData.size(); i++) {
            Feature f = testData.get(i).feature;
            Label result = net.classify(f);
            if (!result.equals(testData.get(i).label)) {
                failed++;
                File file = new File("./failed/" + i + "_" + result.value + "_" + testData.get(i).label + "_image.png");
                try {
                    ImageIO.write(testData.get(i).feature.toImage(), "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Main.logger.info("Accuracy: {}%", (1d - (double) failed / testData.size()) * PERCENTAGE);
    }

    public static void learn() {
        Feature feature = draw.getFeature();
        BufferedImage image = feature.toImage();
        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Label l = net.classify(feature);
        Label must = new Label(nowLabel);
        while (!l.equals(must)) {
            net.learnStep(new DataInstance(feature, new Label(nowLabel)), BEST_PAPAMS);
            l = net.classify(feature);
        }
        System.out.println("[OK]");
    }

    public static void goDraw() {
        draw = new Draw();
        draw.showMe();
    }

    public static void getValues() {
        draw.setVisible(false);
        Feature feature = draw.getFeature();
        BufferedImage image = feature.toImage();
        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Label l = net.classify(feature);
        if (l.value == nowLabel) {
            System.out.println("[CORRECT]");
        } else {
            System.out.println("[WRONG] Got label = " + l.value);
        }
    }

    public static void cleanFailed() {
        File dir = new File("./failed");
        for (File file : dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}
