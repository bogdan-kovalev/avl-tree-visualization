package teamdev;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Connector {
	
	public static List<Line2D> lines;
	
	public static List<Line2D> getLines(GNode rootGNode) {
		lines = new ArrayList<>();
		addLine(rootGNode); // recursive
		
		return lines;
	}
	
	private static void addLine(GNode parent) {
		if(parent == null) return;
		GNode child = parent.getLeft();
		if (child != null && !child.isEmpty) {
			Point p1 = parent.getBottomConnectionPoint(); 
			Point p2 = child.getTopConnectionPoint();
			lines.add(new Line2D.Double(p1, p2));
			addLine(child);
		}
		child = parent.getRight();
		if (child != null && !child.isEmpty) {
			Point p1 = parent.getBottomConnectionPoint(); 
			Point p2 = child.getTopConnectionPoint();
			lines.add(new Line2D.Double(p1, p2));
			addLine(child);
		}
	}

}
