import com.google.gson.Gson

object JSONExample extends App {
    case class Contact(firstName: String, lastName: String, phone: Int)
    val ironman = new Contact("Tony", "Stark", 12345678)
    val gson = new Gson
    println(gson.toJson(ironman))
    val cap = gson.fromJson("""
        {"firstName":"Steve","lastName":"Roger","phone":0}
    """, classOf[Contact]
    )
    println(gson.toJson(cap))
}

/*
$ scalac -cp gson-2.3.1.jar JSONExmaple.scala
$ scala -cp gson-2.3.1.jar:. JSONExample
*/