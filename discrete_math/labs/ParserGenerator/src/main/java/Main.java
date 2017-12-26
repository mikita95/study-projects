package main.java;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by nikita on 08.05.16.
 */
public class Main {
    public static void main(String[] args) {
        String file = args[0];
        try {
            InputStream is = new FileInputStream(file);
            new Generator(is, args[1]).generate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
