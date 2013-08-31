package pdl.parser

import scala.util.parsing.combinator.RegexParsers
import pdl.ast.{ListItem, Text, UnorderedList}

trait ListParser extends RegexParsers with ParserCommons with ListPostProcessor {

  private def text: Parser[Text] = ".*".r ^^ Text

  private def unorderedListBullet = "\\*".r

  private def listBulletContent = (emptySpace ?) ~> text <~ (newline ?)

  private def listItem = (unorderedListBullet +) ~ listBulletContent ^^ {
    case bullets ~ text => {
      UnorderedListItem(bullets.length, text)
    }
  }

  def unorderedList: Parser[UnorderedList] =
    (listItem *) ^^ (t => convertUnorderedListToAST(t))
}

case class UnorderedListItem(level: Int, text: Text)