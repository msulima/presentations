package pdl.parser

import scala.util.parsing.combinator.RegexParsers

trait ParserCommons {
  this: RegexParsers =>

  def emptySpace = "[ \t]".r
  def newline = "[\r\n|\n]".r

}
