import montefiore.ulg.ac.be.graphics.*;


public class VisitorCopy implements Visitor{
    private Node copiedNode;
    private NodeFolder currentFolder;
    private ExplorerSwingView esv;
    private int level;
    private Node target;

    public VisitorCopy(Node target) {
        this.currentFolder = null;
        this.level = 0;
        this.target = target;
        target.acceptVisitor(this);
    }

    @Override
    public void visitAliasNode(NodeAlias node) {
        return;
    }

    @Override
    public void visitArchiveNode(NodeArchive node) {
        NodeArchive copiedArchive = (NodeArchive) node.getCopy();

        if (this.currentFolder == null) {
            copiedArchive.setParent(node.getParent());
            this.copiedNode = copiedArchive;
        }
        else {
            copiedArchive.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedArchive);
        }
    }

    @Override
    public void visitFileNode(NodeFile node) {
        NodeFile copiedFile = (NodeFile) node.getCopy();

        if (this.currentFolder == null) {
            copiedFile.setParent(node.getParent());
            this.copiedNode = copiedFile;
        }
        else {
            copiedFile.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedFile);
        }
    }


    @Override
    public void visitFolderNode(NodeFolder node) {
        NodeFolder copiedFolder = (NodeFolder) node.getCopy();

        if (this.currentFolder == null) {
            copiedFolder.setParent(node.getParent());
            this.copiedNode = copiedFolder;
            this.currentFolder = copiedFolder;
        }
        else {
            copiedFolder.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedFolder);
            this.currentFolder = copiedFolder;

        }
        this.level++;
    }

    @Override
    public void visitNode(Node node) {
        return;
    }

    @Override
    public void exitCurrentFolder() {
        this.currentFolder = this.currentFolder.getParent();
        this.level--;
    }

    public Node getResult() {
        return this.copiedNode;
    }

    public NodeFolder getCurrentFolder() {
        return currentFolder;
    }
}
