package edu.index

import com.github.takezoe.solr.scala.{MapQueryResult, Order}
import edu.api.JsonHandling
import edu.environment.SolrConnection

/*
  NOTES:
  1. doc("highlights") "s" thapnu parcha
  2. "result" in the form:  MapQueryResult( 2,0 ,List( Map( highlights -> "", url -> "", id -> "", content ->"",title ->""), Map(....), Map(...)))
      result.documents removes MapQueryResult returns inner List()
 */

object Querying {

  def queryData(queryField: String, queryString: String): String = {

    // query
    SolrConnection.client match {
      case Right(client) =>
        val mapResult = client.query(s"${queryField}: ${queryString}")
        .id("id")
        .fields("id", "url", "title", "content")
        .highlight(s"${queryField}", prefix ="<strong>", postfix = "</strong>",size=999999)
//        .getResultAsMap(Map(s"${queryField}" -> s"$queryString"))
        .getResultAsMap()


        JsonHandling.generateJsonResponse(mapResult,queryField)

      case Left(error) => error
    }
  }
}


