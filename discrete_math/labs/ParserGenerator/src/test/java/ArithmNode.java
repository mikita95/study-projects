package test.java;
import static test.java.ArithmLexer.*;
import java.util.*;
import org.antlr.v4.runtime.*;

public class ArithmNode {
    String name;
    public ArrayList<ArithmNode> children = new ArrayList<>();
    public HashMap<String, Object> attrs = new HashMap<>();
    public ArithmNode(String name) {
        this.name = name;
    }
    public void addChild(ArithmNode n) {
        children.add(n);
    }
}

