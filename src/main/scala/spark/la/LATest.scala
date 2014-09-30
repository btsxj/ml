package spark.la


/**
 *  Spark 1.0.2
 *  
 *  Local Vector
 *   - Dense Vector
 *   - Sparse Vector
 *   
 *  Local Matrix
 *   - Dense Matrix
 *   - Sparse Matrix ?in future
 *   
 *  Distributed Matrix
 *   - RowMatrix <- RowMatrix(RDD[Vector])
 *   - IndexedRowMatrix <- IndexedRowMatrix(RDD[IndexedRow])   IndexedRow(index: Long, vector: Vector) 
 *   - CoordinateMatrix <- CoordinateMatrix(RDD[MatrixEntry])  MatrixEntry(i: Long, j: Long, value: Double) 
 *   - Other types ?
 */

/**
 * [0 2 3]   [0 0 7]   [0 43 18]
   [4 0 0] * [0 8 0] = [0  0 28]
   [0 0 5]   [0 9 6]   [0 45 30]
   
  val dv1 = Vectors.dense(0.0, 2.0, 3.0)
  val sv2 = Vectors.sparse(3, Seq((1, 4.0)))
  val sv3 = Vectors.sparse(3, Seq((3, 5.0)))
  
  val sv4 = Vectors.sparse(3, Seq((3, 7.0)))
  val sv5 = Vectors.sparse(3, Seq((2, 8.0)))
  val dv6 = Vectors.dense(0.0, 9.0, 6.0)
  
    0 -1 2
    4 11 2
    
    3 -1
    1 2
    6 1
    
    = 11 0
      35 20
  
 */

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import org.apache.spark.mllib.linalg.{Vector, Vectors, Matrices}
import org.apache.spark.mllib.linalg.distributed.RowMatrix


object LATest extends App {
  
  val spConfig = (new SparkConf).setMaster("local").setAppName("SparkLA")
  val sc = new SparkContext(spConfig)
 
  // Load and parse Coordinate(COO) Sparse Matrix
  val coo_matrix_input = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/latest3_coo.txt").map( line =>
    line.split(' ')
  )
  println(coo_matrix_input.partitions.size)
  val cols_num = coo_matrix_input.collect.apply(1).map(_.toInt).reduceLeft(_ max _) + 1
  val coo_matrix_tuple = coo_matrix_input.flatMap(_.zipWithIndex).groupBy(_._2).values.map(e => e.map(d => d._1)).map{ e =>
    val a = e.toArray
    (a(0).toInt, (a(1).toInt, a(2).toDouble))
  }
  val coo_matrix = coo_matrix_tuple.groupByKey.sortByKey(true)
  val rows = coo_matrix.map{ e =>
    Vectors.sparse(cols_num, e._2.toSeq)
  }
  coo_matrix.foreach{e => print(e._1 + " "); e._2.foreach( e => print("("+e._1 + " " + e._2 + ") ")); println}


  // Load and parse the data file.
  /*val rows = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/latest3.txt").map { line =>
    val values = line.split(' ').map(_.toDouble)
    Vectors.sparse(values.length, values.zipWithIndex.map(e => (e._2, e._1)).filter(_._2 != 0.0) )
  } */
  // Build a distributed RowMatrix
  val rmat = new RowMatrix(rows)
  
  // Build a local DenseMatrix
  val dm = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/latest4.txt").map { line =>
    val values = line.split(' ').map(_.toDouble)
    Vectors.dense(values)
  }
  //dm.foreach(println)
  
  println(dm.count + " * " + dm.take(1)(0).size)
  val ma = dm.map(_.toArray).take(dm.count.toInt)
  val localMat = Matrices.dense( dm.count.toInt, dm.take(1)(0).size, transpose(ma).flatten )  
  
  // Multiply two matrices
  rmat.multiply(localMat).rows.foreach(println)

  
  /**
   * Transpose a matrix
   */
  def transpose(m: Array[Array[Double]]): Array[Array[Double]] = {
    (for {
      c <- m(0).indices
    } yield m.map(_(c)) ).toArray
    
  }
}