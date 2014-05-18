package domain.model

import pdl.parser.DefaultSlideSyntaxParser
import pdl.ast.{Content, Header}
import pdl.generators.html.HtmlGenerator
import domain.model.Slide.SlideId

trait SlidesDeck {

  private val parser = new DefaultSlideSyntaxParser {}

  private val slide1 = Header("Mateusz Sulima", "Syntactic sugar in Scala")

  private val slide2 = Content(parser.parseElementTree(
    """Cechy Scali
      |* Działa pod JVM
      |* Statyczne typowanie
      |* Mieszany paradygmat
      |** Obiektowy
      |** Funkcyjny
      |* Dla zawodowych developerów
      |* Pełna interoperatywność z Javą
    """.stripMargin), "Mateusz Sulima", "Syntactic sugar in Scala")

  private val slide3 = Content(parser.parseElementTree(
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

  private val slide4 = Content(parser.parseElementTree(
    """Scala może wyglądać jak Scala
      |Oto dowód:
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

  private val slideParser = new HtmlGenerator {}

  private val slides = Seq(
    Slide("title", slideParser(slide1), next = Some("features")),
    Slide("features", slideParser(slide2), prev = Some("title"), next = Some("scala-like-java")),
    Slide("scala-like-java", slideParser(slide3), prev = Some("features"), next = Some("scala-like-scala")),
    Slide("scala-like-scala", slideParser(slide4), prev = Some("scala-like-java"))
  )

  def slide(slideId: SlideId): Option[Slide] =
    slides.find(_.slideId == slideId)
}
