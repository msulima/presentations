package domain.model

import HtmlGenerator.{Header, Content}
import domain.model.Slide.SlideId

trait SlidesDeck extends SlideRenderer {

  private val slide1 = Slide("title", Header, "")

  private val slide2 = Slide("features", Content,
    """Cechy Scali
      |
      |* Działa pod JVM
      |* Statyczne typowanie
      |* Mieszany paradygmat
      |    * Obiektowy
      |    * Funkcyjny
      |* Dla zawodowych developerów
      |* Pełna interoperatywność z Javą
    """.stripMargin)

  private val slide3 = Slide("scala-like-java", Content,
    """Scala może wyglądać jak Java
      |
      |    package com.futureprocessing.scala_sugar
      |     
      |    object StringsScalaLikeJava {
      |     
      |      def padStart(string: String, minLength: Int, padChar: Char): String = {
      |        if (string.length >= minLength) {
      |          return string
      |        }
      |
      |        val sb: StringBuilder = new StringBuilder(minLength)
      |        for (i <- string.length until minLength) {
      |          sb.append(padChar)
      |        }
      |
      |        sb.append(string)
      |        return sb.toString()
      |      }
      |    }""".stripMargin)

  private val slide4 = Slide("scala-like-scala", Content,
    """Scala może wyglądać jak Scala
      |
      |    package com.futureprocessing.scala_sugar
      |
      |    object StringsScala {
      |     
      |      def padStart(string: String, minLength: Int, padChar: Char) =
      |        padChar.toString * (minLength - string.length) + string
      |    }""".stripMargin)

  private val presentation = Presentation("Syntactic sugar in Scala", "Mateusz Sulima", Seq(
    slide1, slide2, slide3, slide4
  ))

  def slide(slideId: SlideId): Option[RenderedSlide] =
    render(presentation, slideId)
}
