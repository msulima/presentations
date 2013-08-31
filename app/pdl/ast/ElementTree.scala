package pdl.ast

sealed trait ElementTree

case class Text(text: String) extends ElementTree

case class Code(code: String) extends ElementTree

case class UnorderedList(points: ListItem*) extends ElementTree

case class ListItem(elements: ElementTree*)
