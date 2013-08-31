package pdl.parser

import pdl.ast.{ListItem, UnorderedList, Text, ElementTree}
import test.tools.TestBase

class SlideParserTest extends TestBase with ParameterisedTest[String, ElementTree] {

  behavior of "Slide Parser"

  val slideParser = new DefaultSlideSyntaxParser {}

  def run(text: String) =
    slideParser.parseElementTree(text)

  it should "change simple text to text node" forArgs
  "text" -> Text("text")

  it should "change single list element to unordered list node" forArgs
  "* list element" -> UnorderedList(ListItem(Text("list element")))

}
