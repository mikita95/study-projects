import org.StructureGraphic.v1.DSTreeNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree implements DSTreeNode {
    public String node;
    public List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        if (children == null) this.children = new ArrayList<>();
        else
            this.children = Arrays.asList(children);
    }

    @Override
    public DSTreeNode[] DSgetChildren() {
        return children.toArray(new DSTreeNode[children.size()]);
    }

    @Override
    public Color DSgetColor() {
        return Color.black;
    }

    @Override
    public Object DSgetValue() {
        return node;
    }
}
