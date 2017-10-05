import scala.collection.Set
import scala.collection.Map


sealed trait Term
case class App(t1:Term, t2:Term) extends Term
case class Lam(x:String, t:Term) extends Term
case class Var(x:String) extends Term


def fv(t:Term):Set[String] = t match {
  case Var(x) => Set(x)
  case Lam(v,t) => fv(t) - v
  case App(t1,t2) => fv(t1) ++ fv(t2)
}


val e1 = App(Var("x"), Lam("y",Var("y")))


type Subst = (String, Term)

def appSubst(s:Subst, t:Term) : Term = subst match {
  case (x,t1) => t match {
    case Var(y) if x == y => t1
    case Var(y)           => Var(y)
    case App(t2,t3)       => App(appSubst(s,t2), appSubst(s,t3)) 
    case Lam(v,t) if x == v => appSubst(s,rename(t,(fv(t)+x)))
    case Lam(v,t) if fv(t) 
  }
}
