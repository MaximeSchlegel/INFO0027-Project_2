package Visitor;

import Node.*;

public class VisitorArchive implements Visitor {
    private StringBuilder content;
    private int current_indent;

    public VisitorArchive(FolderNode target) {
        this.content = new StringBuilder();
        this.current_indent = 0;
        target.acceptVisitor(this);
    }

    @Override
    public void visitAliasNode(AliasNode node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("  ");
        }
        this.content.append("- ");
        this.content.append(node.getName());
        this.content.append("\n");
    }

    @Override
    public void visitFileNode(FileNode node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("   ");
        }
        this.content.append("- ");
        this.content.append(node.getName());
        this.content.append("\n");
    }

    @Override
    public void visitArchiveNode(ArchiveNode node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("  ");
        }
        this.content.append("- ");
        this.content.append(node.getName());
        this.content.append("\n");
    }

    @Override
    public void visitFolderNode(FolderNode node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("  ");
        }
        this.content.append("+ ");
        this.content.append(node.getName());
        this.content.append("\n");
        this.current_indent++;
    }

    @Override
    public void visitNode(Node node) {
        for (int i = 0; i < this.current_indent; i++) {
            this.content.append("  ");
        }
        this.content.append(node.getName());
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
