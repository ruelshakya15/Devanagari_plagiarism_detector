package edu.scrape

import edu.environment.IndexLiterals.CLIENT_URL
import edu.environment.ScrapeLiterals.{SCRAP_BASE_URL, pageSequence}
import org.jsoup.Jsoup

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.mutable.ArrayBuffer

case class Extraction(pageLink: String = null, pageTitle: String = null, pageContent: String = null) {

  private def ExtractPage = {
    val refinedLinks = ArrayBuffer[String]()
    for (pages <- pageSequence) yield {
      val doc = Jsoup.connect(SCRAP_BASE_URL + pages).get()
      val links = doc.select("#content .ok-news-post  a").asScala.map(link => link.attr("href"))
      refinedLinks ++= links.drop(4)
    }
    refinedLinks
  }

  def ExtractFromSinglePage = {
    val articles: ArrayBuffer[Extraction] = for (iterateLinks <- ExtractPage) yield {
      val doc2 = Jsoup.connect(iterateLinks).get()
      val title = doc2.select(".ok-entry-header h1").asScala.map(_.text())
      val content = doc2.select(".entry-content p").asScala.map(_.text())
      Extraction(iterateLinks, title.mkString, content.mkString)
    }
    articles
  }
}
