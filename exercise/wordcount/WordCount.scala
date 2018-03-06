import java.io._
import scala.collection._
object WordCount extends App {
  val inFile = io.Source.fromFile("./input.txt")
  val outFile = new PrintWriter(new File("./output.txt" ))
  var dict = Map[String,Int]()
  for (line <- inFile.getLines) {
    val words = line.split(" ")
    for (w <- words) {
      dict.get(w) match {
        case None => dict = dict + ((w,1))
        case Some(counts) => dict = dict + ((w,counts + 1))
      }
    }
  }
  val wordCount = dict.toList
  for ( wc <- wordCount ) {
    outFile.write(s"${wc._1},${wc._2}\n")
  }
  inFile.close
  outFile.close
}
