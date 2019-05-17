import montefiore.ulg.ac.be.graphics.TextAreaManager;


public class VisitorDisplay implements Visitor{
    private TextAreaManager DisplayManager;
    private Node target;
    private int level;

    public VisitorDisplay(TextAreaManager DisplayManager, Node target){
        this.DisplayManager = DisplayManager;
        this.target = target;
        this.level = 0;
        target.acceptVisitor(this);
    }

    @Override
    public void visitArchiveNode(NodeArchive node) {
        if (this.target instanceof NodeFolder) {
            for (int i = 1; i < this.level; i++) {
                this.DisplayManager.appendText("    ");
            }
            this.DisplayManager.appendText("- " + node.toString() + "\n");
        }
        else {
            this.DisplayManager.appendText(node.getContent() + "\n");
        }
    }

    @Override
    public void visitFileNode(NodeFile node) {
        if(this.target instanceof NodeFolder){
            for (int i = 1; i < this.level; i++) {
                this.DisplayManager.appendText("    ");
            }
            this.DisplayManager.appendText("- " + node.toString() + "\n");
        }
        else{
            this.DisplayManager.appendText(node.getContent());
        }
    }

    @Override
    public void visitFolderNode(NodeFolder node) {
        if (this.level != 0) {
            for (int i = 1; i < this.level; i++) {
                this.DisplayManager.appendText("    ");
            }
            this.DisplayManager.appendText("+ " + node.toString() + "\n");
        }
        this.level++;
    }


    @Override
    public void visitAliasNode(NodeAlias node) {
        if (this.target instanceof NodeFolder) {
            for (int i = 1; i < this.level; i++) {
                this.DisplayManager.appendText("    ");
            }
            this.DisplayManager.appendText("- " + node.toString() + "\n");
        }
        else {
            this.DisplayManager.appendText(node.getContent() + "\n");
        }
    }

    @Override
    public void visitNode(Node node) {
    }

    @Override
    public void exitCurrentFolder() {
        this.level--;
    }
}
