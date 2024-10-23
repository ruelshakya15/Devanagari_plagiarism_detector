package edu.environment

object ScrapeLiterals  {
  val SCRAP_BASE_URL = "https://www.onlinekhabar.com/content/news"

  val totalPages: Int = 1

  /**
   * Generate next pages for extraction
   *
   * @return
   */
  def pageSequence: Seq[String] = (1 until totalPages + 1) map (pageNo => "/page/" + pageNo)

}
