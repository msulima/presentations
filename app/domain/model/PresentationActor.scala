package domain.model

import akka.actor._
import domain.model.PresentationActor.{GetSlide, SlideChanged, Register}
import akka.actor.Terminated
import domain.model.Slide.SlideId

object PresentationActor {

  def props() = Props(new PresentationActor)

  case class SlideChanged(slideId: SlideId)

  case class GetSlide(slideId: SlideId)

  case object Register

}

class PresentationActor extends Actor with SlidesDeck with ActorLogging {

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
    case GetSlide(slideId) =>
      sender() ! slide(slideId)
    case msg@SlideChanged(slideId) =>
      log.info(s"Changing slide to $slideId")
      currentSlideId = slideId
      (listeners - sender()).foreach(_ ! msg)
  }
}
