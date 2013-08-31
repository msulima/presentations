package domain.model

import play.api.templates.Html
import views.html.presentations
import presentations.slides.templates.elements._
import pdl.ast._

trait SlideParser {

  def apply(slide: Slide): Html = slide match {
    case Header(author, title) => header(title)
    case Content(bodies, author, title) => presentationContent("presentation-content-light") {
      mapAndJoinHtml(bodies, parseElementTree)
    }
  }

  private def parseElementTree(body: ElementTree): Html = body match {
    case Paragraph(text) => Html("<p>") += parseElementTree(text) += Html("</p>")
    case Text(text) => Html(text)
    case Code(text) => code(text)
    case UnorderedList(elements@_*) => Html("<ul>") += mapAndJoinHtml(elements, parseUnorderedList) += Html("</ul>")
  }

  private def parseUnorderedList(listItem: ListItem): Html =
    Html("<li>") += mapAndJoinHtml(listItem.elements, parseElementTree) += Html("</li>")

  private def mapAndJoinHtml[T](bodies: Seq[T], mapper: T => Html) =
    bodies.map(mapper).reverse.foldLeft(Html(""))((body, html) => html += body)
}