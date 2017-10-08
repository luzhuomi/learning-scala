import scala.collection.Set

object LambdaCalculus {

  sealed trait Term
  case class App(t1:Term, t2:Term) extends Term
  case class Lam(x:String, t:Term) extends Term
  case class Var(x:String) extends Term

  val e0 = App(Var("x"), Lam("y",Var("y")))

  // Exercise define e1, e2, e3, e4

  def pretty(t:Term):String = t match {
    case Var(x)     => x
    case Lam(v,t)   => s"(\\${v}->" ++ pretty(t) ++")"
    case App(t1,t2) => pretty(t1) ++ " " ++ pretty(t2)
  }

  // Exercise: define a function fv(t:Term):Set[String] which
  // return the set of free variables in t
  // note that given that
  //   val x:A = ...
  //   val s:Set[A] = ...
  //   s - x returns all elements in s except for x
  // given ss is another Set
  //   val ss:Set[A] = ...
  //   s ++ ss returns all elements in both s and ss
  def fv(t:Term):Set[String] = Set() // TODO:CHANGE ME



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
    // exercise: implement the missing case for Lam(v,t1) TODO: CHANGE ME
  }

  // exercise: implement equiv function among two terms t1 and t2
  //
  def equiv(t1:Term, t2:Term):Boolean = true // TODO: CHANGE ME


  // apply nor until there is no change
  def norS(t:Term):Term = {
    def reduce(t:Term,max:Int):(Term,Int) = nor(t,max) match {
      case (t1,max1) => {
        if (equiv(t,t1)) {
          (t1,max1)
        } else {
          reduce(t1,max1)
        }
      }
    }
    reduce(t,0)._1
  }


}
