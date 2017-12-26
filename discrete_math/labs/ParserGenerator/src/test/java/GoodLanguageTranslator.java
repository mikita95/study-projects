package test.java;
import org.antlr.v4.runtime.Token;

import static test.java.GoodLanguageLexer.*;

public class GoodLanguageTranslator {
     
    GoodLanguageLexer lexer;
    Token curToken;
    public GoodLanguageTranslator(GoodLanguageLexer lexer) {
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
    public GoodLanguageNode lvalueblock() {
        GoodLanguageNode result = new GoodLanguageNode("lvalueblock");
        switch (curToken.getType()) {
            case NAME: {
                result.children.add(lvalue());
                result.children.add(morelv());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode morerv() {
        GoodLanguageNode result = new GoodLanguageNode("morerv");
        switch (curToken.getType()) {
            case COMMA: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.COMMA));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(rvalue());
                result.children.add(morerv());
                break;
            }
            case RD: {
                break;
            }
            case SEMICOLON: {
                break;
            }
            case VAR: {
                break;
            }
            case Token.EOF: {
                break;
            }
            case WR: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode rvalue() {
        GoodLanguageNode result = new GoodLanguageNode("rvalue");
        switch (curToken.getType()) {
            case NUMBER: {
                result.children.add(atom());
                result.children.add(contatom());
                break;
            }
            case LPAREN: {
                result.children.add(atom());
                result.children.add(contatom());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode program() {
        GoodLanguageNode result = new GoodLanguageNode("program");
        switch (curToken.getType()) {
            case RD: {
                result.children.add(statement());
                result.children.add(program());
                break;
            }
            case VAR: {
                result.children.add(statement());
                result.children.add(program());
                break;
            }
            case WR: {
                result.children.add(statement());
                result.children.add(program());
                break;
            }
            case NAME: {
                result.children.add(statement());
                result.children.add(program());
                break;
            }
            case Token.EOF: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode morelv() {
        GoodLanguageNode result = new GoodLanguageNode("morelv");
        switch (curToken.getType()) {
            case COMMA: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.COMMA));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(lvalue());
                result.children.add(morelv());
                break;
            }
            case RD: {
                break;
            }
            case SEMICOLON: {
                break;
            }
            case VAR: {
                break;
            }
            case Token.EOF: {
                break;
            }
            case WR: {
                break;
            }
            case ASSIGN: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode writable() {
        GoodLanguageNode result = new GoodLanguageNode("writable");
        switch (curToken.getType()) {
            case NUMBER: {
                result.children.add(rvalue());
                break;
            }
            case LPAREN: {
                result.children.add(rvalue());
                break;
            }
            case DAPO: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.DAPO));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                GoodLanguageNode child1 = new GoodLanguageNode(consume(GoodLanguageLexer.LINE));
                child1.attrs.put("text", child1.name);
                result.addChild(child1);
                GoodLanguageNode child2 = new GoodLanguageNode(consume(GoodLanguageLexer.DAPO));
                child2.attrs.put("text", child2.name);
                result.addChild(child2);
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode statement() {
        GoodLanguageNode result = new GoodLanguageNode("statement");
        switch (curToken.getType()) {
            case NAME: {
                result.children.add(assignment());
                result.children.add(sem());
                break;
            }
            case RD: {
                result.children.add(read());
                result.children.add(sem());
                break;
            }
            case WR: {
                result.children.add(write());
                result.children.add(sem());
                break;
            }
            case VAR: {
                result.children.add(variables());
                result.children.add(sem());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode sem() {
        GoodLanguageNode result = new GoodLanguageNode("sem");
        switch (curToken.getType()) {
            case SEMICOLON: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.SEMICOLON));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(sem());
                break;
            }
            case RD: {
                break;
            }
            case VAR: {
                break;
            }
            case Token.EOF: {
                break;
            }
            case WR: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode write() {
        GoodLanguageNode result = new GoodLanguageNode("write");
        switch (curToken.getType()) {
            case WR: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.WR));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(writable());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode variables() {
        GoodLanguageNode result = new GoodLanguageNode("variables");
        switch (curToken.getType()) {
            case VAR: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.VAR));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                GoodLanguageNode child1 = new GoodLanguageNode(consume(GoodLanguageLexer.SEP));
                child1.attrs.put("text", child1.name);
                result.addChild(child1);
                result.children.add(assignment());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode read() {
        GoodLanguageNode result = new GoodLanguageNode("read");
        switch (curToken.getType()) {
            case RD: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.RD));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(lvalueblock());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode assignment() {
        GoodLanguageNode result = new GoodLanguageNode("assignment");
        switch (curToken.getType()) {
            case NAME: {
                result.children.add(lvalueblock());
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.ASSIGN));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(rvalueblock());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode rvalueblock() {
        GoodLanguageNode result = new GoodLanguageNode("rvalueblock");
        switch (curToken.getType()) {
            case NUMBER: {
                result.children.add(rvalue());
                result.children.add(morerv());
                break;
            }
            case LPAREN: {
                result.children.add(rvalue());
                result.children.add(morerv());
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode contatom() {
        GoodLanguageNode result = new GoodLanguageNode("contatom");
        switch (curToken.getType()) {
            case MULT: {
                result.children.add(o());
                result.children.add(atom());
                break;
            }
            case PLUS: {
                result.children.add(o());
                result.children.add(atom());
                break;
            }
            case MINUS: {
                result.children.add(o());
                result.children.add(atom());
                break;
            }
            case COMMA: {
                break;
            }
            case RD: {
                break;
            }
            case SEMICOLON: {
                break;
            }
            case VAR: {
                break;
            }
            case Token.EOF: {
                break;
            }
            case WR: {
                break;
            }
            case RPAREN: {
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode o() {
        GoodLanguageNode result = new GoodLanguageNode("o");
        switch (curToken.getType()) {
            case PLUS: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.PLUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                break;
            }
            case MINUS: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.MINUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                break;
            }
            case MULT: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.MULT));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode lvalue() {
        GoodLanguageNode result = new GoodLanguageNode("lvalue");
        switch (curToken.getType()) {
            case NAME: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.NAME));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
    public GoodLanguageNode atom() {
        GoodLanguageNode result = new GoodLanguageNode("atom");
        switch (curToken.getType()) {
            case NUMBER: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.NUMBER));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                break;
            }
            case LPAREN: {
                GoodLanguageNode child0 = new GoodLanguageNode(consume(GoodLanguageLexer.LPAREN));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(rvalue());
                GoodLanguageNode child1 = new GoodLanguageNode(consume(GoodLanguageLexer.RPAREN));
                child1.attrs.put("text", child1.name);
                result.addChild(child1);
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        return result;
    }
}

