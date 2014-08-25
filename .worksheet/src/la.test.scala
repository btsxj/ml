package la

//import scala.language.implicitConversions

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(91); 
  implicit val a = 9;System.out.println("""a  : Int = """ + $show(a ));$skip(40); 
  
  def some(implicit v: Int): Int = v;System.out.println("""some: (implicit v: Int)Int""");$skip(7); val res$0 = 
  some;System.out.println("""res0: Int = """ + $show(res$0));$skip(65); 
  
  implicit def intBigger(a: Int, b: Int): Int = Math.max(a,b);System.out.println("""intBigger: (a: Int, b: Int)Int""");$skip(90); 
  implicit def strBigger(a: String, b: String): String = if(a.compareTo(b) > 0 ) a else b;System.out.println("""strBigger: (a: String, b: String)String""");$skip(70); 
  def chooseBigger[T](a: T, b: T)(implicit f: (T, T)=> T): T = f(a,b);System.out.println("""chooseBigger: [T](a: T, b: T)(implicit f: (T, T) => T)T""");$skip(20); val res$1 = 
  chooseBigger(1,2);System.out.println("""res1: Int = """ + $show(res$1));$skip(27); val res$2 = 
  chooseBigger("y3", "y5")
 
  trait foo[A];System.out.println("""res2: String = """ + $show(res$2));$skip(34); val res$3 = 
  new foo[Int]{}
  
  type Matrix = List[Int]
  //type Matrix = List[Row]
  
  trait Comparable[A]{
    def bigger(b: A): A
  };System.out.println("""res3: la.test.foo[Int] = """ + $show(res$3));$skip(217); 
  
  implicit def intComparable( a:Int ) = new Comparable[Int]{
  def bigger(b:Int):Int = Math.max(a,b)
};System.out.println("""intComparable: (a: Int)la.test.Comparable[Int]""");$skip(143); 

  implicit def matrixComparable(a: Matrix) = new Comparable[Matrix]{
    def bigger(b: Matrix): Matrix = if(a.length > b.length) a else b
  };System.out.println("""matrixComparable: (a: la.test.Matrix)la.test.Comparable[la.test.Matrix]""");$skip(28); 
  
  val v1 = List(1, 2, 3);System.out.println("""v1  : List[Int] = """ + $show(v1 ));$skip(22); 
  val v2 = List(4, 5);System.out.println("""v2  : List[Int] = """ + $show(v2 ));$skip(64); val res$4 = 
  
  for{
    e <- v1
  } yield for {
    f <- v2
  } yield e+f;System.out.println("""res4: List[List[Int]] = """ + $show(res$4));$skip(91); 
    
  def chooseBigger2[T](a:T,b:T)(implicit comp: T => Comparable[T] ) : T = a.bigger(b);System.out.println("""chooseBigger2: [T](a: T, b: T)(implicit comp: T => la.test.Comparable[T])T""");$skip(69); 
  def chooseBigger3[T <% Comparable[T]](a: T, b: T): T = a.bigger(b);System.out.println("""chooseBigger3: [T](a: T, b: T)(implicit evidence$1: T => la.test.Comparable[T])T""");$skip(27); val res$5 = 
  
  chooseBigger2(v1, v2);System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(24); val res$6 = 
  chooseBigger3(v1, v2);System.out.println("""res6: List[Int] = """ + $show(res$6));$skip(77); 
  
  val sv1 = Array(Array(1.0,2.0,3), Array(4.0,5.0,6), Array(7.0,8.0,9.0));System.out.println("""sv1  : Array[Array[Double]] = """ + $show(sv1 ));$skip(72); 
  val sv_t = for {
    c <- sv1(0).indices
  } yield sv1.map(e => e(c));System.out.println("""sv_t  : scala.collection.immutable.IndexedSeq[Array[Double]] = """ + $show(sv_t ));$skip(15); val res$7 = 
  sv_t.toArray;System.out.println("""res7: Array[Array[Double]] = """ + $show(res$7))}
}
