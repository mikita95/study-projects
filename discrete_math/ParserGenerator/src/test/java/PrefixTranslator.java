package test.java;
import org.antlr.v4.runtime.Token;

import java.util.Stack;

import static test.java.PrefixLexer.*;

public class PrefixTranslator {
     Stack<String> stack = new Stack<String>();String result = "";
    PrefixLexer lexer;
    Token curToken;
    public PrefixTranslator(PrefixLexer lexer) {
        this.lexer = lexer;
        curToken = nextToken();
    }
    Token nextToken() {
        curToken = lexer.nextToken();
        return curToken;
    }
    String consume(Integer type) {
        if (curToken.getType() != type) throw new RuntimeException();
        String result = curToken.getText();
        nextToken();
        return result;
    }
    public PrefixNode eprime() {
        PrefixNode result = new PrefixNode("eprime");
        switch (curToken.getType()) {
            case N: {
                result.children.add(c());
                break;
            }
            case MULT: {
                break;
            }
            case Token.EOF: {
                break;
            }
            case PLUS: {
                break;
            }
            case MINUS: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public PrefixNode c() {
        PrefixNode result = new PrefixNode("c");
        switch (curToken.getType()) {
            case N: {
                result.children.add(e());
                result.children.add(o());
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.SEP));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(eprime());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public PrefixNode e() {
        PrefixNode result = new PrefixNode("e");
        switch (curToken.getType()) {
            case N: {
                result.children.add(num());
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.SEP));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(eprime());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public PrefixNode num() {
        PrefixNode result = new PrefixNode("num");
        switch (curToken.getType()) {
            case N: {
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.N));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                stack.push((String)(result.children.get(0).attrs.get("text")));
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public PrefixNode start() {
        PrefixNode result = new PrefixNode("start");
        String v = null;
        switch (curToken.getType()) {
            case N: {
                result.children.add(e());
                v = stack.pop();
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public PrefixNode o() {
        PrefixNode result = new PrefixNode("o");
        switch (curToken.getType()) {
            case PLUS: {
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.PLUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                String temp = stack.pop();stack.push('(' + stack.pop() + '+' + temp + ')');
                break;
            }
            case MULT: {
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.MULT));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                String temp = stack.pop();stack.push('(' + stack.pop() + '*' + temp + ')');
                break;
            }
            case MINUS: {
                PrefixNode child0 = new PrefixNode(consume(PrefixLexer.MINUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                String temp = stack.pop();stack.push('(' + stack.pop() + '-' + temp + ')');
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
}

