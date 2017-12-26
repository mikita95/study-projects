package main.java;

/**
 * Created by nikita on 07.05.16.
 */
public class Lexeme {
    private String string;

    public Lexeme(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public String getString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        return (o.getClass() == Lexeme.class) && ((Lexeme) o).getString().equals(string);
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    public void setString(String string) {
        this.string = string;
    }
}
