package Visitor;

import Node.*;

public interface Visitor {
    void visitArchiveNode(ArchiveNode node);
    void visitFileNode(FileNode node);
    void visitFolderNode(FolderNode node);
    void visitAliasNode(AliasNode node);
    void visitNode(Node node);
    void exitCurrentFolder();
}
