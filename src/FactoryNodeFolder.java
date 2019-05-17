public class FactoryNodeFolder implements FactoryNode {
    private String folderName;
    private int copyNumber;

    public FactoryNodeFolder(String folderName) {
        this.folderName = folderName;
        this.copyNumber = 0;
    }

    public NodeFolder getNew() {
        NodeFolder newFolder;
        if (this.copyNumber == 0) {
            newFolder = new NodeFolder(this.folderName, this);
        } else {
            newFolder = new NodeFolder(this.folderName + "(copy_" + this.copyNumber + ")", this);
        }

        this.copyNumber++;

        return newFolder;
    }
}
