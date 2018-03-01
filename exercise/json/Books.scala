import com.google.gson.Gson
import java.io._
object Books extends App {
    case class Book(id:String, language:String, edition:String, author:String)
    case class Library(books:Array[Book])
    val gson = new Gson
    val inFile = io.Source.fromFile("./library.json")
    val outFile = new PrintWriter(new File("./output.txt" ))
    val library = gson.fromJson(inFile.getLines.reduce(_ ++ "\n" ++ _), classOf[Library])
    library match {
        case Library(books) => {
            for (book <- books){
                book match {
                    case Book(id,language,edition,author) => 
                        outFile.write(s"${id},${language},${edition},${author}\n")
                }
            }
        }
    }
    inFile.close
    outFile.close
}

/*
$ scalac -cp gson-2.3.1.jar Books.scala
$ scala -cp gson-2.3.1.jar:. Books
*/