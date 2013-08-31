package controllers

import play.api.mvc._
import play.api.templates.Html
import play.api.libs.concurrent.Akka
import akka.actor.Props
import akka.pattern.ask
import domain.model.{GetSlide, Presentation}
import scala.concurrent.duration._
import controllers.tools.AsyncAction
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}
import play.api.libs.iteratee.Concurrent
import play.api.libs.json.{Json, JsValue}
import play.api.libs.EventSource

object Application extends Controller {

  import play.api.Play.current
  import ExecutionContext.Implicits.global

  def index = Action {
    Ok(views.html.main("Main page.")(Html("")))
  }

  var presentation = Akka.system.actorOf(Props[Presentation])

  implicit val timeout: Timeout = 2.seconds

  val (presentationOut, presentationChannel) = Concurrent.broadcast[JsValue]

  def slideContent(index: Int) = AsyncAction {
    val content: Future[Html] = ask(presentation, GetSlide(index)).mapTo[Html]
    content.map(result => {
      presentationChannel.push(Json.obj("data" -> result.body))
      Ok(result)
    })
  }

  def presentationFeed = Action {
    Ok.stream(presentationOut &> EventSource()).as("text/event-stream")
  }
}