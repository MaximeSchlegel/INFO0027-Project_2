package Visitor;

import Node.*;


public class VistorCopy implements Visitor{
    private Node copiedNode;
    private FolderNode currentFolder;

    public VistorCopy(Node target) {
        this.currentFolder = null;
        target.acceptVisitor(this);
    };

    @Override
    public void visitAliasNode(AliasNode node) {
        return;
    }

    @Override
    public void visitArchiveNode(ArchiveNode node) {
        ArchiveNode copiedArchive = (ArchiveNode) node.getCopy();

        if (this.currentFolder == null) {
            copiedArchive.setParent(node.getParent());
            this.copiedNode = copiedArchive;
        } else {
            copiedArchive.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedArchive);
        }
    }

    @Override
    public void visitFileNode(FileNode node) {
        FileNode copiedFile = (FileNode) node.getCopy();

        if (this.currentFolder == null) {
            copiedNode.setParent(node.getParent());
            this.copiedNode = copiedNode;
        } else {
            copiedFile.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedNode);
        }
    }

    @Override
    public void visitFolderNode(FolderNode node) {
        FolderNode copiedFolder = (FolderNode) node.getCopy();

        if (this.currentFolder == null) {
            copiedFolder.setParent(node.getParent());
            this.copiedNode = copiedFolder;
            this.currentFolder = copiedFolder;
        } else {
            copiedFolder.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedFolder);
            this.currentFolder = copiedFolder;
        }
    }

    @Override
    public void visitNode(Node node) {
        return;
    }

    @Override
    public void exitCurrentFolder() {
        this.currentFolder = this.currentFolder.getParent();
    }

    public Node getResult() {
        return this.copiedNode;
    }
}
