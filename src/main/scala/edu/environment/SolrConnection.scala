package edu.environment

import com.github.takezoe.solr.scala.SolrClient
import edu.environment.JsonLiterals.INVALID_CONN

import scala.util.Try

object SolrConnection {

  val client: Either[String, SolrClient] = createConnection

  private def createConnection: Either[String, SolrClient] = {
    Try {
      Right(new SolrClient(IndexLiterals.CLIENT_URL))
    }.getOrElse(Left(JsonLiterals.INVALID_CONN))
  }
}
