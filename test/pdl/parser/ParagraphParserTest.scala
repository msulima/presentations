package pdl.parser

import pdl.ast.{Paragraph, Text, ElementTree}
import test.tools.{ParameterisedTest, TestBase}
import scala.util.parsing.input.CharSequenceReader

class ParagraphParserTest extends TestBase with ParameterisedTest[String, ElementTree] {

  behavior of "Paragraph Parser"

  def run(arg: String) = {
    val parser = new ParagraphParser {}
    parser.paragraph(new CharSequenceReader(arg)).get
  }

  it should "change simple text to text node" forArgs
    "text" -> Paragraph(Text("text"))

  it should "change multi line text to paragraph node" forArgs
    """first line
      |second line""".stripMargin -> Paragraph(Text("first line"), Text("second line"))

  it should "ignore empty line at end of multi line text" forArgs
    """first line
      |second line
      |
    """.stripMargin -> Paragraph(Text("first line"), Text("second line"))
}
