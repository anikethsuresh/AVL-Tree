package AddressBook3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// This class implements a binary search tree of key/value pairs of strings
public class Table {
	// Root node in the tree
	private Node root = null;
	// Holds the number of Nodes in the tree
	private int numberOfNodes = 0;
	
	/**
	* Displays all nodes in the table.
	* @return The number of nodes in the table.
	*/
	public int display() {
		numberOfNodes = 0;
		return displayTable(root);
	}
	/**
	* Displays all nodes in a (sub)tree using in-order traversal
	* (left, parent, right)
	* @param current The root node of the tree to display
	* @return The number of nodes in the tree
	*/
	private int displayTable(Node current) {
		if(current==null) return 0;
		// LEFT
		displayTable(current.getLeftChild());
		// PARENT
		System.out.println("Name : " + current.getKey() + "\nAddress: " + current.getValue());
		System.out.println("Height : " + current.getHeight() + "\nBalance Factor: " + current.getBalance() + "\n");
		numberOfNodes +=1;
		// RIGHT
		displayTable(current.getRightChild());
		return numberOfNodes;
	}
	
	/**
	* Inserts a new Node into the table. If the provided key already
	* exists, no entry will be created. Otherwise, the new entry is
	* added to the end of the list.
	* @param key
	* @param value
	* @return True if the new node was inserted successfully.
	* False if the provided key already exists in the table.
	*/
	public boolean insert(String key,String value) {
		// Check if key eists
		if(lookUp(key)==null) {
			// Key doesn't exist - Insert the key,value pair
			Node newNode = new Node(key,value);
			root = insertTable(root,newNode);
			return true;
		}
		else {
			// Key Exists
			return false;
		}	
	}

	/**
	* Inserts a new node into a binary search tree.
	* If the new node key is not unique, the new node will not be added. - Handled in insert() function.
	* @param root Root node of the tree
	* @param newNode Node to add
	* @return Root Node of the altered tree
	*/
	private Node insertTable(Node root,Node newNode) {
		// Reached the location to add the new node
		if(root==null) {
			root= newNode;
			return root;
		}
		// Recurse down the right child of the current node
		if(root.getKey().compareTo(newNode.getKey()) < 0 ) {
			root.setRightChild(insertTable(root.getRightChild(),newNode));
			updateAVL(root);
		}
		// Recurse down the left child of the current node
		else if(root.getKey().compareTo(newNode.getKey()) > 0 ) {
			root.setLeftChild(insertTable(root.getLeftChild(),newNode));
			updateAVL(root);
		}
		return rebalance(root);
	}
	

	/**
	* Re-balances an AVL tree at the provided node.
	* Note that the height and balance factor of the node
	* will also be updated.
	* @param root - Root of the (sub)tree to balance
	* @return The root of the newly balanced (sub)tree
	*/
	private Node rebalance(Node root) {
		if(Math.abs(root.getBalance()) == 2){
			// Must rebalance
			if(root.getBalance()==2){
				if(root.getLeftChild().getBalance()==1 || root.getLeftChild().getBalance()==0){
					return rotateRight(root);
				}
				else {
					return rotateLeftThenRight(root);
				}
			}
			else if (root.getBalance()==-2){
				if(root.getRightChild().getBalance()==-1 || root.getRightChild().getBalance()==0){
					return rotateLeft(root);
				}
				else{
					return rotateRightThenLeft(root);
				}
			}
			return root;
		}
		else{
			// Rebalance not required
			return root;
		}	
	}

	/**
	* Performs a right-left double rotation for an
	* unbalanced AVL tree.
	* @param root The root of the unbalanced tree
	* @return The root of the newly balanced tree
	*
	*/
	private Node rotateRightThenLeft(Node root) {
		root.setRightChild(rotateRight(root.getRightChild()));
		return rotateLeft(root);
	}

	/**
	* Performs a left-right double rotation for an
	* unbalanced AVL tree.
	* @param root The root of the unbalanced tree
	* @return The root of the newly balanced tree
	*
	*/
	private Node rotateLeftThenRight(Node root) {
		root.setLeftChild(rotateLeft(root.getLeftChild()));
		return rotateRight(root);
	}

	/**
	* Performs a left-left single rotation for an
	* unbalanced AVL tree.
	* @param root - The root of the unbalanced tree
	* @return The root of the newly balanced tree
	*/
	private Node rotateRight(Node root) {
		Node newRoot = root.getLeftChild();
		root.setLeftChild(newRoot.getRightChild());
		newRoot.setRightChild(root);
		updateAVL(root);
		updateAVL(newRoot);
		return newRoot;
	}

	/**
	* Performs a right-right single rotation for an
	* unbalanced AVL tree.
	* @param root - The root of the unbalanced tree
	* @return The root of the newly balanced tree
	*/
	private Node rotateLeft(Node root) {
		Node newRoot = root.getRightChild();
		root.setRightChild(newRoot.getLeftChild());
		newRoot.setLeftChild(root);
		updateAVL(root);
		updateAVL(newRoot);
		return newRoot;
	}

	/**
	 * Updates the height and balance factor of a node. Note that this method
	 * assumes all child nodes have up-to-date height and balance factors.
	 */
	private void updateAVL(Node root) {
		int leftHeight = (root.getLeftChild()==null) ? -1 : root.getLeftChild().getHeight();
		int rightHeight = (root.getRightChild()==null) ? -1 : root.getRightChild().getHeight();
		root.setBalance(leftHeight - rightHeight);
		int newHeight = Math.max(leftHeight,rightHeight) + 1;
		root.setHeight(newHeight);
	}

