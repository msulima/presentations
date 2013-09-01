package pdl.generators.html

import play.api.templates.Html
import views.html.presentations
import presentations.slides.templates.elements._
import pdl.ast._
import Tag._

trait HtmlGenerator {

  def apply(slide: Slide): Html = slide match {
    case Header(author, title) => header(title)
    case Content(bodies, author, title) => presentationContent("presentation-content-light") {
      concat(bodies.map(parseElementTree))
    }
  }

  private def parseElementTree(body: ElementTree): Html = body match {
    case Paragraph(text) => P(parseElementTree(text))
    case Text(text) => Html(text)
    case Code(lines) => code(lines.map(_.text).mkString("\n"))
    case UnorderedList(elements@_*) => UL(elements.map(parseUnorderedList))
  }

  private def parseUnorderedList(listItem: ListItem): Html =
    LI(listItem.elements.map(parseElementTree))
}