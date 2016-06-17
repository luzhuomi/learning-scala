public class TestEq {
	public static <T extends Eq<T>> boolean check(List<T> l1, List<T> l2) {
		return l1.eq(l2);
	}
	public static void main(String[] args) {
		List<Int> l1 = new List<Int>();
		List<Int> l2 = new List<Int>();
		List<Int> l3 = new List<Int>();
		Int i1 = new Int(1);
		Int i2 = new Int(2);
		Int i3 = new Int(3);
		l1.insert(i1); l1.insert(i2); l1.insert(i3);
		l2.insert(i1); l2.insert(i2); l2.insert(i3);
		l3.insert(i1); l3.insert(i3);

		System.out.println(check(l1,l2));
		System.out.println(check(l1,l3));
	}
}