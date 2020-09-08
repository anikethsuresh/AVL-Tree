package AddressBook3;

public class Node {
	private String key,value;
	private Node leftChild,rightChild;
	private int height, balance;

	/**
	 * 
	 * @return Node returns the Left Child of Node
	 */
	public Node getLeftChild() {
		return leftChild;
	}

	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 
	 * @param Node sets the Left Child of Node
	 */
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * 
	 * @return Node returns the Right Child of Node
	 */
	public Node getRightChild() {
		return rightChild;
	}

	/**
	 * 
	 * @param Node sets the Right Child of Node
	 */
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * Constructor for Node object
	 * @param key 
	 * @param value
	 */
	Node(String key,String value){
		this.key = key;
		this.value = value;
		height = balance = 0;
		leftChild = rightChild = null;
	}

	/**
	 * 
	 * @return key returns key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @param key sets key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
