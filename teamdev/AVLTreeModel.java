package teamdev;

public class AVLTreeModel<T extends Comparable<T>> {
	
	public Node<T> root;

    /**
     * Construct the tree.
     */
    public AVLTreeModel( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( T x )
    {
    	makeAllOld(root);
        root = insert( x, root );
    }

	/**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public T findMin( )
    {
        return elementAt( findMin( root ) );
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public T findMax( )
    {
        return elementAt( findMax( root ) );
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public T find( T x )
    {
        return elementAt( find( x, root ) );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }
    
    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( T x )
    {
    	makeAllOld(root);
        root = remove(x, root);
    }
    
    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private Node<T> remove( T x, Node<T> t) {
    	if(t == null) return null;
    	if( x.compareTo( t.getData() ) < 0 )
            t.setLeft(remove( x, t.getLeft()));
        else if ( x.compareTo( t.getData() ) > 0 )
            t.setRight(remove( x, t.getRight()));
        else { // x == t.getData()
        	Node<T> q = t.getLeft();
        	Node<T> r = t.getRight();
        	t = null;
        	if(r == null) {
        		return q;
        	}
        	Node<T> min = findMin(r);
        	min.setState(State.CHANGED);
        	min.setRight(removeMin(r));
        	min.setLeft(q);
        	return balance(min);
        }
    	return balance(t);
    }

    /**
     * remove min element from a subtree
     * @param p
     * @return
     */
    private Node<T> removeMin(Node<T> p) {
		if(p.getLeft() == null) return p.getRight();
		p.setLeft(removeMin(p.getLeft()));
		return balance(p);
	}

    /**
     * Change states of nodes in subtree to State.OLD
     * @param node
     */
    private static void makeAllOld(Node<?> node) {
		if(node == null) return;
		node.setState(State.OLD);
		
		makeAllOld(node.getLeft());
		makeAllOld(node.getRight());
	}
    
    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private Node<T> insert( T x, Node<T> t ) {
        if( t == null )
            return new Node<T>( x );
        
        if( x.compareTo( t.getData() ) < 0 )
            t.setLeft(insert( x, t.getLeft()));
        else
            t.setRight(insert( x, t.getRight()));
   
        return balance(t);
    }
    
    /**
     * @param node
     * @return balance factor of node
     */
    private static int balanceFactor(Node<?> node) {
    	return height( node.getRight() ) - height( node.getLeft() );
    }
    
    /**
     * calculates the height of node by
     * left and right children
     * @param node
     */
    private static void fixHeight(Node<?> node) {
    	node.setHeight(max( height( node.getLeft() ), height( node.getRight() ) ) + 1);
    }
    
    /**
     * balance the subtree
     * @param node root for subtree that need to be balanced
     * @return root for balanced subtree
     */
    private Node<T> balance(Node<T> node) {
    	fixHeight(node);
    	if(balanceFactor(node) == 2)
    	{
    		if(balanceFactor(node.getRight()) < 0)
    			node.setRight(rotateWithLeftChild(node.getRight()));
	    	return rotateWithRightChild(node);
    	}
    	if(balanceFactor(node) == -2)
    	{
	    	if(balanceFactor(node.getLeft()) > 0)
	    		node.setLeft(rotateWithRightChild(node.getLeft()));	    	
    		return rotateWithLeftChild(node);
    	}	
    	return node;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private Node<T> findMin( Node<T> t )
    {
        if( t == null )
            return t;

        while( t.getLeft() != null )
            t = t.getLeft();
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private Node<T> findMax( Node<T> t )
    {
        if( t == null )
            return t;

        while( t.getRight() != null )
            t = t.getRight();
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private Node<T> find( T x, Node<T> t )
    {
        while( t != null )
            if( x.compareTo( t.getData() ) < 0 )
                t = t.getLeft();
            else if( x.compareTo( t.getData() ) > 0 )
                t = t.getRight();
            else
                return t;    // Match

        return null;   // No match
    }
    
    /**
     * Internal method to get element field.
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private T elementAt( Node<T> t )
    {
        return t == null ? null : t.getData();
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private static void printTree( Node<?> t )
    {
        if( t != null )
        {
            printTree( t.getLeft() );
            System.out.println( t );
            printTree( t.getRight() );
        }
    }

    /**
     * Return the height of node t, or 0, if null.
     */
    private static int height( Node<?> t )
    {
        return t == null ? 0 : t.getHeight();
    }

    /**
     * Return maximum of lhs and rhs.
     */
    private static int max( int lhs, int rhs )
    {
        return lhs > rhs ? lhs : rhs;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for caseComparable 1.
     * Update heights, then return new root.
     */
    private Node<T> rotateWithLeftChild( Node<T> k2 )
    {
        Node<T> k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        fixHeight(k2);
        fixHeight(k1);
        stateChanged(k1);
        stateChanged(k2);
        return k1;
    }
    
    private static void stateChanged(Node<?> node) {
    	if (node.getState() == State.NEW) return;
    	node.setState(State.CHANGED);
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private Node<T> rotateWithRightChild( Node<T> k1 )
    {
        Node<T> k2 = k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        fixHeight(k1);
        fixHeight(k2);
        stateChanged(k1);
        stateChanged(k2);
        return k2;
    }
}
