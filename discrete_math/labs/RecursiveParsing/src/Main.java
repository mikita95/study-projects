import org.StructureGraphic.v1.DSutils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        try {
            String test = "4 9 + ?";
            InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
            Tree tree = (new Parser()).parse(stream);
            DSutils.show(tree, 60, 40);
           // BufferedImage bi = DSutils.fromDS(tree, 60, 40);
           // File outputfile = new File("saved.png");
           // ImageIO.write(bi, "png", outputfile);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}