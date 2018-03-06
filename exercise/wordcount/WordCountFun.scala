import java.io._
import scala.collection._
object WordCountFun extends App {
  val inFile = io.Source.fromFile("./input.txt")
  val outFile = new PrintWriter(new File("./output.txt" ))
  val words = inFile.getLines.toList.flatMap( line => line.split(" ") )
  val empty_dict = Map[String,List[Int]]()
  val wordCount = words.map(w => (w,1))
          .foldLeft(empty_dict)( (dict,t) => t match {
            case (w,one) => dict.get(w) match {
              case None => dict + ((w,List(one)))
              case Some(ones) => dict + ((w,ones ++ List(one)))
            }
          })
          .toList
          .map( wl => wl match {
            case (w,l) => (w,l.sum)
          })
  for ( wc <- wordCount ) {
    outFile.write(s"${wc._1},${wc._2}\n")
  }
  inFile.close
  outFile.close
}
