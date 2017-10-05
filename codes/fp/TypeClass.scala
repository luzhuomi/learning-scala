object TypeClass {
	trait Eq[A] {
		def eq(x:A,y:A):Boolean 
	}
	implicit val eqInt = new Eq[Int] {
	// OR
	// 	implicit object eqInt extends Eq[Int] {
		def eq(x:Int,y:Int):Boolean = {
			x == y
		}
	}
	implicit def eqList[A](implicit ev:Eq[A]) = new Eq[List[A]] {
	// OR implicit class eqList[A](ev:Eq[A]) extends Eq[List[A]] {	
		def eq(xs:List[A],ys:List[A]):Boolean = (xs,ys) match {
			case (Nil,Nil) => true
			case (x::xs2, y::ys2) => ev.eq(x,y) && eq(xs2,ys2)
			case (_, _) => false
		}
	}

	def check[A](x:List[A], y:List[A])(implicit eqList:Eq[List[A]]) = eqList.eq(x,y) 
}

/*
scala> check(List(1,2,3), List(1,2,3))
res0: Boolean = true

scala> check(List(1,2,3), List(1,2,3))
res1: Boolean = false
*/