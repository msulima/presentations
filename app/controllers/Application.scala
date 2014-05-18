package controllers

import play.api.mvc._
import play.twirl.api.Html
import play.api.libs.concurrent.Akka
import akka.pattern.ask
import domain.model._
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}
import play.api.libs.json.Json
import domain.model.RenderedSlide.SlideId
import domain.model.PresentationActor.GetSlide

object Application extends Controller {

  import play.api.Play.current
  import ExecutionContext.Implicits.global

  def index = Action {
    Ok(views.html.main("Main page.")(Html("")))
  }

  val presentationActor = Akka.system.actorOf(PresentationActor.props())

  implicit val timeout: Timeout = 2.seconds

  def slideContent(slideId: SlideId) = Action.async { implicit request =>
    render.async {
      case Accepts.Json() =>
        (presentationActor ? GetSlide(slideId)).mapTo[Option[RenderedSlide]].map {
          case Some(result) =>
            Ok(Json.toJson(result))
          case None =>
            NotFound
        }
      case _ =>
        Future.successful(Ok(views.html.main("Main page.")(Html(""))))
    }
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    PresentationClientActor.props(presentationActor, out)
  }
}
