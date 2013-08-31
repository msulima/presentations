package pdl.parser

import pdl.ast.ElementTree

trait DefaultSlideSyntaxParser extends SlideSyntaxParser with ParagraphParser with ListParser {

  def parseElementTree(text: String): Seq[ElementTree] =
    parse(multipleElementTrees, text).get

  private def multipleElementTrees =
    rep(elementTree)

  private def elementTree =
    unorderedList | paragraph
}
