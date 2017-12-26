import org.StructureGraphic.v1.DSutils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class ParserTest {
    @Test
    public void test1() throws Exception {
        String test = "1 2 10 + *";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test1.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test2() throws Exception {
        String test = "666";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test2.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test3() throws Exception {
        String test = "7 13 -";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test3.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test4() throws Exception {
        String test = "2 9 + 4 *";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test4.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test5() throws Exception {
        String test = "4 2 - 6 0 * +";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test5.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test6() throws Exception {
        String test = "1 2 * 3 4 5 - * * 6 +";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test6.png");
        ImageIO.write(bi, "png", outputfile);
    }

    @Test
    public void test7() throws Exception {
        String test = "4 9 + *";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test8() throws Exception {
        String test = "-";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test9() throws Exception {
        String test = "11 22 33 +";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test10() throws Exception {
        String test = "7 3 - 5";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test11() throws Exception {
        String test = "1 2 -*";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test12() throws Exception {
        String test = "bad + test";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        try {
            Tree tree = (new Parser()).parse(stream);
            // Assert.assertTrue(false);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
    }

    @Test
    public void test13() throws Exception {
        String test = "1 ? 2 * ? 3 4 ? 5 - * * 6 +";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        Tree tree = (new Parser()).parse(stream);
        BufferedImage bi = DSutils.fromDS(tree, 60, 40);
        File outputfile = new File("test7.png");
        ImageIO.write(bi, "png", outputfile);
    }


}