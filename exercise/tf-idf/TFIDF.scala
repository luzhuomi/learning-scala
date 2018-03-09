import java.io._
import scala.collection._
import math._
object TFIDF extends App {
  val inFile = io.Source.fromFile("./input.txt")
  val outFile = new PrintWriter(new File("./output.txt" ))
  val docs  = inFile.getLines.toList
  val totaldocs = docs.length

  def mapred(word:List[String]) : Map[String,Int] = { 
    val empty_dict = Map[String,List[Int]]()
    words.map(w => (w,1))
    .foldLeft(empty_dict)( (dict,t) => t match {
      case (w,one) => dict.get(w) match {
        case None => dict + ((w,List(one)))
        case Some(ones) => dict + ((w,ones ++ List(one)))
      }
    })
    .map( x => x match {
      case (w,l) => (w,l.sum)
    })
  }

  val tf = mapred(docs.flatMap( doc => doc.split(" ").map( _.trim ) ))
  val df = mapred(docs.flatMap( doc => doc.split(" ").map( _.trim ).toSet.toList ))
  val tfidf = tf.map( wc => wc match {
    case (w,termfreq) => df.get(w) match {
      case None => (w,0) // not possible
      case Some(docfreq) => (w,termfreq * log(totaldocs *1.0 / docfreq))
    }
  })
   
  for ( wc <- tfidf ) {
    outFile.write(s"${wc._1},${wc._2}\n")
  }
  inFile.close
  outFile.close
}
