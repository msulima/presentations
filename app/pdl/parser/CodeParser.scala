package pdl.parser

import scala.util.parsing.combinator.RegexParsers
import pdl.ast.{Code, Text}

trait CodeParser extends RegexParsers with ParserCommons with ParagraphParser {

  def codeMarker = "\\{code\\}".r
  def blockStart = codeMarker <~ newline
  def blockEnd = codeMarker ^^^ Nil
  
  def linesInBlock: Parser[List[Text]] = blockEnd | lineInBlock
  
  def lineInBlock = (textLine ~ linesInBlock) ^^ {
    case text ~ textInPreviousLines => text +: textInPreviousLines
  }

  def code = blockStart ~> linesInBlock ^^ Code.apply
}
