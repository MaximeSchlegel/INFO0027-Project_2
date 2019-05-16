package Factory;

import Node.ArchiveNode;
import Node.FolderNode;
import Visitor.Visitor;
import Visitor.VisitorArchive;
import Visitor.VisitorCopy;

public class ArchiveFactory implements NodeFactory {
    private String archiveName;
    private String archiveExtension;
    private int compressionRate;
    private String archiveContent;
    private int copyNumber;

    public ArchiveFactory(String name, String extension, int compressionRate, FolderNode target) {
        VisitorArchive visitor = new VisitorArchive(target);
//        VisitorArchive visitor = new VisitorArchive(target);

        this.archiveName = name;
        this.archiveExtension = extension;
        this.compressionRate = compressionRate;
        this.archiveContent = visitor.getResult();
        this.copyNumber = 0;
    }

    @Override
    public ArchiveNode getNew() {
        ArchiveNode newArchive;

        if (this.copyNumber == 0) {
            newArchive =new ArchiveNode(this.archiveName, this.archiveExtension, this.compressionRate, this.archiveContent, this);
        } else {
            newArchive = new ArchiveNode(this.archiveName + "(copy_" + this.copyNumber + ")", this.archiveExtension, this.compressionRate, this.archiveContent, this);
        }
        this.copyNumber++;

        return newArchive;
    }
}
