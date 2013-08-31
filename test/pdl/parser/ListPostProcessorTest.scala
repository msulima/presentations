package pdl.parser

import test.tools.TestBase
import pdl.ast.{Text, UnorderedList, ListItem}

class ListPostProcessorTest extends TestBase with ParameterisedTest[Seq[UnorderedListItem], UnorderedList] {

  behavior of "List Post Processor"

  it should "convert empty list" forArgs
    Nil -> UnorderedList()

  it should "convert flat list" forArgs
    List(
      UnorderedListItem(1, Text("first item")),
      UnorderedListItem(1, Text("second item"))
    ) -> UnorderedList(
      ListItem(Text("first item")),
      ListItem(Text("second item"))
    )

  it should "convert simple nested list" forArgs
    List(
      UnorderedListItem(1, Text("first item")),
      UnorderedListItem(2, Text("second level first item"))
    ) -> UnorderedList(
      ListItem(Text("first item"), UnorderedList(
        ListItem(Text("second level first item"))
      ))
    )

  it should "convert nested list" forArgs
    List(
      UnorderedListItem(1, Text("first item")),
      UnorderedListItem(2, Text("second level first item")),
      UnorderedListItem(2, Text("second level second item")),
      UnorderedListItem(1, Text("second item"))
    ) -> UnorderedList(
      ListItem(Text("first item"), UnorderedList(
        ListItem(Text("second level first item")),
        ListItem(Text("second level second item"))
      )),
      ListItem(Text("second item"))
    )

  it should "convert nested list with errors" forArgs
    List(
      UnorderedListItem(1, Text("first item")),
      UnorderedListItem(3, Text("third level first item")),
      UnorderedListItem(2, Text("second level second item")),
      UnorderedListItem(1, Text("second item"))
    ) -> UnorderedList(
      ListItem(Text("first item"), UnorderedList(
        ListItem(Text("third level first item")),
        ListItem(Text("second level second item"))
      )),
      ListItem(Text("second item"))
    )


  def run(arg: Seq[UnorderedListItem]) = 
    new ListPostProcessor {}.convertUnorderedListToAST(arg)
}
