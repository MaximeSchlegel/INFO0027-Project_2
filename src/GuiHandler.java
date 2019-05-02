import montefiore.ulg.ac.be.graphics.*;

public class GuiHandler implements ExplorerEventsHandler {

	private ExplorerSwingView esv;
	
    GuiHandler(String[] args) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);
        
        try {
        	// First step to do before anything !!! 
            this.esv.setRootNode(new A(args[0])); // set the root node with a silly "A" object
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
	}

	@Override
	public void createCopyEvent(Object selectedNode) {
		this.esv.addNodeToParentNode();
	}

	@Override
	public void createFileEvent(Object selectedNode) {
		/*
        if(selectedNode instanceof A) {
            A a = (A) selectedNode;
            System.out.println("It's A: " + a.getName());
        }else {
            B b = (B) selectedNode;
            System.out.println("It's B: " + b.getName());
        }*/
	}

	@Override
	public void createFolderEvent(Object selectedNode) {
		// TODO Auto-generated method stub
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
