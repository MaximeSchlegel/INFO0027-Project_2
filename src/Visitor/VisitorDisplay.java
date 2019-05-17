package Visitor;

import Node.*;
import montefiore.ulg.ac.be.graphics.TextAreaManager;


public class VisitorDisplay implements Visitor{
    private TextAreaManager mDisplayAreaManager;
    private Object target;
    private int current_indent;

    public VisitorDisplay(TextAreaManager mDisplayAreaManager, Object target){
        this.mDisplayAreaManager = mDisplayAreaManager;
        this.target = target;
        this.current_indent = 0;
        ((Node)this.target).acceptVisitor(this);
    }

    @Override
    public void visitArchiveNode(ArchiveNode node) {
        this.mDisplayAreaManager.appendText(node.getContent() + "\n");

    }

    @Override
    public void visitFileNode(FileNode node) {
        if(this.target instanceof FolderNode){
            for (int i = 0; i < this.current_indent; i++) {
                this.mDisplayAreaManager.appendText("  ");
            }
            this.mDisplayAreaManager.appendText("-" + node.getName() + "\n");
        }
        else{
            this.mDisplayAreaManager.appendText(node.getContent());
        }
    }

    @Override
    public void visitFolderNode(FolderNode node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.mDisplayAreaManager.appendText("  ");
        }
        this.mDisplayAreaManager.appendText("+" + node.getName() + "\n");
        this.current_indent++;
    }


    @Override
    public void visitAliasNode(AliasNode node) {
        this.mDisplayAreaManager.appendText(node.getContent());
    }

    @Override
    public void visitNode(Node node) {
        return;
    }

    @Override
    public void exitCurrentFolder() {
        this.current_indent--;
    }
}
