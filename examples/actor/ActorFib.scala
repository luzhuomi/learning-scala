import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ActorFib {

	case class FibRequest(n:Int)
	case class FibResult(n:Int)
	case object Stop

	implicit val timeout = akka.util.Timeout(15 seconds)

	def fib(x:Int):Int = x match {
		case 0 => 0
		case 1 => 1
		case n => fib(n-1) + fib(n-2)
	}

	class FibActor extends Actor {
		def receive = {
			case Stop => context.stop(self)
			case FibRequest(n) if n <= 0 => sender ! FibResult(0)
			case FibRequest(n) if n < 10 => sender ! FibResult(fib(n))
			case FibRequest(n) if n >= 10 => {
				val child1 = context.actorOf(Props[FibActor]) 
				val child2 = context.actorOf(Props[FibActor])
				val future1:Future[Any] = child1 ? FibRequest(n-1)
				val future2:Future[Any] = child2 ? FibRequest(n-2)
				val dest = sender
				val future1and2:Future[Int] = for (
					FibResult(v1) <- future1 ;
					FibResult(v2) <- future2 
					) yield v1+v2
				future1and2 onComplete {
					case Success(v) => 
						child1 ! Stop
						child2 ! Stop
						dest ! FibResult(v) // can't user sender here, because sender refers to one in the future
					case Failure(e) => println("failed!" + e.toString)
				}
				/*
				future1 onComplete {
					case Success(FibResult(v1)) => {
						future2 onComplete {
							case Success(FibResult(v2)) => {
								val v = v1+v2 
								child1 ! Stop
								child2 ! Stop
								dest ! FibResult(v)
							}
							case _ => println("future2 failed!")
						}
					}
					case _ => println("future1 failed!")
				}*/
			}
		}
	}

	def main(args:Array[String]):Unit = {
		val system = ActorSystem("FibSystem")
		val fibActor = system.actorOf(Props[FibActor])
		// start them going
		val n = if (args.length > 0) args(0).toInt else 10 
		val f = fibActor ? FibRequest(n)
		f onComplete {
			case Success(v) => 
				println(v)
				fibActor ! Stop
			case Failure(e) => println("failed!" + e.toString)
		}
	}

}

