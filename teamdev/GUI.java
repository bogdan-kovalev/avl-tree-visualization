package teamdev;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	private Controller cntr;
	private View view;

	public GUI(AVLTreeModel<Integer> model, GraphicTreeConstructor gtc) {
		super("AVLTree visualization");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		saveScreenSize();
		setHalfScreenFrameSize();
		setLocationByPlatform(true);
		setLayout(new GridBagLayout());

		view = new View(gtc);
		view.setBackground(new Color(220,220,220));

		cntr = new Controller(model, view);
		cntr.setBackground(Color.LIGHT_GRAY);
		
		add(view, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		add(cntr, new GridBagConstraints(1, 0, 1, 1, 0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		setDefaultLookAndFeelDecorated(true);
		setVisible(true);
	}

	private static void saveScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_WIDTH = screenSize.width;
		SCREEN_HEIGHT = screenSize.height;
	}

	private void setHalfScreenFrameSize() {
		setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	}
}

@SuppressWarnings("serial")
class Controller extends JPanel {

	private AVLTreeModel<Integer> model;
	private View view;
	private JTextField insertDataField;
	private JTextField deleteDataField;
	private JButton insertButton;
	private JButton clearButton;
	private JButton deleteButton;
	private JCheckBox rndCheck;
	private ArrayList<Integer> inserted = new ArrayList<>();
	private Random rnd = new Random();
	private AtomicInteger at = new AtomicInteger();

	public Controller(AVLTreeModel<Integer> m, View v) {
		
		model = m;
		view = v;
		
		setLayout(new GridBagLayout());
		
		rndCheck = new JCheckBox("Random");

		insertDataField = new JTextField();
		insertDataField.setText(String.valueOf(at.get()));
		insertDataField.setColumns(5);
		insertDataField.setHorizontalAlignment(JTextField.CENTER);
		
		deleteDataField = new JTextField();
		deleteDataField.setText("Empty");
		deleteDataField.setEditable(false);
		deleteDataField.setColumns(5);
		deleteDataField.setHorizontalAlignment(JTextField.CENTER);
		
		rndCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rndCheck.isSelected())
					insertDataField.setText(String.valueOf((int)(rnd.nextDouble()*100)));
				else
					insertDataField.setText(String.valueOf(at.get()));
				
			}
		});

		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insert(insertDataField.getText());
				view.updateTree();
			}
		});
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete(deleteDataField.getText());
				view.updateTree();
			}
		});
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				clear();
				view.updateTree();
			}
		});

		add(rndCheck, new GridBagConstraints(0, 0, 1, 1, 1.0, 0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		add(insertDataField, new GridBagConstraints(0, 1, 1, 1, 1.0, 0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		add(insertButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		add(deleteDataField, new GridBagConstraints(0, 3, 1, 1, 1.0, 0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		add(deleteButton, new GridBagConstraints(0, 4, 1, 1, 1.0, 0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		add(clearButton, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
	}

	private void clear() {
		model.makeEmpty();
		
		inserted.clear();
		at.set(0);
		insertDataField.setText(String.valueOf(at.get()));
		
		deleteDataField.setText("Empty");
		deleteDataField.setEditable(false);
		deleteButton.setEnabled(false);
	}

	private void insert(String data) {
		Integer in;
		try {
			in = Integer.valueOf(data);
		}
		catch (Exception ex) {return;}
		
		model.insert(in);
		inserted.add(in);
		
		if(!deleteDataField.isEditable()) {
			deleteDataField.setEditable(true);
			deleteDataField.setText(insertDataField.getText());
			deleteButton.setEnabled(true);
		}
		
		if(rndCheck.isSelected())
			insertDataField.setText(String.valueOf((int)(rnd.nextDouble()*100)));
		else
			insertDataField.setText(String.valueOf(at.addAndGet(1)));
	}
	
	private void delete(String data) {
		Integer in;
		try {
			in = Integer.valueOf(data);
		}
		catch (Exception ex) {return;}
		
		model.remove(in);
		inserted.remove(in);
		
		if (inserted.isEmpty()) {
			deleteDataField.setText("Empty");
			deleteDataField.setEditable(false);
			deleteButton.setEnabled(false);
		}
		else 
			deleteDataField.setText(String.valueOf(inserted.get((int)(rnd.nextDouble()*(inserted.size()-1)))));
	}

}

@SuppressWarnings("serial")
class View extends JPanel {

	private GraphicTreeConstructor gtc;

	public View(GraphicTreeConstructor gtc) {
		this.gtc = gtc;
		setLayout(new GridBagLayout());
	}

	public void updateTree() {
		removeAll();
		gtc.construct();
		startDrawWithRoot();
		revalidate();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2.f));
		for(Line2D line : Connector.getLines(gtc.getRootGNode())) {
			g2.draw(line);
		}
	}
	
	private void startDrawWithRoot() {
		GNode root = gtc.getRootGNode();
		if(root == null) return;
		int verticalLocationOnGrid = 0;
		root.horizontalLocationOnGrid = 0;
		int gridwidth = root.getGridWidth();
		add(root, new GridBagConstraints(root.horizontalLocationOnGrid, verticalLocationOnGrid, gridwidth, 1, 1.0, 0,
				GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));
		doStep(root, 1); // recursive
	}
	
	private void doStep(GNode parentGNode,int verticalLocationOnGrid) {
		GNode leftChild = parentGNode.getLeft();
		if (leftChild != null) {
			int gridwidth = leftChild.getGridWidth();
			leftChild.horizontalLocationOnGrid = parentGNode.horizontalLocationOnGrid;
			add(leftChild, new GridBagConstraints(leftChild.horizontalLocationOnGrid, verticalLocationOnGrid, gridwidth, 1, 1.0, 0,
					GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(40,0,0,0), 0, 0));
			doStep(leftChild, verticalLocationOnGrid+1);
		}
		GNode rightChild = parentGNode.getRight();
		if (rightChild != null) {
			int gridwidth = rightChild.getGridWidth();
			rightChild.horizontalLocationOnGrid = parentGNode.horizontalLocationOnGrid + gridwidth;
			add(rightChild, new GridBagConstraints(rightChild.horizontalLocationOnGrid, verticalLocationOnGrid, gridwidth, 1, 1.0, 0,
					GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(40,0,0,0), 0, 0));
			doStep(rightChild, verticalLocationOnGrid+1);
		}
	}
	
}