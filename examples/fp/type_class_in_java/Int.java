public class Int implements Eq<Int> {
	private int val;
	public Int(int x) { val = x; }
	public void setVal(int x) { val = x;}
	public int getVal() { return val; }
	public boolean eq(Int that) {
		return (val == that.getVal());
	} 
}