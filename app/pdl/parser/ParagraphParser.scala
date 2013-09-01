package pdl.parser

import scala.util.parsing.combinator.RegexParsers
import pdl.ast.{Paragraph, Text}

trait ParagraphParser extends RegexParsers with ParserCommons {

  def text = ".+".r ^^ Text

  def textLine = ".+".r  <~ (newline ?) ^^ Text

  def multiLineText = (text <~ (newline ?)).+ <~ (newline ?)

  def paragraph: Parser[Paragraph] = multiLineText ^^ (p => Paragraph(p: _*))
}
