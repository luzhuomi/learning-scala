package example

import scalaz._
import Scalaz._


object ListMonad {
	implicit object listMonadPlus extends MonadPlus[List] {
		override def point[A](a: => A):List[A] = List(a)
		override def bind[A, B](sa: List[A])(f: A => List[B]): List[B] = {
			sa flatMap f
		}
		override def empty[A]:List[A] = Nil
		override def plus[A](x:List[A],y: => List[A]):List[A] = x ++ y
	}

	val l1 = List(1,2,3)
	val l2 = List('a','b')

	def mult[A,B](as:List[A], bs:List[B]) : List[(A,B)] = for 
	( a <- as
	; b <- bs
	) yield (a,b)
}

/*

$ sbt console
Welcome to Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_65).
Type in expressions to have them evaluated.
Type :help for more information.

scala> import example.ListMonad._
import example.ListMonad._

scala> mult(l1,l2)
res0: List[(Int, Char)] = List((1,a), (1,b), (2,a), (2,b), (3,a), (3,b))
*/