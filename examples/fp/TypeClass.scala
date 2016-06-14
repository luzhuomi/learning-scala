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
		def eq(xs:List[A],ys:List[A]):Boolean = (xs,ys) match {
			case (Nil,Nil) => true
			case (Nil, _ ) => false
			case (_,  Nil) => false
			case (x::xs_, y::ys_) => ev.eq(x,y) && eq(xs_,ys_)
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