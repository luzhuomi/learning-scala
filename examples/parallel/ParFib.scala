object ParFib extends App {
	def fib(x:Int):Int = x match {
		case 0 => 0
		case 1 => 1
		case n => fib(n-1) + fib(n-2)
	}
	println((30 to 40).toList.par.map(fib))
}