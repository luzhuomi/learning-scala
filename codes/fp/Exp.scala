// show connection with Java and method overriding
sealed trait Exp
case class Val(v:Int) extends Exp
case class Plus(e1:Exp, e2:Exp) extends Exp

def simp(e:Exp):Exp = e match 
{
	case Val(v) => e
	case Plus(Val(0), e2) => e2
	case Plus(e1,e2) => Plus(simp(e1), simp(e2))
}

