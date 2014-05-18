package infrastructure

import akka.actor.{ActorLogging, ActorRef, Props, Actor}
import infrastructure.PresentationActor.{SlideChanged, Register}

object PresentationClientActor {

  def props(presentationActor: ActorRef, out: ActorRef) =
    Props(new PresentationClientActor(presentationActor, out))
}

class PresentationClientActor(presentationActor: ActorRef, out: ActorRef) extends Actor with ActorLogging {

  val SlideChangedRegex = "slideChanged: (.+)".r

  override def preStart() = {
    presentationActor ! Register
  }

  def receive = {
    case msg: String =>
      log.debug(s"Received: $msg")
      msg match {
        case SlideChangedRegex(title) =>
          presentationActor ! new SlideChanged(title)
        case _ =>
          out ! ("Unknown message: " + msg)
      }
    case SlideChanged(slideId) =>
      out ! s"slideChanged: $slideId"
  }
}
