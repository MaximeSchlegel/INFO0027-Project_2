import montefiore.ulg.ac.be.graphics.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	private EventSource eventSource;
	private PrintWriter writer = null;


    GuiHandler(String[] args) throws NullHandlerException {
    	//create the observer used to print the log
        this.esv = new ExplorerSwingView(this);
		this.eventSource = new EventSource();

		if(args.length == 0){
			this.eventSource.addObserver(
					event -> {
						System.out.println(event);
					});
		}
		else {
			try {
				this.writer = new PrintWriter(new File("./log.txt"));
				this.eventSource.addObserver(
						event -> {
							writer.write(event + "\n");
							writer.flush();
						});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
		}

		//set up the root node
        try {
            this.esv.setRootNode(new NodeFolder("Root", null));
        } catch (RootAlreadySetException e) {
			e.printStackTrace();
			return;
		}
		this.esv.refreshTree();
	}

	@Override
	public void createFileEvent(Object selectedNode) {
		if (!(selectedNode instanceof NodeFolder)) {
			esv.showPopupError("Can not add a file to this element");
			return;
		}

		NodeFolder parent = (NodeFolder) selectedNode;

		//Get name and content from the dialog
		String[] fileConfig = esv.fileMenuDialog();
		String fileName = fileConfig[0];
		String fileContent = fileConfig[1];

		if (fileName.isEmpty()) {
			esv.showPopupError("Can not create a file without a name");
			return;
		}

		//create the file factory and then the file
		FactoryNodeFile factory = new FactoryNodeFile(fileName, fileContent);
		NodeFile newFile = factory.getNew();
		newFile.setParent(parent);

		//add the file to tree
		try {
			esv.addNodeToSelectedNode(newFile);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newFile);
		esv.refreshTree();

		//notify the observer
        eventSource.scanUserInteraction("createFile " + newFile.toString() + " in "+ parent.toString());
	}

	@Override
	public void createFolderEvent(Object selectedNode) {
		if (!(selectedNode instanceof NodeFolder)) {
			esv.showPopupError("Can not add a folder to this element");
			return;
		}

		NodeFolder parent = (NodeFolder) selectedNode;

		//Get name and content from the dialog
		String folderName = esv.folderMenuDialog();
		if (folderName.isEmpty()) {
			esv.showPopupError("Can not create a file without a name");
			return;
		}

		//cretae the node factory and the node
		FactoryNodeFolder factory = new FactoryNodeFolder(folderName);
		NodeFolder newFolder = factory.getNew();
		newFolder.setParent(parent);

		//add the node in the tree
		try {
			esv.addNodeToSelectedNode(newFolder);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newFolder);
		esv.refreshTree();

		//notify the event observer
        eventSource.scanUserInteraction("createFolder " + newFolder.toString() + " in "+ parent.toString());
	}

	@Override
	public void createAliasEvent(Object selectedNode) {
		if (esv.isRootNodeSelected()) {
			esv.showPopupError("Can not create an alias of the root");
			return;
		}
		if(!(selectedNode instanceof NodeFile)) {
			esv.showPopupError("Can not create an alias this element");
			return;
		}

		NodeFile target = (NodeFile) selectedNode;
		NodeFolder parent = target.getParent();

		NodeAlias newAlias = new NodeAlias(target);
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
		if (!(selectedNode instanceof NodeFolder)) {
			esv.showPopupError("Can not compress this element");
			return;
		}

		//Get name and content from the dialog
		String archiveName = esv.displayArchiveWindow1();
		String extension = esv.displayArchiveWindow2();
		int compressionLevel = esv.displayArchiveWindow3();

		NodeFolder target = (NodeFolder) selectedNode;
		NodeFolder parent = target.getParent();

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

		FactoryNodeArchive factory = new FactoryNodeArchive(archiveName, extension, compressionLevel, target);
		NodeArchive newArchive = factory.getNew();
		newArchive.setParent(parent);

		try {
			esv.addNodeToParentNode(newArchive);
		} catch (NoParentNodeException e) {
			e.printStackTrace();
			return;
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(newArchive);
		esv.refreshTree();
        eventSource.scanUserInteraction("createArchive " + newArchive.toString());
	}

	@Override
	public void createCopyEvent(Object selectedNode) {
		if(esv.isRootNodeSelected()) {
			esv.showPopupError("Can not copy the root");
			return;
		}
		if (selectedNode instanceof NodeAlias) {
			esv.showPopupError("Can not copy an alias");
			return;
		}

		Node nodeToBeCopied = (Node)selectedNode;
		NodeFolder parent = nodeToBeCopied.getParent();

		VisitorCopy visitorCopy = new VisitorCopy(nodeToBeCopied);

		Node nodeCopied = visitorCopy.getResult();


		try {
			this.esv.addNodeToParentNode(nodeCopied);
			if (nodeCopied instanceof NodeFolder) {
				this.addFolderChild((NodeFolder) nodeCopied, 1);
			}
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		} catch (NoParentNodeException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		parent.addChild(nodeCopied);
		esv.refreshTree();

        eventSource.scanUserInteraction("createCopy " + nodeCopied.toString());
	}

	@Override
	public void doubleClickEvent(Object selectedNode) {
		// TODO Auto-generated method stub
		esv.getTextAreaManager().clearAllText();
		new VisitorDisplay(esv.getTextAreaManager(), (Node) selectedNode);
        Node node = (Node)selectedNode;
		eventSource.scanUserInteraction("Display " + node.toString());
	}

	@Override
	public void eventExit() {
        eventSource.scanUserInteraction("eventExit ");
        if(writer != null)
			writer.close();// close the PrintWriter
	}

	private void addFolderChild(NodeFolder folder, int level) throws Exception {
    	//add the child of the folder in the esv hierarchie
		for(Node child: folder.getChildren()) {
			this.esv.addNodeToLastInsertedNode(child, level);
			if (child instanceof NodeFolder) {
				this.addFolderChild((NodeFolder) child, level + 1);
			}
		}
	}
}
