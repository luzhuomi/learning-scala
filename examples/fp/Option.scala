// example for option types
object Option {
	/*
	sealed trait Option[+A] 
	case object None extends Option[Nothing]
	case class Some[A](x:A) extends Option[A]
	*/
	def div(x:Int)(y:Int):Option[Int] = 
	{
		if (y == 0) None else Some(x/y)
	}

	val xs = List(0,1,2) map (x => div(2)(x)) // List(None, Some(2), Some(1))
	val ys = xs filter (x => x.nonEmpty) // List(Some(2), Some(1))
	val zs = ys map ( x => x match { case Some(y) => y } ) // List(2,1)
}