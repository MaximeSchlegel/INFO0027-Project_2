public class FileNode extends Node {
    private String content;
    private FolderNode parent;
    public FileNode(String name, FolderNode parent) {
        super(name);
        this.parent = parent;
    }

    public FolderNode getParent() {
        return parent;
    }
}
