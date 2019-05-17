public class FactoryNodeFile implements FactoryNode {
    private String fileName;
    private String fileContent;
    private int copyNumber;

    public FactoryNodeFile(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.copyNumber = 0;
    }

    public NodeFile getNew() {
        NodeFile newFolder;

        if (this.copyNumber == 0) {
            newFolder = new NodeFile(this.fileName, this.fileContent, this);
        } else {
            newFolder = new NodeFile(this.fileName + "(copy_" + this.copyNumber + ")", this.fileContent,this);
        }
        this.copyNumber++;

        return newFolder;
    }
}
