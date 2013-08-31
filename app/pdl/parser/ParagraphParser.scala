package pdl.parser

import scala.util.parsing.combinator.RegexParsers
import pdl.ast.{Paragraph, Text}

trait ParagraphParser extends RegexParsers with ParserCommons {

  def text: Parser[Text] = ".+".r ^^ Text

  def paragraph: Parser[Paragraph] = text <~ (newline *) ^^ Paragraph
}
