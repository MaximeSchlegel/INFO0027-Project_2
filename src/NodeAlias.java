public class NodeAlias extends Node {
    private NodeFile original;

    public NodeAlias(NodeFile original) {
        super("File Alias", null);
        this.original = original;
    }

    @Override
    public String getName() {
        String name = this.original.getName();
        if (name == null) {
            return "File Alias";
        }
        return name + " (alias)";
    }

    @Override
    public NodeFolder getParent() {
        return super.getParent();
    }

    @Override
    public FactoryNode getFactory() {
        FactoryNode factory = this.original.getFactory();
        return this.original.getFactory();
    }

    public String getContent() {
        return this.original.getContent();
    }

    @Override
    public String toString() {
        String name = this.original.getName();
        if (name == null) {
            return "File Alias";
        }
        return original.getName() + " (alias)";
    }

    @Override
    public Node getCopy() {
        return null;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitAliasNode( this);
    }
}
