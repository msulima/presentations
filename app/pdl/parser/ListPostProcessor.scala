package pdl.parser

import pdl.ast.{Text, UnorderedList, ListItem}

trait ListPostProcessor {

  def convertUnorderedListToAST(listItems: Seq[UnorderedListItem]) = {
    val folded: List[ItemsOnLevel] = listItems.foldLeft(List[ItemsOnLevel]())(calculateLevelForItems).reverse
    UnorderedList(unFlattenItemsOnLevels(folded): _*)
  }

  def calculateLevelForItems(acc: List[ItemsOnLevel], item: UnorderedListItem): List[ItemsOnLevel] = {
    acc match {
      case Nil =>
        List(ItemsOnLevel(List(item.text), 1))
      case (head@ItemsOnLevel(items, level)) :: tail if item.level > level =>
        ItemsOnLevel(List(item.text), level + 1) :: head :: tail
      case (head@ItemsOnLevel(items, level)) :: tail if item.level < level =>
        ItemsOnLevel(List(item.text), item.level) :: head :: tail
      case ItemsOnLevel(items, level) :: tail =>
        ItemsOnLevel(items :+ item.text, level) :: tail
    }
  }

  def unFlattenItemsOnLevels(folded: List[ItemsOnLevel]): Seq[ListItem] = folded match {
    case Nil => Nil
    case ItemsOnLevel(items, level) :: Nil =>
      items.map(textToListItem)
    case ItemsOnLevel(items, level) :: tail =>
      val itemsOnThisLevel = items.dropRight(1)
      val headerOfHigherLevel = items.last
      val (itemsOnHigherLevel, restOfItems) = tail.span(_.level > level)

      toListItem(itemsOnThisLevel) ++ (createHigherLevel(headerOfHigherLevel, itemsOnHigherLevel) +: unFlattenItemsOnLevels(restOfItems))
  }

  def createHigherLevel(headerOfHigherLevel: Text, itemsOnHigherLevel: List[ItemsOnLevel]) = 
    ListItem(headerOfHigherLevel, UnorderedList(unFlattenItemsOnLevels(itemsOnHigherLevel): _*))

  def toListItem(texts: Seq[Text]) =
    texts.map(t => ListItem(t))

  def textToListItem(text: Text) =
    ListItem(text)
}

case class ItemsOnLevel(items: Seq[Text], level: Int)