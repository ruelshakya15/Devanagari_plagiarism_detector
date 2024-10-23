package edu.api

import io.undertow.Handlers.path
import io.undertow.Undertow
import io.undertow.server.handlers.BlockingHandler
import io.undertow.server.{HttpHandler, HttpServerExchange}
import io.undertow.util.Headers

import java.io.{BufferedReader, InputStreamReader}


object SearchServer extends App {
  //Simpler way to read the request body is to dispatch to a worker thread, which makes "HttpExchange#getInputStream()" available.
  val myHandler = new BlockingHandler(searchHandler)

  val server = Undertow.builder()
    .addHttpListener(8080, "localhost")
    .setHandler(path().addExactPath("/search", myHandler))
    .build()

  server.start()


  def searchHandler: HttpHandler = (exchange: HttpServerExchange) => {

    //Read JSON input from the request
    val reader = new BufferedReader(new InputStreamReader(exchange.getInputStream))
    val requestBody = Stream.continually(reader.readLine()).takeWhile(_ != null).mkString

    //Handle JSON
    val jsonResponse = JsonHandling.processJsonReq(requestBody)
    println(jsonResponse)

    //Set response content type and send JSON response
    exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, "application/json")
    exchange.getResponseSender.send(jsonResponse)
  }
}


