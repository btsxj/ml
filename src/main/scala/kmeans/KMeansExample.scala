package kmeans

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors

object KMeansExample extends App {
  val spConfig = (new SparkConf).setMaster("local[2]").setAppName("SparkNaiveBayes")
  val sc = new SparkContext(spConfig)
  
  val irisData = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/iris-data.txt")
  // Take first four columns and transform to Double
  val dvs = irisData.map( e => Vectors.dense(e.split(',').take(4).map(_.toDouble)))
  
  val numClusters = 3
  val numIterations = 500
  val clusters = KMeans.train(dvs, numClusters, numIterations)
  
  val WSSSE = clusters.computeCost(dvs)
  println("Within Set Sum of Squared Errors = " + WSSSE)

}