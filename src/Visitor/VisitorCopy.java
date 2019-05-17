package Visitor;

import Node.*;
import montefiore.ulg.ac.be.graphics.*;


public class VisitorCopy implements Visitor{
    private Node copiedNode;
    private FolderNode currentFolder;
    private ExplorerSwingView esv;
    private int level;
    private Object target;

    public VisitorCopy(Object target, ExplorerSwingView esv) {
        this.currentFolder = null;
        this.esv = esv;
        this.level = 0;
        this.target = target;
        ((Node)target).acceptVisitor(this);
    }

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
            copiedFile.setParent(node.getParent());
            this.copiedNode = copiedFile;
        }
        else {
            copiedFile.setParent(this.currentFolder);
            this.currentFolder.addChild(copiedFile);
        }
        if(this.target instanceof FolderNode){
            try {
                this.esv.addNodeToLastInsertedNode(copiedFile, level);
            } catch (NoPreviousInsertedNodeException e) {
                e.printStackTrace();
            } catch (LevelException e) {
                e.printStackTrace();
            }
        }else{
            try {
                this.esv.addNodeToParentNode(copiedFile);
            } catch (NoSelectedNodeException e) {
                e.printStackTrace();
            } catch (NoParentNodeException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void visitFolderNode(FolderNode node) {

        FolderNode copiedFolder = (FolderNode) node.getCopy();
        if (this.currentFolder == null) {
            copiedFolder.setParent(node.getParent());
            this.currentFolder = copiedFolder;

        }
        else {
            System.out.println(currentFolder);
            copiedFolder.setParent(this.currentFolder);
            this.currentFolder = copiedFolder;
            this.currentFolder.addChild(copiedFolder);

        }
        this.copiedNode = copiedFolder;
        try {
            if(level == 0){
                this.esv.addNodeToParentNode(this.copiedNode);
            }else{
                this.esv.addNodeToLastInsertedNode(this.copiedNode, level);
            }
            this.level++;
        } catch (NoSelectedNodeException e) {
            e.printStackTrace();
        } catch (NoParentNodeException e) {
            e.printStackTrace();
        } catch (NoPreviousInsertedNodeException e) {
            e.printStackTrace();
        } catch (LevelException e) {
            e.printStackTrace();
        }

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

    public FolderNode getCurrentFolder() {
        return currentFolder;
    }
}
