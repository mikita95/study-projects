package main.java;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by nikita on 07.05.16.
 */
public class TranslatingGrammar {
    private String name;
    private Lexeme start;
    private Set<Lexeme> nonTerminals;
    private Map<Lexeme, ArrayList<Attribute>> attributes;
    private Set<Lexeme> terminals;
    private Map<Lexeme, ArrayList<Rule>> rules;
    private String members, header;

    public TranslatingGrammar(String name, Lexeme start, Set<Lexeme> nonTerminals, Map<Lexeme, ArrayList<Attribute>> attributes, Set<Lexeme> terminals, Map<Lexeme, ArrayList<Rule>> rules, String members, String header) {
        this.name = name;
        this.start = start;
        this.nonTerminals = nonTerminals;
        this.attributes = attributes;
        this.terminals = terminals;
        this.rules = rules;
        this.members = members;
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lexeme getStart() {
        return start;
    }

    public void setStart(Lexeme start) {
        this.start = start;
    }

    public Set<Lexeme> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(Set<Lexeme> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public Map<Lexeme, ArrayList<Attribute>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Lexeme, ArrayList<Attribute>> attributes) {
        this.attributes = attributes;
    }

    public Set<Lexeme> getTerminals() {
        return terminals;
    }

    public void setTerminals(Set<Lexeme> terminals) {
        this.terminals = terminals;
    }

    public Map<Lexeme, ArrayList<Rule>> getRules() {
        return rules;
    }

    public void setRules(Map<Lexeme, ArrayList<Rule>> rules) {
        this.rules = rules;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
