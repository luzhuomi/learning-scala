import java.io._

object LinkExtractor2 extends App {
    val inFile = io.Source.fromFile("./page.html")
    val outFile = new PrintWriter(new File("./output.txt" ))
    val anchor = """<a href=\"([^"]*)\"[^>]*>""".r
    val links = for (line <- inFile.getLines) {
        for (link <- anchor.findAllIn(line)) {
            outFile.write(s"${link}\n")
        }
    }
    inFile.close
    outFile.close
}