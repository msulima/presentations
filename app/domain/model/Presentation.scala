package domain.model

import akka.actor.Actor
import play.api.templates.Html
import pdl.ast._
import pdl.parser.DefaultSlideSyntaxParser

class Presentation extends Actor {

  val parser = new DefaultSlideSyntaxParser {}

  val slide1 = Header("Mateusz Sulima", "Syntactic sugar in Scala")

  val slide2 = Content(parser.parseElementTree(
    """Cechy Scali
      |* Działa pod JVM
      |* Statyczne typowanie
      |* Mieszany paradygmat
      |** Obiektowy
      |** Funkcyjny
      |* Dla zawodowych developerów
      |* Pełna interoperatywność z Javą
    """.stripMargin), "Mateusz Sulima", "Syntactic sugar in Scala")

  val slide3 = Content(parser.parseElementTree(
    """Scala może wyglądać jak Java
      |{code}
      |package com.futureprocessing.scala_sugar
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
      |}
      |{code}""".stripMargin), "Mateusz Sulima", "Syntactic sugar in Scala")

  val slide4 = Content(parser.parseElementTree(
    """Scala może wyglądać jak Scala
      |{code}
      |package com.futureprocessing.scala_sugar
      |  
      |object StringsScala {
      | 
      |    def padStart(string: String, minLength: Int, padChar: Char) =
      |        padChar.toString * (minLength - string.length) + string
      |}
      |{code}
    """.stripMargin), "Mateusz Sulima", "Syntactic sugar in Scala")

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