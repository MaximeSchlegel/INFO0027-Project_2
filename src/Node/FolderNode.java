package Node;

import Factory.FolderFactory;
import Factory.NodeFactory;
import Visitor.Visitor;

import java.util.ArrayList;

public class FolderNode extends Node {
    private ArrayList<Node> children;

    public FolderNode(String name, FolderFactory factory) {
        super(name, factory);
        this.children = new ArrayList<>();
    }

    public ArrayList<Node> getChild() {
        return this.children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitFolderNode(this);
    }
}
