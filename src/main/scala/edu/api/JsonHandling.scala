package edu.api

import com.github.takezoe.solr.scala.MapQueryResult
import edu.environment.JsonLiterals
import edu.environment.JsonLiterals.{INVALID_QUERY, INVALID_STRING, JSON_PARAM, VALID_FIELDS}
import edu.index.Querying
import edu.index.Querying.queryData
import edu.scrape.Preprocessing
import org.json4s._
import org.json4s.jackson.{JsonMethods, Serialization}
import org.json4s.jackson.Serialization.writePretty
import org.json4s.jackson.Serialization.write
import org.json4s.prefs.ExtractionNullStrategy
import org.json4s.prefs.ExtractionNullStrategy.Keep

/*ASK:paila url aairacha extract garda*/
//to fix : content ki title highlight garne check
// na milda json response of error deko chaina

object JsonHandling  {
  private implicit val formats: Formats = new DefaultFormats {
    override val extractionNullStrategy: ExtractionNullStrategy = Keep // Handling null values
  }

  def processJsonReq(reqJsonString: String): String = {
    val parsedString = JsonMethods.parse(reqJsonString)
    val extractString: reqFormat = parsedString.extract[reqFormat]
//    val queryParam = (extractString.queryField, Preprocessing.preprocessUserText(extractString.queryString)) //Preprocess extracted User queryString
    val queryParam = (extractString.queryField, extractString.queryString)

    //Checking for valid field and query strings
    if (VALID_FIELDS.contains(queryParam._1) && !INVALID_STRING.contains(queryParam._2)) {
      Querying.queryData(queryParam._1, queryParam._2)
    }
    else INVALID_QUERY
  }

  def generateJsonResponse(result: MapQueryResult,queryField: String): String = {

    val formattedJsonData = result.documents.map(
      inMap => {
        queryField match {
        case "title" =>
          responseFormat(inMap(JSON_PARAM._1).toString,
            inMap(JSON_PARAM._2).toString,
            inMap(JSON_PARAM._5).toString,
            inMap(JSON_PARAM._4).toString,
            "LEFT")
        case "content" =>
          responseFormat(inMap(JSON_PARAM._1).toString,
            inMap(JSON_PARAM._2).toString,
            inMap(JSON_PARAM._3).toString,
            inMap(JSON_PARAM._5).toString,
            "LEFT")
        }
      }
    )
    writePretty(formattedJsonData)
  }

}

case class reqFormat(queryField: String, queryString: String)
case class responseFormat(id: String, url: String, title: String, content: String, matchingScore: String)

