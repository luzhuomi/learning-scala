object ImplicitFun {
	implicit def logger(mesg:String):Unit = println("[Log] " + mesg)

	def fib(x:Int)(implicit log:String=>Unit):Int = x match  {
		case 0 => 0
		case 1 => 1
		case _ => {
			log("computing fib(" + x +")")
			fib(x-1) + fib(x-2)			
		}
	}

	// fib(5)
	// fib(5)(x => Unit)
}