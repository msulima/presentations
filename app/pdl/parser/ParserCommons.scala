package pdl.parser

import scala.util.parsing.combinator.RegexParsers

trait ParserCommons {
  this: RegexParsers =>

  override def skipWhitespace = false

  def emptySpace = "[ \t]".r

  def newline = "[\r\n|\n]".r

}
