package pdl.parser

import test.tools.{ParameterisedTest, TestBase}
import pdl.ast.{Text, ListItem, UnorderedList}
import scala.util.parsing.input.CharSequenceReader

class ListParserTest extends TestBase with ParameterisedTest[String, UnorderedList] {

  behavior of "List Parser"

  def run(arg: String) = {
    val parser = new ListParser {}
    parser.unorderedList(new CharSequenceReader(arg)).get
  }

  it should "parse single list element" forArgs
    "* list element" -> UnorderedList(ListItem(Text("list element")))

  it should "parse single list element without space after bullet" forArgs
    "*list element" -> UnorderedList(ListItem(Text("list element")))

  it should "parse multiple list elements" forArgs
    """* first item
      |* second item""".stripMargin -> UnorderedList(
      ListItem(Text("first item")),
      ListItem(Text("second item"))
    )

  it should "parse nested lists elements" forArgs
    """* first item
      |** second level first item
      |** second level second item
      |* second item""".stripMargin -> UnorderedList(
      ListItem(Text("first item"), UnorderedList(
        ListItem(Text("second level first item")),
        ListItem(Text("second level second item"))
      )),
      ListItem(Text("second item"))
    )
}
