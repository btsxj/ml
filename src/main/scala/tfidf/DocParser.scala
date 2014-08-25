package tfidf

import java.io.File

import scala.io.Source

object DocParser {

  /**
   * 
   */
  def parseAll(path: String): Iterable[Document] = Util.textFile(path).map(parse)
  
  /**
   * Parse a document
   * 
   * Put specific parser here
   */
  def parse(file: File): Document = {
    val source = Source.fromFile(file).mkString
    Document(file.getAbsolutePath(), source)
  }
}

//
case class Document(docId: String, body: String = "")

  /*
  val docs = DocParser.parseAll(inputPath)
  //docs.foreach{d => println(d.docId); println(d.body) }

  val termDocs = TermTokenizer.tokenizeAll(docs)
  val numDocs = termDocs.size
  //termDocs.foreach{d => println(d.docId); println(d.terms) }
  
  val tdRdd = sc.parallelize[TermDoc](termDocs.toSeq)
  val terms = tdRdd.flatMap(_.terms).distinct().collect().sortBy(identity)
  terms.foreach(println)
  
  */