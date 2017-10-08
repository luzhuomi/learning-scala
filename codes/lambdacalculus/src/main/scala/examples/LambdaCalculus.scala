package examples

import scala.collection.Set

object LambdaCalculus {

  sealed trait Term
  case class App(t1:Term, t2:Term) extends Term
  case class Lam(x:String, t:Term) extends Term
  case class Var(x:String) extends Term


  def pretty(t:Term):String = t match {
    case Var(x)     => x
    case Lam(v,t)   => s"(\\${v}->" ++ pretty(t) ++")"
    case App(t1,t2) => pretty(t1) ++ " " ++ pretty(t2)
  }
  // Exercise
  def fv(t:Term):Set[String] = t match {
    case Var(x)     => Set(x)
    case Lam(v,t)   => fv(t) - v
    case App(t1,t2) => fv(t1) ++ fv(t2)
  }

  val e0 = App(Var("x"), Lam("y",Var("y")))

  type Subst = (String, Term)


  def subst(s:Subst, t:Term, max:Int) : (Term,Int) = s match {
    case (x,t1) => t match {
      case Var(y) if x == y => (t1,max)
      case Var(y)           => (Var(y),max)
      case App(t2,t3)       => {
        val (u2,max2) = subst(s,t2,max)
        val (u3,max3) = subst(s,t3,max2)
        (App(u2, u3),max3)
      }
      case Lam(y,t) if x == y => {
        // clash of y with x, no need to rename
        // just stop the substitution at this term
        (Lam(y,t),max)
      }
      case Lam(y,t) if fv(t1).contains(y) => {
        val w = "x"+max
        val max1 = max + 1
        rename(w, y,t,max1) match {
          case (t2,max2) => subst(s,Lam(w,t2),max2)
        }
      }
      case Lam(y,t) => {
        subst(s,t,max) match {
          case (t1,max1) => (Lam(y,t1),max1)
        }
      }
    }
  }

  // renaming lambda bound var in the lambda body
  def rename(w:String, // fresh var
    v:String, // the original lambda var
    t:Term,   // lambda body
    max:Int // ,maxid
  ):(Term,Int) =
    // this is just a substitution
    // according to defintion of fv, fv(\v.t) = fv(t) - {v}
    // since w is fresh, it will not be clashing/captured any where in t.
    subst((v,Var(w)),t,max)


  def nor(t:Term, max:Int):(Term,Int) = t match { // we apply
    case Var(x)   => (Var(x),max)
    case Lam(v,t) => nor(t,max) match {
      case (t1,max1) => (Lam(v,t1),max1)
    }
    case App(Lam(v,t1),t2) => {
      val s = (v,t2)
      subst(s,t1,max)
    }
    case App(t1,t2) => nor(t1,max) match {
      case (t3,max3) if equiv(t1,t3,max3) => nor(t2,max3) match {
        case (t4,max4) => (App(t3,t4),max4)
      }
      case (t3,max3) => (App(t3,t2),max3)
    }
  }
  // exercise:implement aor

  // apply nor until there is no change
  def norS(t:Term):Term = {
    def reduce(t:Term,max:Int):(Term,Int) = nor(t,max) match {
      case (t1,max1) => {
        if (equiv(t,t1,max1)) {
          (t1,max1)
        } else {
          reduce(t1,max1)
        }
      }
    }
    reduce(t,0)._1
  }

  // exercise:
  def equiv(t1:Term, t2:Term, max:Int):Boolean = (t1,t2) match {
    case (Var(x),Var(y))          => x == y
    case (Lam(v1,t1), Lam(v2,t2)) => {
      val w = "x"+max
      val max1 = max+1
      subst((v1,Var(w)),t1,max1) match {
        case (t3,max3) => subst((v1,Var(w)),t2,max3) match {
          case (t4,max4) => equiv(t3,t4,max4)
        }
      }
    }
    case (App(t1,t2), App(t3,t4)) => equiv(t1,t3,max) && equiv(t2,t4,max)
    case _ => false
  }

  val x = "x"
  val y = "y"
  val z = "z"

  val e1 = App(App(Lam(x,Var(x)), Lam(y,Var(y))), Lam(z,Var(z)))
  val e2 = Lam(x,App(Lam(x,App(App(Var(x), Var(x)),Var(y))), Lam(z,App(Var(z),Var(x)))))
  val e3 = App(Lam(x,App(Var(x), Var(x))), Lam(x,App(Var(x), Var(x))))
  val e4 = App(App(Lam(y,Lam(x,Var(y))), Lam(z,Var(z))), App(Lam(x,App(Var(x), Var(x))), Lam(x,App(Var(x), Var(x)))))
}
