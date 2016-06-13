object List { // this is like a package
	sealed trait List[+A] {
		def map[B](f:A=>B):List[B] = this match {
			case Nil => Nil
			case Cons(x,xs) => Cons(f(x),xs.map(f))
		}
	}
	case object Nil extends List[Nothing]
	case class Cons[A](x:A, xs:List[A]) extends List[A]



	def length[A](l:List[A]):Int = l match {
		case Nil => 0
		case Cons(_,xs) => 1 + length(xs)
	}

	val x = Cons(1,Cons(2,Nil))
	val y = x.map(_+1)
}
