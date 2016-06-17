object ThreadFib extends App {
	def fib(x:Int):Int = x match {
		case 0 => 0
		case 1 => 1
		case n => fib(n-1) + fib(n-2)
	}

	for (i <- 30 to 40) { 
		new Thread(new Runnable {
			def run() {
				println(fib(i))
			}
		}).start
	}
}