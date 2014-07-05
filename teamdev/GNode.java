package teamdev;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GNode extends JComponent {

	public static Dimension DEFAULT_SIZE = new Dimension(20, 20);
	public int height;
	public boolean isEmpty = true;
	public int horizontalLocationOnGrid;

	private Node<Integer> node;
	private GNode parentGNode;
	private GNode left;
	private GNode right;
	private String shapeType = "Rectagle2D";
	

	public void setShape(String shapeType) {
		this.shapeType = shapeType;
	}

	public GNode(GNode parentGNode) {
		setPreferredSize(DEFAULT_SIZE);
		this.setParentGNode(parentGNode);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(isEmpty) return;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3.f));

		RectangularShape rShape = getShape();
		
		switch (getNode().getState()) {
			case NEW : 
				g2.setPaint(new Color(0,150,0));
				break;
			case OLD : 
				g2.setPaint(new Color(70,70,70));
				break;
			case CHANGED : 
				g2.setPaint(new Color(200, 0, 0));
				break;
			default : break;
		}
		
		g2.fill(rShape);
		g2.setPaint(new Color(255, 210, 0));
		drawTextOnCetreOf_rShape(g2, rShape, String.valueOf(getNode().getData()));
	}

	public Point getTopConnectionPoint() {
		return new Point(getLocation().x + getWidth() / 2, getLocation().y);
	}

	public Point getBottomConnectionPoint() {
		return new Point(getLocation().x + getWidth() / 2, getLocation().y
				+ getHeight() - 1);
	}
	
	public boolean isLeftChild(GNode child) {
		if (child == getLeft()) return true;
		return false;
	}
	
	public int getGridWidth() {		
		return (int) Math.pow(2, height-1);
	}

	public void setData(Node<Integer> node) {
		this.setNode(node);
		isEmpty = false;
		setVisible(true);
	}

	public GNode getRight() {
		return right;
	}

	public void setRight(GNode right) {
		this.right = right;
	}

	public GNode getLeft() {
		return left;
	}

	public void setLeft(GNode left) {
		this.left = left;
	}

	public GNode getParentGNode() {
		return parentGNode;
	}

	public void setParentGNode(GNode parentGNode) {
		this.parentGNode = parentGNode;
	}

	public Node<Integer> getNode() {
		return node;
	}

	public void setNode(Node<Integer> node) {
		this.node = node;
	}

	private RectangularShape getShape() {
		RectangularShape rShape = null;
		switch (shapeType) {
		case "Rectangle2D":
			rShape = new Rectangle2D.Double(0, 0, getWidth() - 1,
					getHeight() - 1);
			break;
		case "RoundRectagle2D":
			rShape = new RoundRectangle2D.Double(0, 0, getWidth() - 1,
					getHeight() - 1, 4, 4);
			break;
		default:
			rShape = new Rectangle2D.Double(0, 0, getWidth() - 1,
					getHeight() - 1);
			break;
		}

		return rShape;
	}
	
	private void drawTextOnCetreOf_rShape(Graphics2D g2,
			RectangularShape rShape, String text) {
		Font sansbold14 = new Font("SansSerif", Font.BOLD, 10);
		g2.setFont(sansbold14);

		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = sansbold14.getStringBounds(text, context);

		double x = rShape.getCenterX() - bounds.getWidth() / 2, y = rShape
				.getCenterY() - bounds.getHeight() / 2, ascent = -bounds.getY(), baseY = y
				+ ascent;

		g2.drawString(text, (int) x, (int) baseY);
	}
}
