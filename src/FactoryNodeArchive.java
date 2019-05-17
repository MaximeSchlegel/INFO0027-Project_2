public class FactoryNodeArchive implements FactoryNode {
    private String archiveName;
    private String archiveExtension;
    private int compressionRate;
    private String archiveContent;
    private int copyNumber;

    public FactoryNodeArchive(String name, String extension, int compressionRate, NodeFolder target) {
        VisitorArchive visitor = new VisitorArchive(target);

        this.archiveName = name;
        this.archiveExtension = extension;
        this.compressionRate = compressionRate;
        this.archiveContent = visitor.getResult();
        this.copyNumber = 0;
    }

    @Override
    public NodeArchive getNew() {
        NodeArchive newArchive;

        if (this.copyNumber == 0) {
            newArchive =new NodeArchive(this.archiveName, this.archiveExtension, this.compressionRate, this.archiveContent, this);
        } else {
            newArchive = new NodeArchive(this.archiveName + "(copy_" + this.copyNumber + ")", this.archiveExtension, this.compressionRate, this.archiveContent, this);
        }
        this.copyNumber++;

        return newArchive;
    }
}
