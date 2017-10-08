package examples

import scala.collection.Set
import scalaz._
import scalaz.{State => S}
import Scalaz._

object LambdaCalculusState {

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



  type Subst = (String, Term)

  case class Ctxt(maxid:Int)

  // a tricky wrapper to have point with a slightly more specific instance of Monad State
	def point[A](a: => A)(implicit m:Monad[({type λ[B] = State[Ctxt,B]})#λ]):State[Ctxt,A] = m.point(a)

  def freshVar:State[Ctxt,String] = for {
    ctxt <- get[Ctxt] // local type inference is too weak
    val maxid = ctxt match { case Ctxt(maxid) => maxid }
    _ <- put(Ctxt(maxid+1))
  } yield ("x" +maxid)



  def subst(s:Subst, t:Term): State[Ctxt,Term] = s match {
    case (x,t1) => t match {
      case Var(y) if x == y => point(t1)
      case Var(y)           => point(Var(y))
      case App(t2,t3)       => for {
        u2 <- subst(s,t2)
        u3 <- subst(s,t3)
      } yield App(u2, u3)
      case Lam(y,t) if x == y => {
        // clash of y with x, no need to rename
        // just stop the substitution at this term
        point(Lam(y,t))
      }
      case Lam(y,t) if fv(t1).contains(y) => for {
        w  <- freshVar
        t2 <- rename(w, y, t)
        r  <- subst(s,Lam(w,t2))
      } yield r
      case Lam(y,t) => for {
        t1 <- subst(s,t)
      } yield Lam(y,t1)
    }
  }

  // renaming lambda bound var in the lambda body
  def rename(w:String, // fresh var
    v:String, // the original lambda var
    t:Term // lambda body,maxid
  ):State[Ctxt,Term] =
    // this is just a substitution
    // according to defintion of fv, fv(\v.t) = fv(t) - {v}
    // since w is fresh, it will not be clashing/captured any where in t.
    subst((v,Var(w)),t)


  def nor(t:Term):State[Ctxt,Term] = t match { // we apply
    case Var(x)   => point(Var(x))
    case Lam(v,t) => for {
      t1 <- nor(t)
    } yield Lam(v,t1)
    case App(Lam(v,t1),t2) => {
      val s = (v,t2)
      subst(s,t1)
    }
    case App(t1,t2) => for {
      t3 <- nor(t1)
      isEq <- equiv(t1,t3)
      t4 <- if (isEq) {
        nor(t2)
      } else {
        point(t2)
      }
    } yield App(t3,t4)
  }
  // exercise:implement aor

  // apply nor until there is no change
  def norS(t:Term):Term = {
    def reduce(t:Term):State[Ctxt,Term] = for {
        t1 <- nor(t)
        isEq <- equiv(t,t1)
        t2 <- if (isEq) {
        point(t1)
      } else {
        reduce(t1)
      }
    } yield t2
    reduce(t).run(Ctxt(0)) match {
      case (ctxt,t3) => t3
    }
  }

  // exercise:
  def equiv(t1:Term, t2:Term):State[Ctxt,Boolean] = (t1,t2) match {
    case (Var(x),Var(y))          => point(true)
    case (Lam(v1,t1), Lam(v2,t2)) => for {
      w  <- freshVar
      t3 <- subst((v1,Var(w)),t1)
      t4 <- subst((v2,Var(w)),t2)
      r  <- equiv(t3,t4)
    } yield r
    case (App(t1,t2), App(t3,t4)) => for {
      r1 <- equiv(t1,t3)
      r2 <- equiv(t2,t4)
    } yield (r1 && r2)
    case _ => point(false)
  }

  val x = "x"
  val y = "y"
  val z = "z"
  val e1 = App(Var(x), Lam(y,Var(y)))

  val e2 = App(App(Lam(x,Var(x)), Lam(y,Var(y))), Lam(z,Var(z)))
  val e3 = Lam(x,App(Lam(x,App(App(Var(x), Var(x)),Var(y))), Lam(z,App(Var(z),Var(x)))))
  val e4 = App(Lam(x,App(Var(x), Var(x))), Lam(x,App(Var(x), Var(x))))
  val e5 = App(App(Lam(y,Lam(x,Var(y))), Lam(z,Var(z))), App(Lam(x,App(Var(x), Var(x))), Lam(x,App(Var(x), Var(x)))))
}
