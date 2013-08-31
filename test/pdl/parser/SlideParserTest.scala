package pdl.parser

import pdl.ast._
import test.tools.{ParameterisedTest, TestBase}
import pdl.ast.UnorderedList
import pdl.ast.ListItem
import pdl.ast.Text

class SlideParserTest extends TestBase with ParameterisedTest[String, Seq[ElementTree]] {

  behavior of "Slide Parser"

  val slideParser = new DefaultSlideSyntaxParser {}

  def run(text: String) =
    slideParser.parseElementTree(text)

  it should "parse empty input" forArgs
    "" -> List()

  it should "change simple text to text node" forArgs
    "text" -> List(Paragraph(Text("text")))

  it should "change single list element to unordered list node" forArgs
    "* list element" -> List(UnorderedList(ListItem(Text("list element"))))

  it should "change text and list" forArgs
    """text
      |
      |* list element""".stripMargin -> List(Paragraph(Text("text")), UnorderedList(ListItem(Text("list element"))))
}
