package Factory;

import Node.FolderNode;


public class FolderFactory implements NodeFactory {
    private String folderName;
    private int copyNumber;

    public FolderFactory(String folderName) {
        this.folderName = folderName;
        this.copyNumber = 0;
    }

    public FolderNode getNew() {
        FolderNode newFolder;
        if (this.copyNumber == 0) {
            newFolder = new FolderNode(this.folderName, this);
        } else {
            newFolder = new FolderNode(this.folderName + "(copy_" + this.copyNumber + ")", this);
        }

        this.copyNumber++;

        return newFolder;
    }
}
