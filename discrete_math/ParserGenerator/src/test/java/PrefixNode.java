package test.java;
import static test.java.PrefixLexer.*;
import java.util.*;
import org.antlr.v4.runtime.*;

public class PrefixNode {
    String name;
    public ArrayList<PrefixNode> children = new ArrayList<>();
    public HashMap<String, Object> attrs = new HashMap<>();
    public PrefixNode(String name) {
        this.name = name;
    }
    public void addChild(PrefixNode n) {
        children.add(n);
    }
}

