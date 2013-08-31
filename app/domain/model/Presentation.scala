package domain.model

import akka.actor.Actor
import play.api.templates.Html
import pdl.ast._

class Presentation extends Actor {

  val slide1 = Header("Mateusz Sulima", "Syntactic sugar in Scala")

  val slide2 = Content(List(
    Text("Cechy Scali:"),
    UnorderedList(
      ListItem(Text("Działa pod JVM")),
      ListItem(Text("Statyczne typowanie")),
      ListItem(Text("Mieszany paradygmat"), UnorderedList(
        ListItem(Text("Obiektowy")),
        ListItem(Text("Funkcyjny"))
      )),
      ListItem(Text("Dla zawodowych developerów")),
      ListItem(Text("Pełna interoperatywność z Javą"))
    )
  ), "Mateusz Sulima", "Syntactic sugar in Scala")

  val slide3 = Content(List(
    Text("Scala może wyglądać jak Java"),
    Code( """package com.futureprocessing.scala_sugar
            | 
            |object StringsScalaLikeJava {
            |     
            |    def padStart(string: String, minLength: Int, padChar: Char): String = {
            |        if (string.length >= minLength) {
            |            return string
            |        }
            |    
            |        val sb: StringBuilder = new StringBuilder(minLength)
            |        for (i <- string.length until minLength) {
            |            sb.append(padChar)
            |        }
            |    
            |        sb.append(string)
            |        return sb.toString()
            |    }
            |}""".stripMargin)
  ), "Mateusz Sulima", "Syntactic sugar in Scala")

  val slide4 = Content(List(
    Text("Scala może wyglądać jak Scala"),
    Code( """package com.futureprocessing.scala_sugar
            |  
            |object StringsScala {
            | 
            |    def padStart(string: String, minLength: Int, padChar: Char) =
            |        padChar.toString * (minLength - string.length) + string
            |}""".stripMargin)

  ), "Mateusz Sulima", "Syntactic sugar in Scala")

  val slideParser = new SlideParser {}

  val slides: Seq[Html] = {
    Seq(slideParser(slide1), slideParser(slide2), slideParser(slide3), slideParser(slide4))
  }


  def receive = {
    case GetSlide(number) =>
      sender ! slides(number - 1)
  }
}

case class GetSlide(number: Int)