package Factory;

import Node.Node;
import Node.ArchiveNode;
import Node.FolderNode;


public class ArchiveFactory implements NodeFactory {
    private String fileName;
    private String fileContent;
    private int copyNumber;

    public ArchiveFactory(Node target) {
        return;
    }

    @Override
    public ArchiveNode getNew(FolderNode Parent) {
        return null;
    }
}
