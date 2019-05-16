package Node;

import Factory.ArchiveFactory;
import Visitor.Visitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArchiveNode extends Node{
    private String extension;
    private int compressionRate;
    private String archiveContent;

    public ArchiveNode (String name, String extension, int compressionRate, String archiveContent, ArchiveFactory factory) {
        super(name, factory);
        this.extension = extension;
        this.compressionRate = compressionRate;
        this.archiveContent = archiveContent;
    }

    public String getContent() {
        return archiveContent;
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
