package pdl.parser

import test.tools.{ParameterisedTest, TestBase}
import pdl.ast.Code
import scala.util.parsing.input.CharSequenceReader

class CodeParserTest extends TestBase with ParameterisedTest[String, Code] {

  behavior of "Code Parser"

  def run(arg: String) = {
    val parser = new CodeParser {}
    parser.code(new CharSequenceReader(arg)).get
  }

  it should "convert multiline code" forArgs
    """{code}
      |first line
      |second line
      |{code}""".stripMargin -> Code(
      """first line
        |second line""".stripMargin)
}
