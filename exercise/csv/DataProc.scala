object DataProc extends App {
  import java.io._
  val inFile = io.Source.fromFile("./input.csv")
  val outFile = new PrintWriter(new File("./output.txt" ))
  for (line <- inFile.getLines) {
    val cols = line.split(",").map(_.trim)
    println(s"${cols(0)}|${cols(1)}|${cols(2)}")
    outFile.write(s"${cols(0)}|${cols(1)}|${cols(2)}\n")
  }
  inFile.close
  outFile.close
}
