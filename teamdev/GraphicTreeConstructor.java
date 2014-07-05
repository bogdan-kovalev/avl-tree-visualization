package teamdev;

public class GraphicTreeConstructor {

	private GNode rootGNode;
	private AVLTreeModel<Integer> model;

	public GraphicTreeConstructor(AVLTreeModel<Integer> avlTreeModel) {
		model = avlTreeModel;
	}
	
	public GNode getRootGNode() {
		return rootGNode;
	}

	public void construct() {
		if (model.root == null) { rootGNode = null; return; }
		start(model.root);
	}

	private void start(Node<Integer> root) {
		rootGNode = createRootGNode(root);
		doConstructionStep(rootGNode, root.getHeight()); // recursively
	}
	
	private GNode createRootGNode(Node<Integer> root) {		
		GNode rootGNode = new GNode(null);
		rootGNode.setParentGNode(rootGNode);
		rootGNode.setData(root);
		rootGNode.height = root.getHeight();
		return rootGNode;
	}

	private void doConstructionStep(GNode parentGNode, int height) {
		if(--height < 1) return;
		
		// for left
		parentGNode.setLeft(new GNode(parentGNode));
		parentGNode.getLeft().height = height;
		if(parentGNode.getNode() != null) {
			Node<Integer> next = parentGNode.getNode().getLeft();
			if (next != null)
				parentGNode.getLeft().setData(next);
		}
		doConstructionStep(parentGNode.getLeft(), height);
		
		//for right
		parentGNode.setRight(new GNode(parentGNode));
		parentGNode.getRight().height = height;
		if(parentGNode.getNode() != null) {
			Node<Integer> next = parentGNode.getNode().getRight();
			if (next != null)
				parentGNode.getRight().setData(next);
		}
		doConstructionStep(parentGNode.getRight(), height);
	}
	
}
