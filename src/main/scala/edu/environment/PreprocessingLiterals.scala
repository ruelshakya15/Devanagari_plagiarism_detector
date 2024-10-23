package edu.environment

import scala.util.matching.Regex

object PreprocessingLiterals extends App {
  //Stopword File PATH
  val STOPWORDS_FILE = "src/main/resources/stopwords.txt"

  //Regex
  val REGEX_SPECIAL = new Regex("""[‘’“”।~!@#$^%&*\\(\\)_+={}\\[\\]|;:\"'<,>.?`/\\\\-]""")
  val REGEX_DEVNAGARI = (raw"[^\u0900-\u097F|\s]").r
  val REGEX_NUMBER = (raw"[\u0966-\u096F]").r

  //combine multiple regex
  val ALL_REGEX = ("(?:" + REGEX_SPECIAL.pattern.pattern + ")|(?:" + REGEX_DEVNAGARI.pattern.pattern + ")|(?:" + REGEX_NUMBER.pattern.pattern + ")").r
}
