public interface Visitor {
    void visitArchiveNode(NodeArchive node);
    void visitFileNode(NodeFile node);
    void visitFolderNode(NodeFolder node);
    void visitAliasNode(NodeAlias node);
    void visitNode(Node node);
    void exitCurrentFolder();
}
