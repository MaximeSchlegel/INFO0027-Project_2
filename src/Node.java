public class Node {
    private String name;
    private FactoryNode factory;
    private NodeFolder parent;

    public Node(String name, FactoryNode factory) {
        this.name = name;
        this.factory = factory;
        this.parent = null;
    }

    public String getName() {
        return this.name;
    }

    public NodeFolder getParent() {
        return this.parent;
    }

    public void setParent(NodeFolder parent) {
        this.parent = parent;
    }

    public FactoryNode getFactory () {
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
