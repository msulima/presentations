package pdl.ast

sealed trait ElementTree

case class Paragraph(elements: ElementTree*) extends ElementTree

case class Text(text: String) extends ElementTree

case class Code(lines: Seq[Text]) extends ElementTree

case class UnorderedList(points: ListItem*) extends ElementTree

case class ListItem(elements: ElementTree*)

object Code {
  def apply(text: Text): Code = Code(List(text))
  def apply(text: String): Code = Code(text.split("\n").map(Text))
}