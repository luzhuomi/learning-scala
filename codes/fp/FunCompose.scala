val l1 = List(1,2,3)
val l2 = l1.map (((x:Int) => x*2) compose (y=>y+1)) //  List(4, 6, 8)
val l3 = l1.map (((x:Int) => x*2) andThen (y=>y+1)) 