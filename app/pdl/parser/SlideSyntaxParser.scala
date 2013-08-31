package pdl.parser

import pdl.ast.ElementTree

trait SlideSyntaxParser {

  def parseElementTree(text: String): ElementTree
}
