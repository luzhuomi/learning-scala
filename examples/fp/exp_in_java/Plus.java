public class Plus extends Exp {
	private Exp e1;
	private Exp e2;
	public Plus(Exp e1, Exp e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	public Exp getE1() { return e1; }
	public Exp getE2() { return e2; }
	public void setE1(Exp e1) { this.e1 = e2;}
	public void setE2(Exp e2) { this.e2 = e2;}
	public Exp simp() {
		if (e1 instanceof Val) {
			Val v1 = (Val)e1;
			if (v1.getV() == 0) {
				return e2;
			}
		}
		return new Plus(e1.simp(),e2.simp());
	}
	public String toString() {
		return e1.toString() + " + " + e2.toString();
	}
}