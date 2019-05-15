import montefiore.ulg.ac.be.graphics.*;

import java.util.Iterator;

public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	
    GuiHandler(String[] args) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);
        
        try {
        	// First step to do before anything !!! /
            this.esv.setRootNode(new FolderNode(args[0], null)); // set the root node with a silly "A" object
			this.esv.refreshTree();
        } catch (RootAlreadySetException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createAliasEvent(Object selectedNode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void createArchiveEvent(Object selectedNode) {
		// TODO Auto-generated method stub
		if(selectedNode instanceof FolderNode){
			esv.displayArchiveWindow1();
			esv.displayArchiveWindow2();
			esv.displayArchiveWindow3();
		}else{
			esv.showPopupError("the element selected isn't a folder");
		}
	}

	@Override
	public void createCopyEvent(Object selectedNode) {
    	if(!esv.isRootNodeSelected()){
    		System.out.println(selectedNode.getClass());
    		if(selectedNode instanceof FileNode){
    			FileNode mFileNode = (FileNode) selectedNode;
    			FolderNode mParent = mFileNode.getParent();
				try {
					FileNode mFileNodeClone = (FileNode) mFileNode.clone();
					esv.addNodeToParentNode(mFileNodeClone);
					mParent.addFileNode(mFileNodeClone);
					esv.refreshTree();
				} catch (NoSelectedNodeException e) {
					e.printStackTrace();
				} catch (NoParentNodeException e) {
					e.printStackTrace();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}else if(selectedNode instanceof FolderNode){
				FolderNode mFolderNode = (FolderNode) selectedNode;
				try {
					FolderNode mFolderNodeClone = (FolderNode) mFolderNode.clone();
					Iterator<FileNode> mFileNodes = mFolderNode.getmFileNodes().iterator();
					Iterator<FolderNode> mFolderNodes = mFolderNode.getmFolderNodes().iterator();
					esv.addNodeToParentNode(mFolderNodeClone);
					while (mFileNodes.hasNext()){
						esv.addNodeToLastInsertedNode(mFileNodes.next(), 1);
					}
					while (mFolderNodes.hasNext()){
						FolderNode tmpFolder = mFolderNodes.next();
						esv.addNodeToLastInsertedNode(tmpFolder, 1);
						System.out.println(tmpFolder.getmFileNodes().size());
						for (int i = 0; i < tmpFolder.getmFileNodes().size(); i++){
							FileNode tmpFile = tmpFolder.getmFileNodes().get(i);
							esv.addNodeToLastInsertedNode(tmpFile, 2);
						}
					}
					esv.refreshTree();
				} catch (NoSelectedNodeException e) {
					e.printStackTrace();
				} catch (NoParentNodeException e) {
					e.printStackTrace();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				} catch (NoPreviousInsertedNodeException e) {
					e.printStackTrace();
				} catch (LevelException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void createFileEvent(Object selectedNode) {
			if(selectedNode instanceof FolderNode || esv.isRootNodeSelected()){
				String[] file = esv.fileMenuDialog();
				FolderNode parent = (FolderNode) selectedNode;
				FileNode mFileNode = new FileNode(file[0], parent);
				FolderNode mFolderNode = (FolderNode)selectedNode;
				try {
					mFolderNode.addFileNode(mFileNode);
					esv.addNodeToSelectedNode(mFileNode);
					esv.refreshTree();
				} catch (NoSelectedNodeException e) {
					e.printStackTrace();
				}
				esv.refreshTree();
			}else{
				esv.showPopupError("You can't add file to file");
			}
	}

	@Override
	public void createFolderEvent(Object selectedNode) {

		if(selectedNode instanceof FolderNode || esv.isRootNodeSelected()){
			String fileName = esv.folderMenuDialog();
			FolderNode mParent = (FolderNode)selectedNode;
			FolderNode mFolderNode = new FolderNode(fileName, mParent);
			FolderNode mFolderNodeSelected = (FolderNode)selectedNode;
			try {
				mFolderNodeSelected.addFolderNode(mFolderNode);
				esv.addNodeToSelectedNode(mFolderNode);
			} catch (NoSelectedNodeException e) {
				e.printStackTrace();
			}
			esv.refreshTree();
		}else{
			esv.showPopupError("You can't add folder to file");
		}


	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eventExit() {
		// TODO Auto-generated method stub
	}
}
