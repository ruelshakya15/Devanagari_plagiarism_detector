package edu.scrape

import edu.environment.PreprocessingLiterals.{ALL_REGEX, STOPWORDS_FILE}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.matching.Regex

/*
LEFT: exception handling for stop word file using try catch
 */
object Preprocessing {

  //Extract all articles
  def initialText = Extraction().ExtractFromSinglePage
  def stopWords = Source.fromFile(STOPWORDS_FILE).getLines.toSet//try finally def close conn

  //Stop Word Removal
  def stopWordResult = initialText.map { case Extraction(word1, word2, word3) =>
     Extraction(
      word1,
      removeStopWords(word2),
      removeStopWords(word3)
    )
  }

  // Function to remove stop words from a single word
  def removeStopWords(word: String): String = {
    word.trim
      .split("\\s+")
      .filterNot(stopWords.contains)
      .mkString(" ")
  }

  //  Regex Processing
    // 1. For Indexing
  def finalProcessedText: ArrayBuffer[(String, String, String)] = for (iterateText <- stopWordResult) yield {
    (iterateText.pageLink, ALL_REGEX.replaceAllIn(iterateText.pageTitle, "").mkString, ALL_REGEX.replaceAllIn(iterateText.pageContent, "").mkString)
  }
  // 2. For User Query
  def preprocessUserText(queryString: String):String = {
    val replaceString = removeStopWords(queryString)
    ALL_REGEX.replaceAllIn(replaceString,"").mkString
  }

}

