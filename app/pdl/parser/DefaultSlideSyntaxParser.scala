package pdl.parser

import scala.util.parsing.combinator.RegexParsers
import pdl.ast.{ElementTree, ListItem, UnorderedList, Text}

trait DefaultSlideSyntaxParser extends SlideSyntaxParser with RegexParsers {

  override def skipWhitespace = false
  
  def parseElementTree(text: String) = 
    parse(elementTreeParser, text).get

  private def emptySpace = "[ \t]".r
  private def text: Parser[Text] = ".*".r ^^ { Text }
  private def unorderedListBullet = "\\*".r
  
  private def unorderedList: Parser[UnorderedList] = unorderedListBullet ~> emptySpace ~> text ^^ (t => UnorderedList(ListItem(t)))

  private def elementTreeParser: Parser[ElementTree] =
    unorderedList | text
}
