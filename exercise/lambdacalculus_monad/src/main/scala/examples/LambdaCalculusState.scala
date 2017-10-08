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
      // exercise: implement the missing case for Lam(v,t1) TODO: CHANGE ME
    }
    // exercise:
    def equiv(t1:Term, t2:Term):State[Ctxt,Boolean] = point(true) // TODO: CHANAGE ME

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


    // exercise:implement aor
}
