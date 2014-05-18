package domain.model

import akka.actor._
import domain.model.PresentationActor.{SlideChanged, Register}
import akka.actor.Terminated
import domain.model.PresentationActor.SlideChanged

object PresentationActor {

  def props() = Props(new PresentationActor)

  case class SlideChanged(slideId: String)

  case object Register

}

class PresentationActor extends Actor with ActorLogging {

  var currentSlideId = "title"
  var listeners = Set[ActorRef]()

  def receive = {
    case Register =>
      log.info(s"Actor ${sender()} registered")
      listeners += sender()
      context.watch(sender())
      sender() ! SlideChanged(currentSlideId)
    case Terminated(actorRef) =>
      log.info(s"Actor $actorRef terminated")
      listeners -= actorRef
    case msg@SlideChanged(slideId) =>
      log.info(s"Changing slide to $slideId")
      currentSlideId = slideId
      (listeners - sender()).foreach(_ ! msg)
  }
}
