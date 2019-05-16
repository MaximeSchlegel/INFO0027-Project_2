package Visitor;

import Node.*;
import montefiore.ulg.ac.be.graphics.TextAreaManager;

import java.util.Iterator;

public class VisitorDisplay implements Visitor{
    private TextAreaManager mDisplayAreaManager;

    public VisitorDisplay(TextAreaManager mDisplayAreaManager){
        this.mDisplayAreaManager = mDisplayAreaManager;
    }

    @Override
    public void visitArchiveNode(ArchiveNode node) {
        this.mDisplayAreaManager.clearAllText();
        this.mDisplayAreaManager.appendText(node.getContent() + "\n");

    }

    @Override
    public void visitFileNode(FileNode node) {
        this.mDisplayAreaManager.clearAllText();
        this.mDisplayAreaManager.appendText(node.getContent());
    }

    @Override
    public void visitFolderNode(FolderNode node) {
        this.mDisplayAreaManager.clearAllText();
        iterateAllFoldersTree(node);
    }

    public void iterateAllFoldersTree(Node node){
        Iterator<Node> iter;
        mDisplayAreaManager.appendText(node.toString() + "\n");
        if(node instanceof FolderNode){
            iter = ((FolderNode) node).getChildren().iterator();
            while (iter.hasNext()){
                Node nodeIter = iter.next();
                iterateAllFoldersTree(nodeIter);
            }
        }

    }

    @Override
    public void visitAliasNode(AliasNode node) {
        this.mDisplayAreaManager.clearAllText();
        this.mDisplayAreaManager.appendText(node.getContent());
    }

    @Override
    public void visitNode(Node node) {
        return;
    }

    @Override
    public void exitCurrentFolder() {
        return;
    }
}
