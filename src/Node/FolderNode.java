package Node;

import Factory.FolderFactory;
import Factory.NodeFactory;

import java.util.ArrayList;

public class FolderNode extends Node {
    private ArrayList<Node> children;

    public FolderNode(String name, FolderNode parent, FolderFactory factory) {
        super(name, parent, factory);
        this.children = new ArrayList<>();
    }

    public ArrayList<Node> getChild() {
        return this.children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

}
