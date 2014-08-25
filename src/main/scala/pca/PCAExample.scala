package pca

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.Matrix
import org.apache.spark.mllib.linalg.distributed.RowMatrix

object PCAExample extends App {
  
  val spConfig = (new SparkConf).setMaster("local").setAppName("SparkPCADemo")
  val sc = new SparkContext(spConfig)
  
  // Load and parse the data file.
  val rows = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/pca.txt").map { line =>
    val values = line.split(' ').map(_.toDouble)
    Vectors.dense(values)
  }
  
  val mat = new RowMatrix(rows)
  
  val pc: Matrix = mat.computePrincipalComponents(mat.numCols.toInt)
  
  println("Principal components :")
  println(pc)
  
  sc.stop
}

/*
pca.txt

Principal components :
-0.6728468491327462  -0.739781804055855  
-0.7397818040558551  0.6728468491327463

*/
