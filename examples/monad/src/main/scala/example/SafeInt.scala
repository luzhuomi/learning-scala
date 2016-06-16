package example

import example.SafeVal._
import scalaz._
import Scalaz._

object SafeInt {
	// I don't want to use Option here because 
	// .flatMap is already defined by default
	type SafeInt = SafeVal[Int] 

	def add(x:SafeInt,y:SafeInt):SafeInt = {
		for ( x1 <- x
			; y1 <- y
			) yield (x1 + y1)
	}
	def sub(x:SafeInt,y:SafeInt):SafeInt = {
		for ( x1 <- x
			; y1 <- y
			) yield (x1 - y1)
	}
	def mult(x:SafeInt,y:SafeInt):SafeInt = {
		for ( x1 <- x
			; y1 <- y
			) yield (x1 * y1)
	}
	def div(x:SafeInt,y:SafeInt):SafeInt = {
		for ( x1 <- x
			; y1 <- y
			; if y1 != 0
			) yield (x1 / y1)
	}
}
/*
$ sbt console
scala> import example.SafeVal._
import example.SafeVal._

scala> import example.SafeInt._
import example.SafeInt._

scala> div(add(Val(1),Val(2)),Val(0))
res0: example.SafeInt.SafeInt = Err(divided by 0)
*/