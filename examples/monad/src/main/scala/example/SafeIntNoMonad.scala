package example

import example.SafeVal._

object SafeIntNoMonad {
	type SafeInt = SafeVal[Int]
	/*
	def add(x:SafeInt, y:SafeInt):SafeInt = x match 
	{
		case Err(msg) => Err(msg)
		case Val(i) => y match 
		{
			case Err(msg) => Err(msg)
			case Val(j) => Val(i + j)
		}
	} */

	def pnt[A](x: => A):SafeVal[A] = Val(x)
	def bnd[A,B](sa: SafeVal[A])(f: A => SafeVal[B]): SafeVal[B] = sa match {
		case Err(msg) => Err(msg)
		case Val(a)   => f(a)
	}
	// redefining using pnt and bnd
	def add(x:SafeInt, y:SafeInt):SafeInt = {
		bnd(x)(x1 => { 
			bnd(y)(y1 => pnt(x1+y1))
		})
	}
	def sub(x:SafeInt, y:SafeInt):SafeInt = x match 
	{
		case Err(msg) => Err(msg)
		case Val(i) => y match 
		{
			case Err(msg) => Err(msg)
			case Val(j) => Val(i - j)
		}
	}
	def mult(x:SafeInt, y:SafeInt):SafeInt = x match 
	{
		case Err(msg) => Err(msg)
		case Val(i) => y match 
		{
			case Err(msg) => Err(msg)
			case Val(j) => Val(i * j)
		}
	}
	/*
	def div(x:SafeInt, y:SafeInt):SafeInt = x match 
	{
		case Err(msg) => Err(msg)
		case Val(i) => y match 
		{
			case Err(msg) => Err(msg)
			case Val(j) if j == 0 => Err("divided by 0")
			case Val(j) => Val(i / j)
		}
	}
	*/
	// redefining using pnt and bnd
	def div(x:SafeInt, y:SafeInt):SafeInt = {
		bnd(x)( x1 => {
			bnd(y)( y1 => {
				if (y1 == 0) { Err("divided by 0") }
				else { pnt(x1 / y1) }
			})
		})
	}
}

/*
$ sbt console
scala> import example.SafeVal._
import example.SafeVal._
scala> import example.SafeIntNoMonad._
import example.SafeIntNoMonad._

scala> div(add(Val(1),Val(2)),Val(0))
res0: example.SafeIntNoMonad.SafeInt = Err(divided by 0)
*/