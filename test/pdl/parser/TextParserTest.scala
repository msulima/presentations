package pdl.parser

import pdl.ast.{Text, ElementTree}
import test.tools.TestBase

class TextParserTest extends TestBase with ParameterisedTest[String, ElementTree] {

  behavior of "Text Parser"

  val slideParser = new DefaultSlideSyntaxParser {}

  def run(text: String) =
    slideParser.parseElementTree(text)

  it should "change simple text to text node" forArgs
    "text" -> Text("text")
}
