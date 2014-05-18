package domain.model

import play.twirl.api.Html
import domain.model.RenderedSlide.SlideId
import play.api.libs.json._
import play.api.libs.functional.syntax._

object RenderedSlide {

  type SlideId = String

  implicit val htmlWrites: Writes[Html] = new Writes[Html] {
    override def writes(o: Html): JsValue = JsString(o.toString())
  }

  implicit val writes: Writes[RenderedSlide] = (
    (JsPath \ "slideId").write[SlideId] and
      (JsPath \ "content").write[Html] and
      (JsPath \ "prev").write[Option[SlideId]] and
      (JsPath \ "next").write[Option[SlideId]]
    )(unlift(RenderedSlide.unapply))
}

case class RenderedSlide(slideId: SlideId, content: Html, prev: Option[SlideId] = None, next: Option[SlideId] = None)
