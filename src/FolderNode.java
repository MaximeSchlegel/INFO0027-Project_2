import java.util.ArrayList;

public class FolderNode extends Node {
    private ArrayList<FileNode> mFileNodes;
    private ArrayList<FolderNode> mFolderNodes;
    private FolderNode mParent;
    public FolderNode(String name, FolderNode mParent) {
        super(name);
        mFileNodes = new ArrayList<FileNode>();
        mFolderNodes = new ArrayList<FolderNode>();
        this.mParent = mParent;
    }
    public void addFileNode(FileNode mFileNode){
        mFileNodes.add(mFileNode);
    }

    public void addFolderNode(FolderNode mFolderNode){
        mFolderNodes.add(mFolderNode);
    }

    public ArrayList<FileNode> getmFileNodes() {
        return mFileNodes;
    }

    public ArrayList<FolderNode> getmFolderNodes() {
        return mFolderNodes;
    }
}
