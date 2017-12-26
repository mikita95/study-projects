package test.java;
import static test.java.GoodLanguageLexer.*;
import java.util.*;
import org.antlr.v4.runtime.*;

public class GoodLanguageNode {
    String name;
    public ArrayList<GoodLanguageNode> children = new ArrayList<>();
    public HashMap<String, Object> attrs = new HashMap<>();
    public GoodLanguageNode(String name) {
        this.name = name;
    }
    public void addChild(GoodLanguageNode n) {
        children.add(n);
    }
}

