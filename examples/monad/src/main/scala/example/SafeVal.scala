package example

import scalaz._
import Scalaz._


object SafeVal {
	sealed trait SafeVal[+T] 
	case class Val[T](x:T) extends SafeVal[T]
	case class Err(msg:String) extends SafeVal[Nothing]

	implicit object safeValMonadPlus extends MonadPlus[SafeVal] {
		override def point[A](a: => A):SafeVal[A] = Val(a)
		override def bind[A, B](sa: SafeVal[A])(f: A => SafeVal[B]): SafeVal[B] = sa match {
			case Err(msg) => Err(msg)
			case Val(a)   => f(a)
		} 
		override def empty[A]:SafeVal[A] = Err("divided by 0")
		override def plus[A](x:SafeVal[A],y: => SafeVal[A]):SafeVal[A] = x match {
			case Err(_) => y
			case Val(_) => x
		}

	}

}