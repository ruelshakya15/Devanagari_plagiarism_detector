package edu.index

import com.github.takezoe.solr.scala.SolrClient
import edu.environment.{IndexLiterals, SolrConnection}
import edu.scrape.Preprocessing

import scala.collection.mutable.ArrayBuffer

object Indexing extends App {

  //  Extract Processed Text=> Format into CaseClass
  val data: ArrayBuffer[(String, String, String)] = Preprocessing.finalProcessedText
  val formatData: ArrayBuffer[CoreStruct]  = data.map { case (word1, word2, word3) => (
    CoreStruct(word1.hashCode.toString.replace("-", ""), word1, word2, word3))
  }

  // register
  SolrConnection.client match {
    case Right(client) => formatData.map {
        news =>
          client.add(news)
          client.commit()
        }
      client.shutdown()
    case Left(error) => println(error)
  }


//  formatData.map(news =>
//    SolrConnection.client match {
//      case Right(client) => client.add(news)
//        client.commit()
//
//      case Left(error) => println(error) //**ASK eta error handling kasari garne yo ta app ma afai add garne ho values
//    }
//  )
}

