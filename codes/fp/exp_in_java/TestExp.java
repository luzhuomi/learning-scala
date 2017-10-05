public class TestExp {

	public static void main(String[] args) {
		Exp e = new Plus(new Val(0), new Val(1));
		System.out.println(e.simp());
	}
}