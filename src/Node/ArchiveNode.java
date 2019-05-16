package Node;

import Factory.ArchiveFactory;
import Visitor.Visitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArchiveNode extends Node{
    private String name;
    private String format;
    private int compressionRate;
    private String archiveContent;

    public ArchiveNode (String name, String extendion, int compressionRate, String archiveContent, ArchiveFactory factory) {
        super(name, factory);
        this.compressionRate = compressionRate;
        this.compressionRate = compressionRate;
        this.archiveContent = archiveContent;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitArchiveNode(this);
    }
}
