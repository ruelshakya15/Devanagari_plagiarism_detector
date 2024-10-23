package edu.environment

import edu.index.CoreStruct

object JsonLiterals {
  val VALID_FIELDS: Set[String] = Set("title", "content")

  val INVALID_STRING = ""

  val exampleJson =
    s"""
       |{
       |"queryField":"title",
       |"queryString":"((बिग्रँदैछ माटोको) OR (पर्यटकलाई जति))"
       |}
       |""".stripMargin

  val INVALID_CONN=
    s"""
       |{
       |"status":"fail"
       |message:"Connection with SOlR could not be established"
       |}
       |""".stripMargin

  val INVALID_QUERY =
    s"""
       |{
       |"status":"fail",
       |message:""INVALID Field Type or Empty Content Tab"
       |}
       |""".stripMargin

  val JSON_PARAM = ("id","url","title","content","highlights")
}
