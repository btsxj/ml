package tfidf

import java.io.StringReader

import org.apache.lucene.util.Version
import org.apache.lucene.analysis.util.CharArraySet
import org.apache.lucene.analysis.standard.{StandardTokenizer, StandardAnalyzer }
import org.apache.lucene.analysis.miscellaneous.{ ASCIIFoldingFilter, LengthFilter }
import org.apache.lucene.analysis.core.{ LowerCaseFilter, StopFilter }
import org.apache.lucene.analysis.en.{ EnglishPossessiveFilter, KStemFilter }
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute

import scala.collection.mutable

import collection.JavaConversions._


object TermTokenizer {
  
  val LuceneVersion = Version.LUCENE_4_9

  def tokenizeAll(docs: Iterable[Document]): Iterable[TermDoc] = docs.map(tokenize)

  def tokenize(doc: Document): TermDoc = TermDoc(doc.docId, tokenize(doc.body))

  /**
   * Put specific tokenizer implementation here
   */
  def tokenize(content: String): Seq[String] = {
    val LuceneVersion = Version.LUCENE_4_9
    val sr = new StringReader(content)
    val stream1 = new StandardTokenizer(LuceneVersion, sr)
    val stream2 = new ASCIIFoldingFilter(stream1)
    val stream3 = new LowerCaseFilter(LuceneVersion, stream2)
    val stream4 = new StopFilter(LuceneVersion, stream3, STOPWORD_SET)  //StandardAnalyzer.STOP_WORDS_SET);
    val stream5 = new LengthFilter(LuceneVersion, stream4, 3, 20);
    val stream6 = new EnglishPossessiveFilter(LuceneVersion, stream5);
    val stream = new KStemFilter(stream6);
    stream.reset

    val result = mutable.ArrayBuffer.empty[String]
    while (stream.incrementToken) {
      val term = stream.getAttribute(classOf[CharTermAttribute]).toString
      if (!(term matches ".*[\\d\\.].*")) result += term //Remove digit words
    }
    result
  }
  
  
  val STOP_WORDS = Set(
    "a",
    "about",
    "above",
    "after",
    "again",
    "against",
    "all",
    "am",
    "an",
    "and",
    "any",
    "are",
    "as",
    "at",
    "be",
    "because",
    "been",
    "before",
    "being",
    "below",
    "between",
    "both",
    "but",
    "by",
    "cannot",
    "could",
    "did",
    "do",
    "does",
    "doing",
    "down",
    "during",
    "each",
    "few",
    "for",
    "from",
    "further",
    "had",
    "has",
    "have",
    "having",
    "he",
    "her",
    "here",
    "hers",
    "herself",
    "him",
    "himself",
    "his",
    "how",
    "i",
    "if",
    "in",
    "into",
    "is",
    "it",
    "its",
    "itself",
    "me",
    "more",
    "most",
    "my",
    "myself",
    "no",
    "nor",
    "not",
    "of",
    "off",
    "on",
    "once",
    "only",
    "or",
    "other",
    "ought",
    "our",
    "ours",
    "ourselves",
    "out",
    "over",
    "own",
    "same",
    "she",
    "should",
    "so",
    "some",
    "such",
    "than",
    "that",
    "the",
    "their",
    "theirs",
    "them",
    "themselves",
    "then",
    "there",
    "these",
    "they",
    "this",
    "those",
    "through",
    "to",
    "too",
    "under",
    "until",
    "up",
    "very",
    "was",
    "we",
    "were",
    "what",
    "when",
    "where",
    "which",
    "while",
    "who",
    "whom",
    "why",
    "with",
    "would",
    "you",
    "your",
    "yours",
    "yourself",
    "yourselves")
    //"can","see","shining")
  val STOPWORD_SET = new CharArraySet(LuceneVersion, STOP_WORDS, true)
  

}

case class TermDoc(docId: String, terms: Seq[String])