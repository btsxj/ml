package la

//import scala.language.implicitConversions

object test {
  implicit val a = 9                              //> a  : Int = 9
  
  def some(implicit v: Int): Int = v              //> some: (implicit v: Int)Int
  some                                            //> res0: Int = 9
  
  implicit def intBigger(a: Int, b: Int): Int = Math.max(a,b)
                                                  //> intBigger: (a: Int, b: Int)Int
  implicit def strBigger(a: String, b: String): String = if(a.compareTo(b) > 0 ) a else b
                                                  //> strBigger: (a: String, b: String)String
  def chooseBigger[T](a: T, b: T)(implicit f: (T, T)=> T): T = f(a,b)
                                                  //> chooseBigger: [T](a: T, b: T)(implicit f: (T, T) => T)T
  chooseBigger(1,2)                               //> res1: Int = 2
  chooseBigger("y3", "y5")                        //> res2: String = y5
 
  trait foo[A]
  new foo[Int]{}                                  //> res3: la.test.foo[Int] = la.test$$anonfun$main$1$$anon$1@2e4e3801
  
  type Matrix = List[Int]
  //type Matrix = List[Row]
  
  trait Comparable[A]{
    def bigger(b: A): A
  }
  
  implicit def intComparable( a:Int ) = new Comparable[Int]{
  def bigger(b:Int):Int = Math.max(a,b)
}                                                 //> intComparable: (a: Int)la.test.Comparable[Int]

  implicit def matrixComparable(a: Matrix) = new Comparable[Matrix]{
    def bigger(b: Matrix): Matrix = if(a.length > b.length) a else b
  }                                               //> matrixComparable: (a: la.test.Matrix)la.test.Comparable[la.test.Matrix]
  
  val v1 = List(1, 2, 3)                          //> v1  : List[Int] = List(1, 2, 3)
  val v2 = List(4, 5)                             //> v2  : List[Int] = List(4, 5)
  
  for{
    e <- v1
  } yield for {
    f <- v2
  } yield e+f                                     //> res4: List[List[Int]] = List(List(5, 6), List(6, 7), List(7, 8))
    
  def chooseBigger2[T](a:T,b:T)(implicit comp: T => Comparable[T] ) : T = a.bigger(b)
                                                  //> chooseBigger2: [T](a: T, b: T)(implicit comp: T => la.test.Comparable[T])T
                                                  //| 
  def chooseBigger3[T <% Comparable[T]](a: T, b: T): T = a.bigger(b)
                                                  //> chooseBigger3: [T](a: T, b: T)(implicit evidence$1: T => la.test.Comparable
                                                  //| [T])T
  
  chooseBigger2(v1, v2)                           //> res5: List[Int] = List(1, 2, 3)
  chooseBigger3(v1, v2)                           //> res6: List[Int] = List(1, 2, 3)
  
  val sv1 = Array(Array(1.0,2.0,3), Array(4.0,5.0,6), Array(7.0,8.0,9.0))
                                                  //> sv1  : Array[Array[Double]] = Array(Array(1.0, 2.0, 3.0), Array(4.0, 5.0, 6
                                                  //| .0), Array(7.0, 8.0, 9.0))
  val sv_t = for {
    c <- sv1(0).indices
  } yield sv1.map(e => e(c))                      //> sv_t  : scala.collection.immutable.IndexedSeq[Array[Double]] = Vector(Array
                                                  //| (1.0, 4.0, 7.0), Array(2.0, 5.0, 8.0), Array(3.0, 6.0, 9.0))
  sv_t.toArray                                    //> res7: Array[Array[Double]] = Array(Array(1.0, 4.0, 7.0), Array(2.0, 5.0, 8.
                                                  //| 0), Array(3.0, 6.0, 9.0))
}