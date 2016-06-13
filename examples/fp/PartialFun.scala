object PartialFun {
	def div(x:Int): PartialFunction[Int,Int] = {
		case (y:Int) if y != 0 => x/y
	}
	// val x = div(2)(add(1)(mult(3)(0))) // 2
	// val y = div(2)(add(0)(mult(3)(0))) // this will make the partial function ill defined
	val xs = List(0,1,2) collect ( div(2) ) // List(2, 1)
}