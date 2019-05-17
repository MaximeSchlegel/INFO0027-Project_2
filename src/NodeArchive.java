public class NodeArchive extends Node{
    private String extension;
    private int compressionRate;
    private String archiveContent;

    public NodeArchive(String name, String extension, int compressionRate, String archiveContent, FactoryNodeArchive factory) {
        super(name, factory);
        this.extension = extension;
        this.compressionRate = compressionRate;
        this.archiveContent = archiveContent;
    }

    public String getContent() {
        return this.archiveContent;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitArchiveNode(this);
    }

    @Override
    public String toString() {
        return super.getName() + this.extension;
    }
}
