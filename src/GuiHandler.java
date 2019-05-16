import Factory.ArchiveFactory;
import Factory.FileFactory;
import Factory.FolderFactory;
import Node.AliasNode;
import Node.FileNode;
import Node.FolderNode;
import Node.Node;
import Observer.EventSource;
import montefiore.ulg.ac.be.graphics.*;

import java.io.File;

public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	private EventSource eventSource;
	
    GuiHandler(String[] args, EventSource eventSource) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);
		this.eventSource = eventSource;
        try {
            this.esv.setRootNode(new FolderNode("Root", null));
			this.esv.refreshTree();
        } catch (RootAlreadySetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createFileEvent(Object selectedNode) {
		if (!(selectedNode instanceof FolderNode)) {
			esv.showPopupError("Can not add a file to this element");
			return;
		}

		FolderNode parent = (FolderNode) selectedNode;

		String[] fileConfig = esv.fileMenuDialog();
		String fileName = fileConfig[0];
		String fileContent = fileConfig[1];

		if (fileName.isEmpty()) {
			esv.showPopupError("Can not create a file without a name");
			return;
		}

		FileFactory factory = new FileFactory(fileName, fileContent);
		FileNode newFile = factory.getNew();
		newFile.setParent(parent);

		try {
			esv.addNodeToSelectedNode(newFile);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newFile);
		esv.refreshTree();
        eventSource.scanUserIntraction("createFile " + newFile.getName() + " in "+ parent.getName());
	}

	@Override
	public void createFolderEvent(Object selectedNode) {
		if (!(selectedNode instanceof FolderNode)) {
			esv.showPopupError("Can not add a folder to this element");
			return;
		}

		FolderNode parent = (FolderNode) selectedNode;

		String folderName = esv.folderMenuDialog();
		if (folderName.isEmpty()) {
			esv.showPopupError("Can not create a file without a name");
			return;
		}

		FolderFactory factory = new FolderFactory(folderName);
		FolderNode newFolder = factory.getNew();
		newFolder.setParent(parent);

		try {
			esv.addNodeToSelectedNode(newFolder);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newFolder);
		esv.refreshTree();
        eventSource.scanUserIntraction("createFolder " + newFolder.getName() + " in "+ parent.getName());
	}

	@Override
	public void createAliasEvent(Object selectedNode) {
		if (esv.isRootNodeSelected()) {
			esv.showPopupError("Can not create an alias of the root");
			return;
		}
		if(!(selectedNode instanceof FileNode)) {
			esv.showPopupError("Can not create an alias this element");
			return;
		}

		FileNode target = (FileNode) selectedNode;
		FolderNode parent = target.getParent();

		AliasNode newAlias = new AliasNode(target);
		newAlias.setParent(parent);

		try {
			esv.addNodeToParentNode(newAlias);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		} catch (NoParentNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newAlias);
		esv.refreshTree();
        eventSource.scanUserIntraction("createAlias " + newAlias.getName());
	}

	@Override
	public void createArchiveEvent(Object selectedNode) {
		if (esv.isRootNodeSelected()) {
			esv.showPopupError("Can not compress the root");
			return;
		}
		if (!(selectedNode instanceof  FolderNode)) {
			esv.showPopupError("Can not compress this element");
			return;
		}

		String archiveName = esv.displayArchiveWindow1();
		String extension = esv.displayArchiveWindow2();
		int compressionLevel = esv.displayArchiveWindow3();

		System.out.println(archiveName + extension + " : " + compressionLevel);

		if (archiveName == null) {
			esv.showPopupError("Can not create the archive : Invalid name");
			return;
		}
		if (extension == null) {
			esv.showPopupError("Can not create the archive : Invalid extension");
			return;
		}
		if (compressionLevel == -1) {
			esv.showPopupError("Can not create the archive : Invalid compression level");
			return;
		}

		ArchiveFactory factory = new ArchiveFactory()

		//TODO:: add compress node
        Node node = (Node)selectedNode;
        eventSource.scanUserIntraction("createArchive " + node.getName());
	}

	@Override
	public void createCopyEvent(Object selectedNode) {
		if(esv.isRootNodeSelected()) {
			esv.showPopupError("Can not copy the root");
			return;
		}
		if (selectedNode instanceof AliasNode) {
			esv.showPopupError("Can not copy an alias");
			return;
		}

		System.out.println(selectedNode.getClass());

//		if (selectedNode instanceof Node.FileNode) {
//			Node.FileNode mFileNode = (Node.FileNode) selectedNode;
//			Node.FolderNode mParent = mFileNode.getParent();
//			try {
//				Node.FileNode mFileNodeClone = (Node.FileNode) mFileNode.clone();
//				esv.addNodeToParentNode(mFileNodeClone);
//				mParent.addFileNode(mFileNodeClone);
//				esv.refreshTree();
//			} catch (NoSelectedNodeException e) {
//				e.printStackTrace();
//			} catch (NoParentNodeException e) {
//				e.printStackTrace();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
//		}
//
//		if(selectedNode instanceof Node.FolderNode){
//			Node.FolderNode mFolderNode = (Node.FolderNode) selectedNode;
//			try {
//				Node.FolderNode mFolderNodeClone = (Node.FolderNode) mFolderNode.clone();
//				Iterator<Node.FileNode> mFileNodes = mFolderNode.getmFileNodes().iterator();
//				Iterator<Node.FolderNode> mFolderNodes = mFolderNode.getmFolderNodes().iterator();
//				esv.addNodeToParentNode(mFolderNodeClone);
//				while (mFileNodes.hasNext()){
//					esv.addNodeToLastInsertedNode(mFileNodes.next(), 1);
//				}
//				while (mFolderNodes.hasNext()){
//					Node.FolderNode tmpFolder = mFolderNodes.next();
//					esv.addNodeToLastInsertedNode(tmpFolder, 1);
//					System.out.println(tmpFolder.getmFileNodes().size());
//					for (int i = 0; i < tmpFolder.getmFileNodes().size(); i++){
//						Node.FileNode tmpFile = tmpFolder.getmFileNodes().get(i);
//						esv.addNodeToLastInsertedNode(tmpFile, 2);
//					}
//				}
//				esv.refreshTree();
//			} catch (NoSelectedNodeException e) {
//				e.printStackTrace();
//			} catch (NoParentNodeException e) {
//				e.printStackTrace();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			} catch (NoPreviousInsertedNodeException e) {
//				e.printStackTrace();
//			} catch (LevelException e) {
//				e.printStackTrace();
//			}
//		}
        Node node = (Node)selectedNode;
        eventSource.scanUserIntraction("createCopy " + node.getName());
	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
		// TODO Auto-generated method stub
        Node node = (Node)selectedNode;
        eventSource.scanUserIntraction("Display " + node.getName());
	}

	@Override
	public void eventExit() {
		// TODO Auto-generated method stub
        eventSource.scanUserIntraction("eventExit ");
	}
}
