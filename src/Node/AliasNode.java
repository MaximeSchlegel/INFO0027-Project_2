package Node;

import Factory.NodeFactory;


public class AliasNode extends Node {
    private FileNode original;

    public AliasNode(FileNode original) {
        super("File Alias", original.getParent(), null);
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
    public FolderNode getParent() {
        return super.getParent();
    }

    @Override
    public NodeFactory getFactory() {
        NodeFactory factory = this.original.getFactory();
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
}
