package teamdev;


public class Main {

	private static GraphicTreeConstructor gtc;
	private static AVLTreeModel<Integer> model;

	public static void main(String[] args) {

		model = new AVLTreeModel<>();

		gtc = new GraphicTreeConstructor(model);

		new GUI(model, gtc);

	}

}
