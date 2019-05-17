package Factory;

import Node.FolderNode;
import Node.FileNode;
import Visitor.VisitorCopy;


public class FileFactory implements NodeFactory {
    private String fileName;
    private String fileContent;
    private int copyNumber;

    public FileFactory(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.copyNumber = 0;
    }

    public FileNode getNew() {
        FileNode newFolder;

        if (this.copyNumber == 0) {
            newFolder = new FileNode(this.fileName, this.fileContent, this);
        } else {
            newFolder = new FileNode(this.fileName + "(copy_" + this.copyNumber + ")", this.fileContent,this);
        }
        this.copyNumber++;

        return newFolder;
    }
}
