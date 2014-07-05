package teamdev;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

	private T data;
	private Node<T> left;
	private Node<T> right;
	private int height = 1;
	private State state;

	public Node(T data) {
		this(data, null, null);
	}

	public Node(T data, Node<T> left, Node<T> right) {
		super();
		setState(State.NEW);
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> node) {
		this.right = node;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int compareTo(Node<T> o) {
		return this.data.compareTo(o.data);
	}

	@Override
	public String toString() {
		return "height: " + height + ", data: " + data;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
