

class Person(n:String,i:String) {
	private val name:String = n
	private val id:String   = i
	def getName():String = name
	def getId():String = id
}

trait NightOwl {
	def stayUpLate():Unit 
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


/*
scala> :load OOP.scala
Loading OOP.scala...
defined class Person
defined trait NightOwl
defined class Student
defined class Staff

scala> val tom = new Student("Tom", "X1235", 4.0)
tom: Student = Student@601c1dfc

scala> val jerry = new Staff("Jerry", "T0001", 500000.0)
jerry: Staff = Staff@650fbe32

scala> tom.stayUpLate
woohoo
*/