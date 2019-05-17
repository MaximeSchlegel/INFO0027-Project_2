import java.util.ArrayList;

public class NodeFolder extends Node {
    private ArrayList<Node> children;

    public NodeFolder(String name, FactoryNodeFolder factory) {
        super(name, factory);
        this.children = new ArrayList<>();
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitFolderNode(this);
        for (Node child: this.children) {
            child.acceptVisitor(visitor);
        }
        visitor.exitCurrentFolder();
    }
}
