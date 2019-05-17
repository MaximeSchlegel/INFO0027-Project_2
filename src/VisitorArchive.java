public class VisitorArchive implements Visitor {
    private StringBuilder content;
    private int current_indent;

    public VisitorArchive(NodeFolder target) {
        this.content = new StringBuilder();
        this.current_indent = 0;
        target.acceptVisitor(this);
    }

    @Override
    public void visitAliasNode(NodeAlias node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append("- ");
        this.content.append(node.toString());
        this.content.append("\n");
    }

    @Override
    public void visitFileNode(NodeFile node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append("- ");
        this.content.append(node.toString());
        this.content.append("\n");
    }

    @Override
    public void visitArchiveNode(NodeArchive node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append("- ");
        this.content.append(node.toString());
        this.content.append("\n");
    }

    @Override
    public void visitFolderNode(NodeFolder node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append("+ ");
        this.content.append(node.toString());
        this.content.append("\n");
        this.current_indent++;
    }

    @Override
    public void visitNode(Node node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append(node.toString());
        this.content.append("\n");
    }

    @Override
    public void exitCurrentFolder() {
        this.current_indent--;
    }

    public String getResult() {
        return this.content.toString();
    }
}
