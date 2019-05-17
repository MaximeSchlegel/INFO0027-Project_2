import Factory.ArchiveFactory;
import Factory.*;
import Node.*;
import Observer.EventSource;
import Visitor.*;
import montefiore.ulg.ac.be.graphics.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	private EventSource eventSource;
	private PrintWriter writer = null;

	
    GuiHandler(String[] args) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);
		this.eventSource = new EventSource();
		if(args.length == 0){
			this.eventSource.addObserver(event -> {System.out.println(event);});
		}else{
			try {
				this.writer = new PrintWriter(new File("./log.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			this.eventSource.addObserver(event -> {
				writer.write(event + "\n");
				writer.flush();
			});
		}

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
        eventSource.scanUserInteraction("createFile " + newFile.toString() + " in "+ parent.toString());
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
        eventSource.scanUserInteraction("createFolder " + newFolder.toString() + " in "+ parent.toString());
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
        eventSource.scanUserInteraction("createAlias " + newAlias.toString());
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
        eventSource.scanUserInteraction("createArchive " + node.toString());
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

		Node nodeToBeCopied = (Node)selectedNode;
		FolderNode parent = nodeToBeCopied.getParent();
		VisitorCopy visitorCopy = new VisitorCopy(nodeToBeCopied, this.esv);
		visitorCopy.visitNode((Node)selectedNode);

		Node nodeCopied = visitorCopy.getResult();
		parent.addChild(nodeCopied);

		esv.refreshTree();

		Node node = (Node)selectedNode;
        eventSource.scanUserInteraction("createCopy " + node.toString());
	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
		// TODO Auto-generated method stub
		esv.getTextAreaManager().clearAllText();
		new VisitorDisplay(esv.getTextAreaManager(), selectedNode);
        Node node = (Node)selectedNode;
		eventSource.scanUserInteraction("Display " + node.toString());

//		VisitorDisplay visitorDisplay = new VisitorDisplay(esv.getTextAreaManager(), node);
//        if(selectedNode instanceof FileNode){
//            this.visitorDisplay.visitFileNode((FileNode)selectedNode);
//        }else if(selectedNode instanceof AliasNode){
//			this.visitorDisplay.visitAliasNode((AliasNode)selectedNode);
//		}
//		else if(selectedNode instanceof FolderNode){
//			this.visitorDisplay.visitFolderNode((FolderNode)selectedNode);
//		}
//		else if(selectedNode instanceof ArchiveNode){
//			this.visitorDisplay.visitArchiveNode((ArchiveNode)selectedNode);
//		}


	}

	@Override
	public void eventExit() {
		// TODO Auto-generated method stub
        eventSource.scanUserInteraction("eventExit ");
        if(writer != null)
			writer.close();// close the PrintWriter
	}
}
