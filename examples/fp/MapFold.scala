val l = List(1,2,3,4)
val l2 = l.map(x => x * 2)

val l3 = l.foldLeft(0)((x,y) => x + y) // (((0 + 1) + 2) + 3) + 4
val l4 = l.foldRight(0)((x,y) => x + y) // 0 + (1 + (2 + (3 + 4)))
