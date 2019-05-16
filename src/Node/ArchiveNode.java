package Node;

import Factory.ArchiveFactory;

public class ArchiveNode extends Node{
    private int compressionRate;
    private Node target;

    public ArchiveNode (String name, FolderNode parent, Node target, int compressionRate, ArchiveFactory factory) {
        super(name, parent, factory);
        this.target = target;
        this.compressionRate = compressionRate;
    }


}
