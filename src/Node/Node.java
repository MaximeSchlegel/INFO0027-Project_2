package Node;

import Factory.NodeFactory;
import Visitor.Visitor;

public class Node {
    private String name;
    private NodeFactory factory;
    private FolderNode parent;

    public Node(String name, NodeFactory factory) {
        this.name = name;
        this.factory = factory;
        this.parent = null;
    }

    public String getName() {
        return this.name;
    }

    public FolderNode getParent() {
        return this.parent;
    }

    public void setParent(FolderNode parent) {
        this.parent = parent;
    }

    public NodeFactory getFactory () {
        return this.factory;
    }

    public String toString() {
        return this.name;
    }

    public Node getCopy() {
        return this.factory.getNew();
    }

    public void acceptVisitor(Visitor visitor){
        visitor.visitNode(this);
    }
}
