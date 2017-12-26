package main.java;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nikita on 07.05.16.
 */
public class Rule {
    private Lexeme left;
    private List<Lexeme> right;
    private String code;


    public Rule(Lexeme left, List<Lexeme> right, String code) {
        this.left = left;
        this.right = right;
        this.code = code;
    }

    public Boolean isEpsilonRule() {
        return right.isEmpty();
    }

    @Override
    public String toString() {
        return left + " -> " + right.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public Lexeme getLeft() {
        return left;
    }

    public void setLeft(Lexeme left) {
        this.left = left;
    }

    public List<Lexeme> getRight() {
        return right;
    }

    public void setRight(List<Lexeme> right) {
        this.right = right;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
