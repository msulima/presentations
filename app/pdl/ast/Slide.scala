package pdl.ast

sealed trait Slide {
  def author: String

  def title: String
}

case class Header(author: String, title: String) extends Slide

case class Content(content: Seq[ElementTree], author: String, title: String) extends Slide
