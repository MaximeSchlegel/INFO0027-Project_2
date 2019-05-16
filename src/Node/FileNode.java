package Node;

import Factory.FileFactory;
import Visitor.Visitor;

public class FileNode extends Node {
    private String content;

    public FileNode(String name, String content, FileFactory factory) {
        super(name, factory);
        this.content = content;
    }

    public String getContent() {
        if(this.content.isEmpty())
            return "Content Is Empty";
        else
            return this.content;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitFileNode(this);
    }
}
