package naivebayes

object test {
  val a = Array((0.0, 0.0),(1.0, 0.0), (2.0, 2.0))//> a  : Array[(Double, Double)] = Array((0.0,0.0), (1.0,0.0), (2.0,2.0))
  val b = a.filter( e => e._1 == e._2)            //> b  : Array[(Double, Double)] = Array((0.0,0.0), (2.0,2.0))
  
  val c = Iterator("big", "age", "born", "data", "hello", "data").toSeq
                                                  //> c  : Seq[String] = Stream(big, ?)
  val d = Seq("big", "big2", "data")              //> d  : Seq[String] = List(big, big2, data)
  
  val e = d.map( dw => c.count( w => w.equals(dw)) )
                                                  //> e  : Seq[Int] = List(1, 0, 2)
 
}