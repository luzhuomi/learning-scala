import java.io._

object LinkExtractor extends App {

    val inFile = io.Source.fromFile("./page.html")
    val outFile = new PrintWriter(new File("./output.txt" ))
    val anchor = """.*<a href=\"([^"]*)\"[^>]*>.*""".r
    val links = for (line <- inFile.getLines)  {
        line match {
            case anchor(link) => 
                outFile.write(s"${link}\n")
            case _ => () 
        }
    }
    inFile.close
    outFile.close
}