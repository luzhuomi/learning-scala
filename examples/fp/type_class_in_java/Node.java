public class Node<T extends Eq<T>> implements Eq<Node<T>> {
	private T val;
	private Node<T> next;
	public Node(T v) { 
		val = v;
		next = null;
	}
	public T getVal() { return val; }
	public void setVal(T v) { val = v;}
	public Node<T> getNext() { return next; }
	public void setNext(Node<T> n) {
		next = n;
	}
	public boolean eq(Node<T> that) {
		if (!val.eq(that.getVal())) { 
			return false; 
		}
		else {
			if ((next == null) && (that.getNext() == null)) { return true; }
			else if ((next == null) && (that.getNext() != null)) { return false; }			
			else if ((next != null) && (that.getNext() == null)) { return false; } 
			else {
				return next.eq(that.getNext());
			}
		} 
	} 
}
