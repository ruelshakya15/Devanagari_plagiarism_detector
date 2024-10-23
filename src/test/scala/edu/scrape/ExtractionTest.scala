package edu.scrape

import org.scalatest.flatspec.AnyFlatSpec

class ExtractionTest extends AnyFlatSpec {
//  println(Extraction.articles)
//  val testing = sys.props.getOrElse("test.environment","")
//  println(testing.toLowerCase() == true)
  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}
