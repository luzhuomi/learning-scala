

class Person(n:String,i:String) {
	private val name:String = n
	private val id:String   = i
	def getName():String = name
	def getId():String = id
}


class Student(n:String, i:String, g:Double) extends Person(n,i) with NightOwl {
	private var gpa = g
	def getGPA() = gpa
	def setGPA(g:Double) = 
	{
		gpa = g
	}
	override def stayUpLate():Unit = 
	{ 
		println("woohoo") 
	}
}

class Staff(n:String, i:String, sal:Double) extends Person(n,i) {
	private var salary = sal
	def getSalary() = salary
	def setSalary(sal:Double) = 
	{
		salary = sal
	}
}

trait NightOwl {
	def stayUpLate():Unit 
}

/*
object OOP
{
	

}
*/
/*
class Rational(n: Int, d: Int) {
	private def gcd(x: Int, y: Int): Int = {
		if (x == 0) y
		else if (x < 0) gcd(-x, y)
		else if (y < 0) -gcd(x, -y)
		else gcd(y % x, x)
	}
	private val g = gcd(n, d)
	val numer: Int = n/g
	val denom: Int = d/g
	def +(that: Rational) = new Rational(numer * that.denom + that.numer * denom,denom * that.denom)
	def -(that: Rational) =	new Rational(numer * that.denom - that.numer * denom,denom * that.denom)
	def *(that: Rational) =	new Rational(numer * that.numer, denom * that.denom)
	def /(that: Rational) =	new Rational(numer * that.denom, denom * that.numer)
}
*/