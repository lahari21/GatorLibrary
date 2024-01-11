class Node implements Comparable<Node> {
	boolean RED = true;
	boolean BLACK = false;
	Data data;
	int key;
	Node parent, left, right;
	// red = t black=f
	boolean color;

	// parameterised constructor
	public Node(int bookId, String bookName, String authorName, String availabilityStatus) {
		this.data = new Data(bookId, bookName, authorName, availabilityStatus);
		this.key = bookId;
		this.color = RED;

	}

	// setting the node
	void set(Node node) {
		this.key = node.key;
		this.data = node.data;
	}

	@Override
	public int compareTo(Node node) {
		return this.data.compareTo(node.data);
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

}