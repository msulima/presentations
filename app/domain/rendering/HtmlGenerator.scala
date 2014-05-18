package domain.rendering

import play.twirl.api.Html
import views.html.presentations
import presentations.slides.templates.elements._
import org.pegdown.PegDownProcessor
import HtmlGenerator.{Header, Content}
import domain.presentations.Slide

object HtmlGenerator {

  val Header: Slide.Theme = "header"

  val Content: Slide.Theme = "content"
}

trait HtmlGenerator {

  lazy val pegdown = new PegDownProcessor()

  def toHtml(title: String, author: String, slide: Slide): Html = slide.theme match {
    case Header =>
      header(title)
    case Content =>
      presentationContent("presentation-content-light", Html(pegdown.markdownToHtml(slide.content)))
  }
}
