object PartialFun {
	def div(x:Int): PartialFunction[Int,Int] = {
		case (y:Int) if y != 0 => x/y
	}
	val xs = List(0,1,2) collect ( div(2) ) // List(2, 1)
	// val ys = List(0,1,2) map ( div(2) ) // we have a scala.MatchError
	val mkZero: PartialFunction[Int,Int] = {
		case (y:Int) if y == 0 => 0
	}
	val ys = List(0,1,2) map ( div(2) orElse mkZero ) // List(0, 2, 1)
}