	/**
	 * Looks up the table entry with the provided key.
	 * 
	 * @param key
	 * @return The value of the entry with the provided key. Null if no entry with
	 *         the key can be found.
	 */
	public String lookUp(String key) {
		Node result = lookUpTree(root,key);
		if (result==null){
			return null;
		}
		else{
			return result.getValue();
		}
	}
	/**
	* Looks up the node in the binary search tree.
	* @param root Root node of the tree
	* @param key Key of the node to search for
	* @return The Node with the provided key.
	* Null if no entry with the key can be found.
	*/
	private Node lookUpTree(Node root, String key) {
		// Key is not found
		if(root==null){
			return null;
		}
		// Key is found at the current node. Return the Node
		if(root.getKey().equalsIgnoreCase(key)) {
			return root;
		}
		// Recurse down the right child of the current node 
		if(root.getKey().compareTo(key) < 0 ) {
			return lookUpTree(root.getRightChild(), key);
		}
		// Recurse down the left child of the current node
		else if(root.getKey().compareTo(key) > 0 ) {
			return lookUpTree(root.getLeftChild(), key);
		}
		return null;
	}
	
	/**
	* Updates the value for a given key.
	* @param key Key to be updated
	* @param newValue New value to be given to the key
	* @return True if key is found and value was updated
			False when the key is not found
	*/
	public boolean update(String key, String newValue) {
		return updateTree(root,key,newValue);
	}
	/**
	Updates the value for a given key.
	@param root Root node of the tree
	@param key Key to be updated
	@param newValue New value to be given to the key
	@return True if key is found and value was updated
			False when the key is not found
	*/
	private boolean updateTree(Node root, String key,String newValue) {
		// key doesn't exist
		if(root==null)return false;
		// key is found
		if(root.getKey().equalsIgnoreCase(key)) {
			root.setValue(newValue);
			return true;
		}
		// Recurse down the right child of the current node
		if(root.getKey().compareTo(key) < 0 ) {
			return updateTree(root.getRightChild(), key,newValue);
		}
		// Recurse down the left child of the current node
		else if(root.getKey().compareTo(key) > 0 ) {
			return updateTree(root.getLeftChild(), key,newValue);
		}
		return false;
	}
	
	/**
	* Deletes the table entry with the given key.
	* @param key
	* @return True if the entry was successfully deleted. False if
	* no entry with the given key was found.
	*/
	public boolean delete(String key) {
		// Check if the key exists
		if(lookUp(key)!=null) {
			// Key exists
			root = deleteFromTree(root,key);
			return true;
		}
		else {
			// Key doesn't exist
			return false;
		}
	}

	/**
	* Deletes the node with the provided key from the given tree
	* @param root The root of the tree containing the node to delete
	* @param key The key of the node to delete
	* @return The root node of the altered tree.
	*/
	private Node deleteFromTree(Node root, String key) {
		// Root is at the key which has to be deleted
		if(root.getKey().equalsIgnoreCase(key)) {
			// If this node has no children - Clean delete
			if(root.getLeftChild()== null && root.getRightChild()==null) {
				root.setKey(null);
				root.setValue(null);
				root = null;
				return root;
			}
			// If this node has 2 Children
			if(root.getLeftChild()!=null && root.getRightChild()!=null) {
				// Find largest node in the left tree of the root
				Node rootToDelete = findLargestNode(root.getLeftChild());
				// Interchange values
				root.setKey(rootToDelete.getKey());
				root.setValue(rootToDelete.getValue());
				// Delete the previously found largest node 
				root.setLeftChild(deleteFromTree(root.getLeftChild(), rootToDelete.getKey()));
				updateAVL(root);
				return rebalance(root);
			}
			// If this node has 1 Child - Left Child only
			if(root.getLeftChild()!=null && root.getRightChild()==null) {
				Node nextNode = root.getLeftChild();
				root.setLeftChild(null);
				root = null;
				return nextNode;	
			}
			// If this node has 1 child - Right child only
			else if(root.getLeftChild()==null && root.getRightChild()!=null) {
				Node nextNode = root.getRightChild();
				root.setRightChild(null);
				root = null;
				return nextNode;
			}
		}
		// Recurse down the right child of the current node 
		if(root.getKey().compareTo(key) < 0 ) {
			root.setRightChild(deleteFromTree(root.getRightChild(),key));
			updateAVL(root);
		}
		// Recurse down the left child of the current node 
		else if(root.getKey().compareTo(key) > 0 ) {
			root.setLeftChild(deleteFromTree(root.getLeftChild(),key));
			updateAVL(root);
		}
		return rebalance(root);
	}
		
	/**
	* Saves the table to a text file
	* @param fileName Name of the file to contain the table
	* @throws IOException
	*/
	public void save(String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writeNode(writer,root);
		writer.close();
	}
	
	/**
	* Writes a tree to a file using pre-order traversal
	* (parent, left, right)
	* @param writer Writer to the file
	* @param root Root node of the tree to write
	* @throws IOException
	*/
	private void writeNode(BufferedWriter writer, Node root) throws IOException {
		if(root==null) {
			return;
		}
		// PARENT
		writer.write(root.getKey()); 
		writer.newLine();
		writer.write(root.getValue());
		writer.newLine();
		writer.newLine();
		// LEFT
		writeNode(writer,root.getLeftChild());
		// RIGHT
		writeNode(writer,root.getRightChild());
	}
	
	/**
	* Finds the largest node of the provided tree
	* @param root The root of the tree
	* @return The largest node in the provided tree
	*/
	private Node findLargestNode( Node root) {
		if(root==null)return null;
		// Iterate to the right most child from the root
		while(root.getRightChild()!= null) {
			root = root.getRightChild();
		}
		return root;
	}

	public Node getRoot(){
		return root;
	}
}
