package Factory;

import Node.Node;
import Node.ArchiveNode;
import Node.FolderNode;
import Visitor.Visitor;

import java.util.ArrayList;


public class ArchiveFactory implements NodeFactory {
    private String archiveName;
    private ArrayList<String> archiveContent;
    private int compressoinRate;
    private int copyNumber;

    public ArchiveFactory(Node target) {
        return;
    }

    @Override
    public ArchiveNode getNew() {
        return null;
    }
}
