package naivebayes

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(84); 
  val a = Array((0.0, 0.0),(1.0, 0.0), (2.0, 2.0));System.out.println("""a  : Array[(Double, Double)] = """ + $show(a ));$skip(39); 
  val b = a.filter( e => e._1 == e._2);System.out.println("""b  : Array[(Double, Double)] = """ + $show(b ));$skip(75); 
  
  val c = Iterator("big", "age", "born", "data", "hello", "data").toSeq;System.out.println("""c  : Seq[String] = """ + $show(c ));$skip(37); 
  val d = Seq("big", "big2", "data");System.out.println("""d  : Seq[String] = """ + $show(d ));$skip(56); 
  
  val e = d.map( dw => c.count( w => w.equals(dw)) );System.out.println("""e  : Seq[Int] = """ + $show(e ))}
 
}
