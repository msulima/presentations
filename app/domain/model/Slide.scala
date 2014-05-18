package domain.model

import play.twirl.api.Html
import domain.model.Slide.SlideId
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Slide {

  type SlideId = String

  implicit val htmlWrites: Writes[Html] = new Writes[Html] {
    override def writes(o: Html): JsValue = JsString(o.toString())
  }

  implicit val writes: Writes[Slide] = (
    (JsPath \ "slideId").write[SlideId] and
      (JsPath \ "slide").write[Html] and
      (JsPath \ "prev").write[Option[SlideId]] and
      (JsPath \ "next").write[Option[SlideId]]
    )(unlift(Slide.unapply))
}

case class Slide(slideId: SlideId, slide: Html, prev: Option[SlideId] = None, next: Option[SlideId] = None)
