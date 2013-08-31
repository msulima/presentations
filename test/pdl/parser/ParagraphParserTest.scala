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

  it should "change multiline text to paragraph node" forArgs
    """text
      |
    """.stripMargin -> Paragraph(Text("text"))
}
