package test.java;
import org.antlr.v4.runtime.Token;

import static test.java.ArithmLexer.*;

public class ArithmTranslator {
    
    ArithmLexer lexer;
    Token curToken;
    public ArithmTranslator(ArithmLexer lexer) {
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
    public ArithmNode a() {
        ArithmNode result = new ArithmNode("a");
        Integer v = null;
        switch (curToken.getType()) {
            case N: {
                result.children.add(f());
                result.children.add(ap());
                v = (Integer)(result.children.get(0).attrs.get("v")) * (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            case LBRACE: {
                result.children.add(f());
                result.children.add(ap());
                v = (Integer)(result.children.get(0).attrs.get("v")) * (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode e() {
        ArithmNode result = new ArithmNode("e");
        Integer v = null;
        switch (curToken.getType()) {
            case N: {
                result.children.add(t());
                result.children.add(ep());
                v = (Integer)(result.children.get(0).attrs.get("v")) + (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            case LBRACE: {
                result.children.add(t());
                result.children.add(ep());
                v = (Integer)(result.children.get(0).attrs.get("v")) + (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode f() {
        ArithmNode result = new ArithmNode("f");
        Integer v = null;
        switch (curToken.getType()) {
            case N: {
                ArithmNode child0 = new ArithmNode(consume(ArithmLexer.N));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                v = Integer.parseInt((String)(result.children.get(0).attrs.get("text")));
                break;
            }
            case LBRACE: {
                ArithmNode child0 = new ArithmNode(consume(ArithmLexer.LBRACE));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(e());
                ArithmNode child1 = new ArithmNode(consume(ArithmLexer.RBRACE));
                child1.attrs.put("text", child1.name);
                result.addChild(child1);
                v = (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode ep() {
        ArithmNode result = new ArithmNode("ep");
        Integer v = null;
        switch (curToken.getType()) {
            case PLUS: {
                ArithmNode child0 = new ArithmNode(consume(ArithmLexer.PLUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(t());
                result.children.add(ep());
                v = (Integer)(result.children.get(1).attrs.get("v")) + (Integer)(result.children.get(2).attrs.get("v"));
                break;
            }
            case RBRACE: {
                v = 0;
                break;
            }
            case Token.EOF: {
                v = 0;
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode ap() {
        ArithmNode result = new ArithmNode("ap");
        Integer v = null;
        switch (curToken.getType()) {
            case MULT: {
                ArithmNode child0 = new ArithmNode(consume(ArithmLexer.MULT));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(f());
                result.children.add(ap());
                v = (Integer)(result.children.get(1).attrs.get("v")) * (Integer)(result.children.get(2).attrs.get("v"));
                break;
            }
            case RBRACE: {
                v = 1;
                break;
            }
            case Token.EOF: {
                v = 1;
                break;
            }
            case MINUS: {
                v = 1;
                break;
            }
            case PLUS: {
                v = 1;
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode t() {
        ArithmNode result = new ArithmNode("t");
        Integer v = null;
        switch (curToken.getType()) {
            case N: {
                result.children.add(a());
                result.children.add(tp());
                v = (Integer)(result.children.get(0).attrs.get("v")) - (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            case LBRACE: {
                result.children.add(a());
                result.children.add(tp());
                v = (Integer)(result.children.get(0).attrs.get("v")) - (Integer)(result.children.get(1).attrs.get("v"));
                break;
            }
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
    public ArithmNode tp() {
        ArithmNode result = new ArithmNode("tp");
        Integer v = null;
        switch (curToken.getType()) {
            case MINUS: {
                ArithmNode child0 = new ArithmNode(consume(ArithmLexer.MINUS));
                child0.attrs.put("text", child0.name);
                result.addChild(child0);
                result.children.add(a());
                result.children.add(tp());
                v = (Integer)(result.children.get(1).attrs.get("v")) - (Integer)(result.children.get(2).attrs.get("v"));
                break;
            }
            case RBRACE: {
                v = 0;
                break;
            }
            case Token.EOF: {
                v = 0;
                break;
            }
            case PLUS: {
                v = 0;
                break;
            }
            
            default: throw new RuntimeException("Unexpected token");
        }
        result.attrs.put("v", v);
        return result;
    }
}

