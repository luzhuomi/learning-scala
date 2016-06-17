public class Val extends Exp {
	private int v;
	public Val(int v) {
		this.v = v;
	}
	public int getV() {
		return this.v;
	}
	public void setV(int v) {
		this.v = v;
	}

	public Exp simp() {
		return this;
	}

	public String toString() {
		return Integer.toString(v);
	}
}