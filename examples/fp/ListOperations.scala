// length
val l1 = List(1,2,3)
val len = l1.length // 3
// concatenation
val l2 = List(-1,-2,-3)
val l3 = l1 ++ l2
// reverse
val l4 = l1.reverse // List(3,2,1)
// min/max
val min = l1.min // 1
val max = l1.max // 3
// sorting
val sl3 = l3.sortWith(_ < _) // List(-3, -2, -1, 1, 2, 3)
// sub-list
val sl3a = sl3.take(3) // List(-3,-2,1)
val sl3b = sl3.drop(3) // List(1,2,3)

