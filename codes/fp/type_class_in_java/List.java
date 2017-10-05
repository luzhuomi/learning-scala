public class List<T extends Eq<T>> implements Eq<List<T>> {
	private Node<T> pHead; 
	public List() { 
		pHead = null;
	}
	public void insert(T v) {
		Node<T> n = new Node<T>(v);
		n.setNext(pHead);
		pHead = n;
	}
	public Node<T> getPHead() { return pHead; }

	public boolean eq(List<T> that) {
		return pHead.eq(that.getPHead());
	}

}