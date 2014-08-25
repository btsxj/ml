package tfidf

object TFIDFDriver extends App {

  val commonPath = "D:/xiaojun/lesson/worksp/scala/ml/src/main/resources/tfidf/ex2"
  val trainPath = commonPath + "/training/input"
  val testPath = commonPath + "/test/input"

  // Make a dictionary from training documents
  val dictionary = TFIDF.makeADictionary(TFIDF.tokenizeFiles(trainPath))
  println(dictionary)
  
  // Calculate tf from test documents
  val testTfDir = TFIDF.tokenizeFiles(testPath)
  // Build up tfidf matrix
  val tfidf_mat = TFIDF.tfidfMatrix(testPath, dictionary )
  // Normalize tfidf matrix
  val norm_tfidf = tfidf_mat.map(TFIDF.normalize)
  norm_tfidf.foreach(println)
  
  //Calculate cosine similarity (%)
  println(TFIDF.cosine(norm_tfidf(0), norm_tfidf(1)) * 100 + "%")
  
}