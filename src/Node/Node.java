package Node;

import Factory.NodeFactory;

public class Node {
    private String name;
    private FolderNode parent;
    private NodeFactory factory;

    public Node(String name, FolderNode parent, NodeFactory factory) {
        this.name = name;
        this.parent = parent;
        this.factory = factory;
    }

    public String getName() {
        return this.name;
    }

    public FolderNode getParent() {
        return this.parent;
    }

    public NodeFactory getFactory () {
        return this.factory;
    }

    public String toString() {
        return this.name;
    }

    public Node getCopy() {
        return this.factory.getNew(null);
    }
}
