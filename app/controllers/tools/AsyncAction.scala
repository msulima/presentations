package controllers.tools

import play.api.mvc.{Action, Result}
import play.api.mvc.Results.Async
import scala.concurrent.Future

object AsyncAction  {

  def apply(body: => Future[Result]) = Action {
    Async {
      body
    }
  }
}
