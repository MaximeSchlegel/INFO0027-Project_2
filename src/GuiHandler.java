import Factory.*;
import Node.*;
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

		System.out.println("Hello  " + archiveName + extension + compressionLevel);

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

		FolderNode target = (FolderNode) selectedNode;
		FolderNode parent = target.getParent();

		ArchiveFactory factory = new ArchiveFactory(archiveName, extension, compressionLevel, target);
		ArchiveNode newArchive = factory.getNew();
		newArchive.setParent(parent);

		try {
			esv.addNodeToParentNode(newArchive);
		} catch (NoSelectedNodeException e) {
			e.printStackTrace();
			return;
		} catch (NoParentNodeException e) {
			e.printStackTrace();
			return;
		}

		parent.addChild(newArchive);
		esv.refreshTree();
        eventSource.scanUserIntraction("createArchive " + newArchive.toString());
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
