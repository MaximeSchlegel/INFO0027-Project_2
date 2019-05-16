import Factory.ArchiveFactory;
import Factory.*;
import Node.*;
import Observer.EventSource;
import Visitor.*;
import montefiore.ulg.ac.be.graphics.*;

import java.io.File;


public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	private EventSource eventSource;
	private VisitorDisplay visitorDisplay;
	
    GuiHandler(String[] args, EventSource eventSource) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);
		this.eventSource = eventSource;
		this.visitorDisplay = new VisitorDisplay(esv.getTextAreaManager());
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
        eventSource.scanUserIntraction("createFile " + newFile.toString() + " in "+ parent.toString());
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
        eventSource.scanUserIntraction("createFolder " + newFolder.toString() + " in "+ parent.toString());
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
        eventSource.scanUserIntraction("createAlias " + newAlias.toString());
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
		FolderNode parent = (FolderNode) selectedNode;
		FolderNode parentofparent = parent.getParent();


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
		ArchiveFactory factory = new ArchiveFactory(archiveName, extension, compressionLevel, parentofparent);
		ArchiveNode newArchive = factory.getNew();
		newArchive.setParent(parentofparent);
		try {
			esv.addNodeToParentNode(newArchive);
		} catch (NoParentNodeException e) {
			e.printStackTrace();
			return;
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
		}
		parentofparent.addChild(newArchive);
		esv.refreshTree();
        Node node = (Node)selectedNode;
        eventSource.scanUserIntraction("createArchive " + node.toString());
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

//		System.out.println(selectedNode.getClass());
//		VisitorCopy visitorCopy = new VisitorCopy((Node)selectedNode);
//		visitorCopy.visitFileNode((FileNode)selectedNode);
//		FileNode newFile = (FileNode)visitorCopy.getResult();
////		FolderNode parent = newFile.getParent();
////		FolderNode parentofparent = parent.getParent();
//
//		try {
//			esv.addNodeToSelectedNode(newFile);
//			esv.addNodeToParentNode(visitorCopy.getCurrentFolder());
//		} catch (NoSelectedNodeException e) {
//			e.printStackTrace();
//		} catch (NoParentNodeException e) {
//			e.printStackTrace();
//		}
		Node node = (Node)selectedNode;
        eventSource.scanUserIntraction("createCopy " + node.toString());
	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
		// TODO Auto-generated method stub
        Node node = (Node)selectedNode;
        if(selectedNode instanceof FileNode){
            this.visitorDisplay.visitFileNode((FileNode)selectedNode);
        }else if(selectedNode instanceof AliasNode){
			this.visitorDisplay.visitAliasNode((AliasNode)selectedNode);
		}
		else if(selectedNode instanceof FolderNode){
			this.visitorDisplay.visitFolderNode((FolderNode)selectedNode);
		}
		else if(selectedNode instanceof ArchiveNode){
			this.visitorDisplay.visitArchiveNode((ArchiveNode)selectedNode);
		}
        eventSource.scanUserIntraction("Display " + node.toString());

	}

	@Override
	public void eventExit() {
		// TODO Auto-generated method stub
        eventSource.scanUserIntraction("eventExit ");
	}
}
