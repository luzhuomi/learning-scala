package example

object ListNoMonad {
	def mult[A,B](as:List[A], bs:List[B]):List[(A,B)] = {
		as flatMap ( a => 
			bs map ( b => (a,b) )
		)
	}

	val l1 = List(1,2,3)
	val l2 = List('a','b')

}


/*
$ sbt console
Welcome to Scala version 2.10.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_65).
Type in expressions to have them evaluated.
Type :help for more information.

scala> import example.ListNoMonad._
import example.ListNoMonad._

scala> mult(l1,l2)
res0: List[(Int, Char)] = List((1,a), (1,b), (2,a), (2,b), (3,a), (3,b))
*/