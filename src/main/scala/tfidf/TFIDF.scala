package tfidf

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._

import java.io.File

import scala.io.Source

object TFIDF {

  val spConfig = (new SparkConf).setMaster("local").setAppName("TFIDF Demo")
  val sc = new SparkContext(spConfig)

  /**
   * Tokenize every file's content and save to files with same file names
   */
  def tokenizeFiles(inputPath: String): String = {
    val tfDir = new File(inputPath).getParent() + "/tf"
    val tmpDir = inputPath + "/tmp"
    
    val outputDir = new File(tfDir)
    if (outputDir.exists) Util.deleteDir(outputDir)

    val sFiles = Util.textFile(inputPath).toList
    sFiles.foreach { f =>
      val basename = f.getName
      val source = sc.textFile(f.toString)
      val words = source.flatMap(line => TermTokenizer.tokenize(line))

      // Clear output directory
      val tmpd = new File(tmpDir)
      if (tmpd.exists) Util.deleteDir(tmpDir)

      words.coalesce(1).saveAsTextFile(tmpDir)
      val tmpf = new File(tmpDir + "/part-00000")
      new File(tfDir).mkdir //Need to create a directory before rename
      tmpf.renameTo(new File(tfDir, basename))
      Util.deleteDir(tmpDir)
    }
    tfDir
  }

  /**
   * Make a dictionary(index vocabulary)
   */
  def makeADictionary(tfDir: String): Map[Int, String] = {
    val source = sc.textFile(tfDir)
    val words = source.map(word => (word, 1))
      .reduceByKey(_ + _)
      //.sortByKey(true)
      .map(_._1)
      .collect
    val index = (1 to words.length)
    val dictionary = index.zip(words).toMap
    dictionary
  }

  /**
   * Build a tf-idf matrix
   */
  def tfidfMatrix(inputPath: String, dictionary: Map[Int, String]): List[IndexedSeq[Double]] = {
    val sFiles = Util.textFile(inputPath).toList
    val tfDir = tokenizeFiles(inputPath)
    val numOfDocs = sFiles.length

    // Make a vector space of words
    val wvs = sFiles.map(f => tfs(dictionary.values.toVector, tfDir + "/" + f.getName))

    // The number of documents where the term appears
    val ntd = wvs.transpose.map(v => v.count(_ != 0)).toVector

    // Compute idf at every term
    val idf = ntd.map(n => Math.log(numOfDocs.toDouble / (1 + n)))

    // Multiply wvs and a giagonal matrix of idf
    val tfidf_mat = wvs.map(lv => idf.indices.map(i => lv(i) * idf(i)) )
    tfidf_mat
  }

  /**
   * Count term frequence in a document
   */
  def tf(t: String, docId: String): Int = {
    val words = Source.fromFile(docId).getLines
    words.count(w => w.equalsIgnoreCase(t))
  }

  /**
   * Get a series of tf in a document
   * Avoid duplicate IO access
   */
  def tfs(dwords: Vector[String], docId: String): Vector[Int] = {
    val words = sc.textFile(docId).collect
    dwords.map(dw => words.count(w => w.equalsIgnoreCase(dw)))
  }
  
  /**
   * Normalize a vector with Euclidean norm
   */
  def normalize(v: IndexedSeq[Double]): IndexedSeq[Double] = {
    val l2_norm = magnitude(v)
    v.map(e => e / l2_norm)
  }
  
  /**
   * Cosine similarity
   */
  def cosine(x: IndexedSeq[Double], y: IndexedSeq[Double]): Double = {
    dotProduct(x, y) / (magnitude(x) * magnitude(y))
  }
  
  /**
   * Doc product
   */
  def dotProduct(x: IndexedSeq[Double], y: IndexedSeq[Double]): Double = {
    require(x.size == y.size)
    x.indices.map(i => x(i) * y(i)).sum
  }
  
  /**
   * Calculate magnitude of a vector
   */
  def magnitude(v: IndexedSeq[Double]): Double = {
    Math.sqrt(v.map(e => e * e).sum)
  }

}
