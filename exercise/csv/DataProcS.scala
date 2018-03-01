object DataProcS extends App {
  import java.io._
  val inFile = io.Source.fromFile("./input.csv")
  val outFile = new PrintWriter(new File("./output.txt" ))
  val numbers = inFile.getLines.toList.map( line => {
    val cols = line.split(",").map(_.trim)
    cols(2).toInt
    }
  )
  val min = numbers.min
  val max = numbers.max
  val sum = numbers.foldLeft(0)(_ + _)
  val count = numbers.foldLeft(0)( (x,y) => x + 1)
  val mean = sum / count
  val sorted = numbers.sortWith( _ < _ )
  val median = if (count % 2 == 0) {
    val l1 = sorted((count / 2) - 1)
    val l2 = sorted(count / 2)
    (l1 + l2) / 2
  } else {
    sorted(count / 2)
  }
  outFile.write(s"min: ${min}\n")
  outFile.write(s"max: ${max}\n")
  outFile.write(s"mean: ${mean}\n")
  outFile.write(s"median: ${median}\n")
  inFile.close
  outFile.close
}
