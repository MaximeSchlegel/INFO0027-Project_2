package Factory;

import Node.ArchiveNode;
import Node.FolderNode;
import Visitor.VisitorArchive;

public class ArchiveFactory implements NodeFactory {
    private String archiveName;
    private String archiveExtension;
    private int compressoinRate;
    private String archiveContent;
    private int copyNumber;

    public ArchiveFactory(String name, String extension, int compressoinRate, FolderNode target) {
        VisitorArchive visitor = new VisitorArchive(target);

        this.archiveName = name;
        this.archiveExtension = extension;
        this.compressoinRate = compressoinRate;
        this.archiveContent = visitor.getResult();
        this.copyNumber = 0;

        System.out.println(this.archiveName + this.archiveExtension);
        System.out.println(this.archiveContent);
    }

    @Override
    public ArchiveNode getNew() {
        ArchiveNode newArchive;

        if (this.copyNumber == 0) {
            newArchive =new ArchiveNode(this.archiveName, this.archiveExtension, this.compressoinRate, this.archiveContent, this);
        } else {
            newArchive = new ArchiveNode(this.archiveName + "(copy_" + this.copyNumber + ")", this.archiveExtension, this.compressoinRate, this.archiveContent, this);
        }
        this.copyNumber++;

        return newArchive;
    }
}
