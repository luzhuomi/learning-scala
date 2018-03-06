import java.io._

object StopWords extends App {
  val inFile = io.Source.fromFile("./input.txt")
  val outFile = new PrintWriter(new File("./output.txt" ))
  // TODO: read the stop words from a file.
  val stop_words = List("and", "is", "it", "are", "in", "rt", "what", "from",
                "her", "to", "their", "you", "me", "his", "http", "that", "they", "by",
                "he", "a", "on", "for", "i", "of", "this", "she", "the", "my", "at").toSet
  for (line <- inFile.getLines) {
    val words = line.toLowerCase.split(" ").map(_.trim)
    val no_stopwords = words.filter( w => !stop_words.contains(w))
    outFile.write(no_stopwords.mkString(" ") + "\n")
  }
  inFile.close
  outFile.close
}
