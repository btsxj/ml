package svd

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.Matrix
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.linalg.SingularValueDecomposition


object SVDExample extends App {
  
  val spConfig = (new SparkConf).setMaster("local").setAppName("SparkSVDDemo")
  val sc = new SparkContext(spConfig)
  
  // Load and parse the data file.
  //http://en.wikibooks.org/wiki/Data_Mining_Algorithms_In_R/Dimensionality_Reduction/Singular_Value_Decomposition
  val rows = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/pca.txt").map { line =>
    val values = line.split(' ').map(_.toDouble)
    Vectors.dense(values)
  }
  
  val mat = new RowMatrix(rows)
  
  // Compute SVD
  val svd = mat.computeSVD(mat.numCols().toInt, computeU = true)
  val U: RowMatrix = svd.U
  val s: Vector = svd.s
  val V: Matrix = svd.V // A=UsV' : V' is the transpose of V 
  
  println("Left Singular vectors :")
  U.rows.foreach(println)
  //U.rows.toArray.take(1).foreach(println)
  
  
  println("Singular values are :")
  println(s)
  
  println("Right Singular vectors :")
  println(V)
  //V.toArray.take(s.size).foreach(println)
  //println(V.toArray(2))
  
  
  //sc.stop
}

/*
pca.txt

Left Singular vectors :
[-0.38463507702853483,-0.23962665836550157]
[-0.09467734687278807,0.17152311831064126]
[-0.40205732776720116,0.5751529887619422]
[-0.3226595886854251,0.19294109052352315]
[-0.4788838953117921,-0.2731972254659043]
[-0.39356046669263345,0.27031000790374327]
[-0.28188939767070986,-0.5108990721232975]
[-0.16514969629046555,0.04379835027981693]
[-0.2436903781931799,0.015822877696147764]
[-0.1642926391114038,-0.3663890205422716]
Singular values are :
[8.999715611355116,0.689288702018976]
Right Singular vectors :
-0.6875606238607698  -0.7261269782318993  
-0.7261269782318992  0.6875606238607698   

*/
