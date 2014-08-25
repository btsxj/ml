package naivebayes

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint

import org.apache.spark.mllib.classification.NaiveBayes

object NaiveBayesExample extends App {
  
  val spConfig = (new SparkConf).setMaster("local").setAppName("SparkNaiveBayes")
  val sc = new SparkContext(spConfig)
  
  val irisData = sc.textFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/iris-data.txt")
  // setosa : 0 , versicolor : 1 , virginica : 2
  // 5.1,3.5,1.4,0.2,"setosa" -> LabeledPoint(0.0, [5.1,3.5,1.4,0.2])
  val irisMatrix = irisData.map{ 
    line =>
      val parts = line.split(',')
      val dv = Vectors.dense(parts.take(4).map(_.toDouble))
      parts(4).replace("\"", "") match {
        case "setosa" => LabeledPoint(0.0, dv)
        case "versicolor" => LabeledPoint(1.0, dv)
        case "virginica" => LabeledPoint(2.0, dv)
        case _ => throw new Exception("Wrong iris data")
      }
  }
  //irisMatrix.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp")
  
  // training 80% and test 20%
  val splits = irisMatrix.randomSplit(Array(0.8, 0.2), seed = 9L)
  val training = splits(0)
  val test = splits(1)
  test.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp")
  
  val model = NaiveBayes.train(training, lambda = 1.0)
  val prediction = model.predict(test.map(_.features))
  prediction.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp2")
  
  val testF = test.map(_.features)
  testF.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp3")
  
  val preResult = prediction.zip(testF)
  preResult.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp5")
  
  val predictionAndLabel = prediction.zip(test.map(_.label))
  predictionAndLabel.saveAsTextFile("D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tmp4")
  
  println(predictionAndLabel.filter(x => x._1 == x._2).count() + " " + test.count)
  val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
  println(accuracy)
  

}