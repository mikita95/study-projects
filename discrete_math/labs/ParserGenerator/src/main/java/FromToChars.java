package main.java;

/**
 * Created by nikita on 08.05.16.
 */
public class FromToChars extends Lexeme {
    public FromToChars(Character from, Character to) {
        super("" + from + ".." + to);
    }
}
