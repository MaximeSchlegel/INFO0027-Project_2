package Node;

import Factory.FileFactory;

public class FileNode extends Node {
    private String content;

    public FileNode(String name, String content, FolderNode parent, FileFactory factory) {
        super(name, parent, factory);
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
